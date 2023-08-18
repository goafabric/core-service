package org.goafabric.core.ui.adapter;

import org.goafabric.core.data.controller.vo.Patient;
import org.goafabric.core.data.logic.PatientLogic;
import org.goafabric.core.data.repository.entity.PatientNamesOnly;
import org.goafabric.core.ui.SearchLogic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientAdapter implements SearchLogic<Patient> {
    private final PatientLogic patientLogic;

    public PatientAdapter(PatientLogic patientLogic) {
        this.patientLogic = patientLogic;
    }

    @Override
    public List<Patient> search(String search) {
        return patientLogic.findByFamilyName(search);
    }

    public List<PatientNamesOnly> findPatientNamesByFamilyName(String search) {
        return patientLogic.findPatientNamesByFamilyName(search);
    }
}
