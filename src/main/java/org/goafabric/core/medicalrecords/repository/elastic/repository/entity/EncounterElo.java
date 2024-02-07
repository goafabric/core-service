package org.goafabric.core.medicalrecords.repository.elastic.repository.entity;

import org.goafabric.core.organization.repository.extensions.TenantResolver;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Document(indexName = "#{@tenantIdBean.getPrefix()}encounter", createIndex = true)
public class EncounterElo {

    @Id
    private String id;

    private Long version;

    private String organizationId;

    private String patientId;

    private String practitionerId;

    @Field(type = FieldType.Date)
    private LocalDate encounterDate;

    private String encounterName;

    //@Field(type = FieldType.Nested, includeInParent = false)
    //private List<org.goafabric.personservice.syncmedicalrecords.elastic.repository.entity.MedicalRecordEo> medicalRecords;

    private EncounterElo() {}
    public EncounterElo(String id, Long version, String patientId, String practitionerId, String encounterName, LocalDate encounterDate) {
        this.id = id;
        this.version = version;
        this.patientId = patientId;
        this.practitionerId = practitionerId;
        this.encounterName = encounterName;
        this.encounterDate = encounterDate;
        this.organizationId = TenantResolver.getOrgunitId();
    }

    public String getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public String getOrgunitId() {
        return organizationId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPractitionerId() {
        return practitionerId;
    }

    public String getEncounterName() {
        return encounterName;
    }

    public LocalDate getEncounterDate() {
        return encounterDate;
    }
}
