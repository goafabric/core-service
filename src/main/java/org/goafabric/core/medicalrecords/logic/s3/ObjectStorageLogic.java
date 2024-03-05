package org.goafabric.core.medicalrecords.logic.s3;

import am.ik.s3.ListBucketResult;
import am.ik.s3.ListBucketsResult;
import am.ik.s3.S3Client;
import org.goafabric.core.extensions.HttpInterceptor;
import org.goafabric.core.medicalrecords.controller.dto.ObjectEntry;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
@RegisterReflectionForBinding({ListBucketResult.class, ListBucketsResult.class}) //implementation("am.ik.s3:simple-s3-client:0.1.1") {exclude("org.springframework", "spring-web")}
public class ObjectStorageLogic {

    private final Boolean   s3Enabled;
    private final String    schemaPrefix;
    private final S3Client  s3Client;
    private final List<ObjectEntry> objectEntriesInMem = new ArrayList<>();

    public ObjectStorageLogic(@Value("${spring.cloud.aws.s3.enabled}") Boolean s3Enabled,
                              @Value("${multi-tenancy.schema-prefix:}") String schemaPrefix,
                              @Value("${spring.cloud.aws.s3.endpoint}") String endPoint,
                              @Value("${spring.cloud.aws.region.static}") String region,
                              @Value("${spring.cloud.aws.credentials.access-key}") String accessKey,
                              @Value("${spring.cloud.aws.credentials.secret-key}") String secretKey) {
        this.s3Enabled = s3Enabled;
        this.schemaPrefix = schemaPrefix;
        this.s3Client = new S3Client(new RestTemplate(), URI.create(endPoint), region, accessKey, secretKey);
    }


    public ObjectEntry getById(String id) {
        if (!s3Enabled) { return objectEntriesInMem.stream().filter(o -> o.objectName().equals(id)).findFirst().get(); }

        var data = s3Client.getObject(getBucketName(), id);
        return new ObjectEntry(id, null, (long) data.length, data);
    }

    public List<ObjectEntry> search(String search) {
        if (!s3Enabled) { return objectEntriesInMem.stream().filter(o -> o.objectName().startsWith(search)).toList(); }

        var bucketResult = s3Client.listBucket(getBucketName());
        return bucketResult.contents().stream().map(c ->
                        new ObjectEntry(c.key(), null, c.size(), null))
                .filter(o -> o.objectName().toLowerCase().startsWith(search.toLowerCase()))
                .toList();
    }

    public void save(ObjectEntry objectEntry) {
        if (!s3Enabled) { objectEntriesInMem.add(objectEntry); return; }

        createBucketIfNotExists(getBucketName());
        s3Client.putObject(getBucketName(), objectEntry.objectName(), objectEntry.data(),
                MediaType.valueOf(objectEntry.contentType()));
    }

    private void createBucketIfNotExists(String bucket) {
        if (s3Client.listBuckets().buckets().stream().noneMatch(b -> b.name().equals(bucket))) { //this could be slow
            s3Client.putBucket(getBucketName());
            //Bucket Versioning is missing here
        }
    }

    private String getBucketName() {
        return schemaPrefix.replaceAll("_", "-") + HttpInterceptor.getTenantId();
    }

}

