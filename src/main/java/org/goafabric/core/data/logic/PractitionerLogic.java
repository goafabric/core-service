package org.goafabric.core.data.logic;

import org.goafabric.core.data.controller.vo.Practitioner;
import org.goafabric.core.crossfunctional.DurationLog;
import org.goafabric.core.data.logic.mapper.PractitionerMapper;
import org.goafabric.core.data.repository.PractitionerRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@DurationLog
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

    public List<Practitioner> findAll() {
        return practitionerMapper.map(
                practitionerRepository.findAll());
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
