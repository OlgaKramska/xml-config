package com.epam.rd.backend.core.service;

import com.epam.rd.backend.core.model.User;
import com.epam.rd.backend.core.model.UserRole;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User getUserById(Integer id);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<User> getUsersByRoles(Collection<UserRole> roles);

    User addUser(User user);

    User updateUser(User user);

    void deleteUserById(Integer id);

    void deleteUserByEmail(String email);

    void deleteAllUsers();

    long getUsersCount();
}
