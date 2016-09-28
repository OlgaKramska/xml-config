package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.model.User;
import com.epam.rd.backend.core.model.UserRole;
import com.epam.rd.backend.core.repository.UserRepository;
import com.epam.rd.backend.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository repository;

    @Override
    public User getUserById(final Integer id) {
        return repository.findOne(id);
    }

    @Override
    public User getUserByEmail(final String email) {
        return repository.findUserByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        final List<User> users = repository.findAll();
        return users.isEmpty() ? Collections.emptyList() : users;
    }

    @Override
    public List<User> getUsersByRoles(final Collection<UserRole> roles) {
        final List<User> users = repository.findUsersByRoles(roles);
        return users.isEmpty() ? Collections.emptyList() : users;
    }

    @Override
    public User addUser(final User user) {
        return repository.save(user);
    }

    @Override
    public User updateUser(final User user) {
        return repository.save(user);
    }

    @Override
    public void deleteUserById(final Integer id) {
        repository.delete(id);
    }

    @Override
    public void deleteUserByEmail(final String email) {
        repository.deleteUserByEmail(email);
    }

    @Override
    public void deleteAllUsers() {
        repository.deleteAll();
    }

    @Override
    public long getUsersCount() {
        return repository.count();
    }
}
