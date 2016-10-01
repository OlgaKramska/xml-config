package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.model.User;
import com.epam.rd.backend.core.model.UserRole;
import com.epam.rd.backend.core.repository.UserRepository;
import com.epam.rd.backend.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    @Override
    public User getUserById(final Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public User getUserByEmail(final String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        final List<User> users = userRepository.findAll();
        return users.isEmpty() ? Collections.emptyList() : users;
    }

    @Override
    public List<User> getUsersByRoles(final Collection<UserRole> roles) {
        final List<User> users = userRepository.findUsersByRoles(roles);
        return users.isEmpty() ? Collections.emptyList() : users;
    }

    @Override
    public User addUser(final User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(final User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(final Integer id) {
        userRepository.delete(id);
    }

    @Override
    public void deleteUserByEmail(final String email) {
        userRepository.deleteUserByEmail(email);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public long getUsersCount() {
        return userRepository.count();
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
