package org.goafabric.core.medicalrecords.repository.elastic.repository.entity;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "#{@tenantIdBean.getPrefix()}medical_record", createIndex = false)
public class MedicalRecordElo {
    @org.springframework.data.annotation.Id
    private String recordId;

    private String encounterId;

    private String type;

    private String display;

    private String code;

    private MedicalRecordElo() {}

    public MedicalRecordElo(String recordId, String encounterId, String type, String display, String code) {
        this.recordId = recordId;
        this.encounterId = encounterId;
        this.type = type;
        this.display = display;
        this.code = code;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getEncounterId() {
        return encounterId;
    }

    public String getType() {
        return type;
    }

    public String getDisplay() {
        return display;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "MedicalRecordEo{" +
                "recordId='" + recordId + '\'' +
                ", encounterId='" + encounterId + '\'' +
                ", type='" + type + '\'' +
                ", display='" + display + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

