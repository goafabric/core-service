package org.goafabric.core.organization.logic;

import org.goafabric.core.organization.controller.vo.Patient;
import org.goafabric.core.organization.logic.mapper.PatientMapper;
import org.goafabric.core.organization.repository.PatientRepository;
import org.goafabric.core.organization.repository.entity.PatientNamesOnly;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class PatientLogic {
    private final PatientMapper patientMapper;

    private final PatientRepository patientRepository;

    public PatientLogic(PatientMapper patientMapper, PatientRepository patientRepository) {
        this.patientMapper = patientMapper;
        this.patientRepository = patientRepository;
    }

    public void deleteById(String id) {
        patientRepository.deleteById(id);
    }

    public Patient getById(String id) {
        return patientMapper.map(
                patientRepository.findById(id).get());
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

    //performance optimazation if we only nead the lastnames, otherwise stupid hibernate will fetch 1:n relation with n queries
    public List<PatientNamesOnly> findPatientNamesByFamilyName(String search) {
        return patientRepository.findPatientNamesByFamilyNameStartsWithIgnoreCaseOrderByFamilyName(search);
    }



}