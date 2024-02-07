package org.goafabric.core.organization.logic;

import org.goafabric.core.organization.controller.dto.Patient;
import org.goafabric.core.organization.logic.mapper.PatientMapper;
import org.goafabric.core.organization.logic.phonetic.ColognePhonetic;
import org.goafabric.core.organization.repository.PatientRepository;
import org.goafabric.core.organization.repository.entity.PatientEo;
import org.goafabric.core.organization.repository.entity.PatientNamesOnly;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class PatientLogic {
    private final ColognePhonetic
            phonetic = new ColognePhonetic();

    private final PatientMapper mapper;

    private final PatientRepository repository;

    public PatientLogic(PatientMapper mapper, PatientRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public Patient getById(String id) {
        return mapper.map(
                repository.findById(id).get());
    }

    public List<Patient> findByGivenName(String givenName) {
        return mapper.map(
                repository.findByGivenNameStartsWithIgnoreCase(givenName));
    }

    public List<Patient> findByFamilyName(String familyName) {
        return mapper.map(
                repository.findByFamilyNameStartsWithIgnoreCase(familyName));
    }

    public Patient save(Patient patient) {
        var patientEo = mapper.map(patient);
        return mapper.map(repository.save(
                new PatientEo(patientEo.getId(), patientEo.getGivenName(), phonetic.encode(patientEo.getGivenName()), patientEo.getFamilyName(), phonetic.encode(patientEo.getFamilyName()),
                        patientEo.getGender(), patientEo.getBirthDate(), patientEo.getAddress(), patientEo.getContactPoint(), patientEo.getVersion())));
    }

    //performance optimazation if we only nead the lastnames, otherwise stupid hibernate will fetch 1:n specialization with n queries
    public List<PatientNamesOnly> findPatientNamesByFamilyName(String search) {
        return repository.findPatientNamesByFamilyNameStartsWithIgnoreCaseOrderByFamilyName(search);
    }

    public List<PatientNamesOnly> findByFamilyNameAndGivenName(String familyName, String givenName) {
        return repository.findByFamilyNameAndGivenName(familyName.toLowerCase(), phonetic.encode(familyName), givenName, phonetic.encode(givenName));
    }

}
