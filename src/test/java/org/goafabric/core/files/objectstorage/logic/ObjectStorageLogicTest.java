package org.goafabric.core.files.objectstorage.logic;

import org.goafabric.core.files.objectstorage.controller.vo.ObjectEntry;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ObjectStorageLogicTest {
    private S3Client s3Client = Mockito.mock(S3Client.class);
    private final ObjectStorageLogic objectStorageLogic = new ObjectStorageLogic(s3Client, "");

    @Test
    void create() {
        when(s3Client.listBuckets()).thenReturn(
                ListBucketsResponse.builder().buckets(new Bucket[]{}).build());
        objectStorageLogic.create(new ObjectEntry("name", "type", 1l, new byte[]{}));
        //verify(s3Client.putObject(any(PutObjectRequest.class))).ti
    }

    @Test
    void getById() {
        var responseStream = new ResponseInputStream(GetObjectResponse.builder()
                .contentType("type").contentLength(1l).build(), new ByteArrayInputStream(new byte[]{}));
        when(s3Client.getObject(any(Consumer.class))).thenReturn(responseStream);
        assertThat(objectStorageLogic.getById("1")).isNotNull();
    }

    @Test
    void search() {
        var response = Mockito.mock(ListObjectsResponse.class);
        when(s3Client.listObjects(any(Consumer.class))).thenReturn(response);
        when(response.contents()).thenReturn(new ArrayList<>());
        assertThat(objectStorageLogic.search("")).isNotNull();
    }
}