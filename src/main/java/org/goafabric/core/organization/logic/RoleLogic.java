package org.goafabric.core.organization.logic;

import org.goafabric.core.organization.controller.dto.Role;
import org.goafabric.core.organization.logic.mapper.RoleMapper;
import org.goafabric.core.organization.persistence.RoleRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class RoleLogic {
    private final RoleMapper mapper;

    private final RoleRepository repository;

    public RoleLogic(RoleMapper mapper, RoleRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public Role getById(String id) {
        return mapper.map(
                repository.findById(id).orElseThrow());
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public List<Role> findByName(String name) {
        return mapper.map(
                repository.findByNameStartsWith(name));
                
    }

    public Role save(Role role) {
        return mapper.map(repository.save(
                mapper.map(role)));
                
    }


}
