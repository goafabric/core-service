package org.goafabric.core.files.objectstorage.logic;

import io.awspring.cloud.autoconfigure.core.AwsAutoConfiguration;
import org.goafabric.core.crossfunctional.DurationLog;
import org.goafabric.core.files.objectstorage.dto.ObjectEntry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.List;

@Component
@ConditionalOnProperty(value = "spring.cloud.aws.s3.enabled", havingValue = "true")
@Import(AwsAutoConfiguration.class)
@DurationLog
public class ObjectStorageLogic{

    private final S3Client s3Client;
    private static final String bucketName = "objects";

    public ObjectStorageLogic(S3Client s3Client) {
        this.s3Client = s3Client;
        try {
            s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());
        } catch (S3Exception e) {
        }
    }

    public void create(ObjectEntry objectEntry) {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectEntry.objectName())
                        .contentLength(objectEntry.objectSize())
                        .contentType(objectEntry.contentType())
                        .build(),
                RequestBody.fromBytes(objectEntry.data()));
    }

    public ObjectEntry getById(String id) {
        var response = s3Client.getObject(request -> request.bucket(bucketName).key(id));
        try {
            return new ObjectEntry(id, response.response().contentType(), response.response().contentLength(), response.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ObjectEntry> search(String search) {
        var contents = s3Client.listObjects(builder -> builder.bucket(bucketName)).contents();
        return contents.stream().map(c ->
                new ObjectEntry(c.key(), null, c.size(), null))
                .filter(o -> o.objectName().toLowerCase().startsWith(search.toLowerCase()))
                .toList();
    }
}
