package org.goafabric.core.medicalrecords.logic.chatbot;


import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.medicalrecords.logic.jpa.EncounterLogic;
import org.goafabric.core.organization.logic.PatientLogic;
import org.goafabric.core.organization.persistence.entity.PatientNamesOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BruteChatTool {
    @Autowired
    private PatientLogic patientLogic;

    @Autowired
    private EncounterLogic encounterLogic;

    public PatientNamesOnly findPatientViaDatabaseBruteForce(String name) {
        var patientNames = patientLogic.findByFamilyNameAndGivenName(name, "");
        if (!patientNames.isEmpty()) {
            return patientNames.getFirst();
        } else {
            var patientNames2 = patientLogic.findByFamilyNameAndGivenName("", name);
            return !patientNames2.isEmpty() ? patientNames2.getFirst() : null;
        }
    }

    public MedicalRecordType findMedicalRecordTypeViaKeyords(String type) {
        return switch (type.toLowerCase()) {
            case "anamnesis", "anamneses", "anamnese", "anamnesen" -> MedicalRecordType.ANAMNESIS;
            case "condition", "conditions", "diagnosis", "diagnose", "diagnosen" -> MedicalRecordType.CONDITION;
            case "charge", "charges", "chargeitem", "chargeitems", "leistung", "leistungen" -> MedicalRecordType.CHARGEITEM;
            case "finding", "findings", "befund", "befunde" -> MedicalRecordType.FINDING;
            case "therapy", "therapies", "therapie", "therapien" -> MedicalRecordType.THERAPY;
            case "bodymetrics" -> MedicalRecordType.BODY_METRICS;
            //case null -> throw new IllegalArgumentException("Type should not be null");
            default -> null;
        };
    }

    public List<Encounter> findByPatientIdAndDisplayAndType(String patientId, String display, List<MedicalRecordType> types) {
        return encounterLogic.findByPatientIdAndDisplayAndType(patientId, display, types);
    }

}
