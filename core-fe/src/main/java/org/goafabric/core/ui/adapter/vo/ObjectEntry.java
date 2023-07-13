package org.goafabric.core.ui.adapter.vo;

public record ObjectEntry(
    String objectName,
    String contentType,
    Long objectSize,
    byte[] data)
{}
