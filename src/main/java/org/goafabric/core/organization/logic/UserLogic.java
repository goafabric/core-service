package org.goafabric.core.organization.logic;

import org.goafabric.core.extensions.UserContext;
import org.goafabric.core.organization.controller.dto.Permission;
import org.goafabric.core.organization.controller.dto.Role;
import org.goafabric.core.organization.controller.dto.User;
import org.goafabric.core.organization.controller.dto.UserInfo;
import org.goafabric.core.organization.controller.dto.types.PermissionCategory;
import org.goafabric.core.organization.controller.dto.types.PermissionType;
import org.goafabric.core.organization.logic.mapper.UserMapper;
import org.goafabric.core.organization.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class UserLogic {
    private final UserMapper mapper;

    private final UserRepository repository;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public UserLogic(UserMapper mapper, UserRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public User getById(String id) {
        return mapper.map(
                repository.findById(id).get());
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public List<User> findByName(String name) {
        return mapper.map(
                repository.findByNameStartsWithIgnoreCase(name));
                
    }


    public User save(User user) {
        return mapper.map(repository.save(
                mapper.map(user)));
                
    }

    public Boolean hasPermission(String name, PermissionCategory category, PermissionType type) {
        var user = mapper.map(repository.findByName(name));
        if (user.size() == 1) {
            var roles = user.getFirst().roles();
            for (Role role : roles) {
                for (Permission permission : role.permissions()) {
                    if (permission.category().equals(category) && (permission.type().equals(type))) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public UserInfo getUserInfo() {
        return new UserInfo(UserContext.getUserName(), UserContext.getTenantId());
    }
}
