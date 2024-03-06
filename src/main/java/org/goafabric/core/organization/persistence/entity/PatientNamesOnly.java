package org.goafabric.core.organization.persistence.entity;

public interface PatientNamesOnly {
    String getId();
    String getGivenName();
    String getFamilyName();

    default String getFullName() {
        return String.format("%s %s", getGivenName(), getFamilyName());
    }
}
