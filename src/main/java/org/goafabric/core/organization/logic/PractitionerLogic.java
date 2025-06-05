package org.goafabric.core.organization.logic;

import org.goafabric.core.organization.controller.dto.Practitioner;
import org.goafabric.core.organization.logic.mapper.PractitionerMapper;
import org.goafabric.core.organization.persistence.PractitionerRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class PractitionerLogic {
    private final PractitionerMapper mapper;

    private final PractitionerRepository repository;

    public PractitionerLogic(PractitionerMapper mapper, PractitionerRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public Practitioner getById(String id) {
        return mapper.map(
                repository.findById(id).orElseThrow());
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public List<Practitioner> findByGivenName(String givenName) {
        return mapper.map(
                repository.findByGivenNameStartsWithIgnoreCase(givenName));
    }

    public List<Practitioner> findByFamilyName(String familyName) {
        return mapper.map(
                repository.findByFamilyNameStartsWithIgnoreCase(familyName));
    }

    public Practitioner save(Practitioner practitioner) {
        return mapper.map(repository.save(
                mapper.map(practitioner)));
    }

}
