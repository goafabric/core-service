package org.goafabric.core.files.objectstorage.logic;

import io.awspring.cloud.autoconfigure.core.AwsAutoConfiguration;
import org.goafabric.core.extensions.DurationLog;
import org.goafabric.core.extensions.TenantInterceptor;
import org.goafabric.core.files.objectstorage.controller.vo.ObjectEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.List;

@Component
@ConditionalOnProperty(value = "spring.cloud.aws.s3.enabled", havingValue = "true")
@Import(AwsAutoConfiguration.class)
@DurationLog
public class ObjectStorageLogic{

    private final S3Client s3Client;
    
    @Value("${multi-tenancy.schema-prefix}") 
    private String schemaPrefix;
    

    public ObjectStorageLogic(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void create(ObjectEntry objectEntry) {
        createBucketIfNotExists(getBucketName());
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(getBucketName())
                        .key(objectEntry.objectName())
                        .contentLength(objectEntry.objectSize())
                        .contentType(objectEntry.contentType())
                        .build(),
                RequestBody.fromBytes(objectEntry.data()));
    }

    private void createBucketIfNotExists(String bucket) {
        if (s3Client.listBuckets().buckets().stream().noneMatch(b -> b.name().equals(bucket))) {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucket).build());
            s3Client.putBucketVersioning(PutBucketVersioningRequest.builder().bucket(bucket)
                    .versioningConfiguration(VersioningConfiguration.builder().status(BucketVersioningStatus.ENABLED).build())
                        .build());
        }
    }

    public ObjectEntry getById(String id) {
        var response = s3Client.getObject(request -> request.bucket(getBucketName()).key(id));
        try {
            return new ObjectEntry(id, response.response().contentType(), response.response().contentLength(), response.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ObjectEntry> search(String search) {
        var contents = s3Client.listObjects(builder -> builder.bucket(getBucketName())).contents();
        return contents.stream().map(c ->
                new ObjectEntry(c.key(), null, c.size(), null))
                .filter(o -> o.objectName().toLowerCase().startsWith(search.toLowerCase()))
                .toList();
    }
    private String getBucketName() {
        return schemaPrefix.replaceAll("_", "-") + TenantInterceptor.getTenantId();
    }

}
