package org.goafabric.core.data.logic;

import org.goafabric.core.data.controller.dto.Patient;
import org.goafabric.core.crossfunctional.DurationLog;
import org.goafabric.core.data.logic.mapper.PatientMapper;
import org.goafabric.core.data.persistence.PatientRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@DurationLog
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
                patientRepository.findByGivenName(givenName));
    }

    public List<Patient> findByFamilyName(String familyName) {
        return patientMapper.map(
                patientRepository.findByFamilyName(familyName));
    }

    public Patient save(Patient patient) {
        return patientMapper.map(patientRepository.save(
                patientMapper.map(patient)));
    }

}
