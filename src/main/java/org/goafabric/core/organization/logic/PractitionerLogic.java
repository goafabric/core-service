package org.goafabric.core.organization.logic;

import org.goafabric.core.organization.controller.vo.Practitioner;
import org.goafabric.core.organization.logic.mapper.PractitionerMapper;
import org.goafabric.core.organization.repository.PractitionerRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class PractitionerLogic {
    private final PractitionerMapper practitionerMapper;

    private final PractitionerRepository practitionerRepository;

    public PractitionerLogic(PractitionerMapper practitionerMapper, PractitionerRepository practitionerRepository) {
        this.practitionerMapper = practitionerMapper;
        this.practitionerRepository = practitionerRepository;
    }

    public Practitioner getById(String id) {
        return practitionerMapper.map(
                practitionerRepository.findById(id).get());
    }

    public void deleteById(String id) {
        practitionerRepository.deleteById(id);
    }

    public List<Practitioner> findByGivenName(String givenName) {
        return practitionerMapper.map(
                practitionerRepository.findByGivenNameStartsWithIgnoreCase(givenName));
    }

    public List<Practitioner> findByFamilyName(String familyName) {
        return practitionerMapper.map(
                practitionerRepository.findByFamilyNameStartsWithIgnoreCase(familyName));
    }

    public Practitioner save(Practitioner practitioner) {
        return practitionerMapper.map(practitionerRepository.save(
                practitionerMapper.map(practitioner)));
    }

}
