package org.goafabric.core.organization.logic;

import org.goafabric.core.organization.controller.vo.User;
import org.goafabric.core.organization.logic.mapper.UserMapper;
import org.goafabric.core.organization.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class UserLogic {
    private final UserMapper mapper;

    private final UserRepository repository;

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


}
