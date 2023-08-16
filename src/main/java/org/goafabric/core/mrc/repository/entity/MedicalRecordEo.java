package org.goafabric.core.mrc.repository.entity;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Entity
@Table(name="medical_record")
@Document("#{@tenantIdBean.getPrefix()}medical_record")
//@EntityListeners(AuditTrailListener.class)
public class MedicalRecordEo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String type;

    @TextIndexed
    public String display;

    public String code;

    public String relations;

    @Version //optimistic locking
    public Long version;

}
