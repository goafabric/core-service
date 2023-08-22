package org.goafabric.core.organization.logic;

import org.goafabric.core.organization.controller.vo.Organization;
import org.goafabric.core.organization.logic.mapper.OrganizationMapper;
import org.goafabric.core.organization.repository.OrganizationRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class OrganizationLogic {
    private final OrganizationMapper organizationMapper;

    private final OrganizationRepository organizationRepository;

    public OrganizationLogic(OrganizationMapper organizationMapper, OrganizationRepository organizationRepository) {
        this.organizationMapper = organizationMapper;
        this.organizationRepository = organizationRepository;
    }

    public Organization getById(String id) {
        return organizationMapper.map(
                organizationRepository.findById(id).get());
    }

    public void deleteById(String id) {
        organizationRepository.deleteById(id);
    }

    public List<Organization> findByName(String name) {
        return organizationMapper.map(
                organizationRepository.findByNameStartsWithIgnoreCase(name));
    }

    public Organization save(Organization organization) {
        return organizationMapper.map(organizationRepository.save(
                organizationMapper.map(organization)));
    }


}