package org.goafabric.core.medicalrecords.repository.jpa.entity;

import jakarta.persistence.*;
import org.goafabric.core.organization.repository.extensions.AuditTrailListener;


@Entity
@Table(name="medical_record")
//@Document("#{@tenantIdBean.getPrefix()}medical_record")
@EntityListeners(AuditTrailListener.class)
public class MedicalRecordEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    @Column(name = "encounter_id")
    public String encounterId;

    public String type;

    //@TextIndexed
    public String display;

    public String code;

    public String relation;

    @Version //optimistic locking
    public Long version;

}
