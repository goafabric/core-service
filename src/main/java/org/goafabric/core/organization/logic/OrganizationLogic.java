package org.goafabric.core.organization.logic;

import org.goafabric.core.organization.controller.dto.Organization;
import org.goafabric.core.organization.logic.mapper.OrganizationMapper;
import org.goafabric.core.organization.persistence.OrganizationRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class OrganizationLogic {
    private final OrganizationMapper mapper;

    private final OrganizationRepository repository;

    public OrganizationLogic(OrganizationMapper mapper, OrganizationRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public Organization getById(String id) {
        return mapper.map(
                repository.findById(id).orElseThrow());
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public List<Organization> findByName(String name) {
        return mapper.map(
                repository.findByNameStartsWith(name));
    }

    public Organization save(Organization organization) {
        return mapper.map(repository.save(
                mapper.map(organization)));
    }


}
