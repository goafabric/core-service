package org.goafabric.core.organization.logic;

import org.goafabric.core.organization.controller.vo.User;
import org.goafabric.core.organization.logic.mapper.UserMapper;
import org.goafabric.core.organization.repository.RoleRepository;
import org.goafabric.core.organization.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class UserLogic {
    private final UserMapper mapper;

    private final UserRepository repository;

    private final RoleRepository roleRepository;

    public UserLogic(UserMapper mapper, UserRepository repository, RoleRepository roleRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.roleRepository = roleRepository;
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
        var userEo = mapper.map(user);
        userEo.roles = user.roles().stream().map(role -> roleRepository.findById(role.id()).get()).toList();
        return mapper.map(repository.save(userEo));
    }


}
