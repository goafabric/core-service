package org.goafabric.core.organization.controller.dto;

public record Lock(String id, Boolean isLocked, String lockKey, String lockTime, String userName) {
}
