package org.goafabric.core.organization.logic;

import org.goafabric.core.organization.controller.vo.Role;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class RoleLogic {

    public Role getById(String id) {
        /*
        return organizationMapper.map(
                organizationRepository.findById(id).get());
                
         */
        return null;
    }

    public void deleteById(String id) {
        //organizationRepository.deleteById(id);
    }

    public List<Role> findByName(String name) {
        /*
        return organizationMapper.map(
                organizationRepository.findByNameStartsWithIgnoreCase(name));
                
         */
        return null;
    }

    public Role save(Role organization) {
        /*
        return organizationMapper.map(organizationRepository.save(
                organizationMapper.map(organization)));
                
         */
        return null;
    }


}
