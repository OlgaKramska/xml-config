package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.model.User;
import com.epam.rd.backend.core.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(final String email);

    List<User> findUsersByRoles(final Collection<UserRole> roles);

    @Transactional
    void deleteUserByEmail(final String email);
}
