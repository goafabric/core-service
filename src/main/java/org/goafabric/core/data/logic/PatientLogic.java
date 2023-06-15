package org.goafabric.core.data.logic;

import org.goafabric.core.data.controller.dto.Patient;
import org.goafabric.core.data.crossfunctional.DurationLog;
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

    public List<Patient> findByFirstName(String firstName) {
        return patientMapper.map(
                patientRepository.findByFirstName(firstName));
    }

    public List<Patient> findByLastName(String lastName) {
        return patientMapper.map(
                patientRepository.findByLastName(lastName));
    }

    public Patient save(Patient patient) {
        return patientMapper.map(patientRepository.save(
                patientMapper.map(patient)));
    }

}
