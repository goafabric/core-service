package org.goafabric.core.organization.persistence.extensions;

import java.util.Date;

record AuditTrail(
        String id,
        String organizationId,
        String objectType,
        String objectId,
        AuditTrailListener.DbOperation operation,
        String createdBy,
        Date createdAt,
        String modifiedBy,
        Date   modifiedAt,
        String oldValue,
        String newValue
) {}
