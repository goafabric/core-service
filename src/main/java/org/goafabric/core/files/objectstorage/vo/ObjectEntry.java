package org.goafabric.core.files.objectstorage.vo;

public record ObjectEntry(
    String objectName,
    String contentType,
    Long objectSize,
    byte[] data)
{}
