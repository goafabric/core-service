package org.goafabric.core.data.logic;

import org.goafabric.core.data.controller.vo.Patient;
import org.goafabric.core.data.logic.mapper.PatientMapper;
import org.goafabric.core.data.repository.PatientRepository;
import org.goafabric.core.extensions.AuditLog;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AuditLog
@Transactional
public class PatientLogic {
    private final PatientMapper patientMapper;

    private final PatientRepository patientRepository;

    public PatientLogic(PatientMapper patientMapper, PatientRepository patientRepository) {
        this.patientMapper = patientMapper;
        this.patientRepository = patientRepository;
    }

    public Patient getById(String id) {
        return patientMapper.map(
                patientRepository.findById(id).get());
    }

    public List<Patient> findAll() {
        return patientMapper.map(
                patientRepository.findAll());
    }

    public List<Patient> findByGivenName(String givenName) {
        return patientMapper.map(
                patientRepository.findByGivenNameStartsWithIgnoreCase(givenName));
    }

    public List<Patient> findByFamilyName(String familyName) {
        return patientMapper.map(
                patientRepository.findByFamilyNameStartsWithIgnoreCase(familyName));
    }

    public Patient save(Patient patient) {
        return patientMapper.map(patientRepository.save(
                patientMapper.map(patient)));
    }

    //performance optimazation if we only nead the lastnames, otherwise stupid hibernate will fetch 1:n relations with n queries
    public List<String> searchLastNames(String search) {
        return patientRepository.findByFamilyNameStartsWithIgnoreCase(search)
                .stream().map(p -> p.familyName).toList();
    }


}
