package com.epam.rd.backend.core.model;

import java.util.NoSuchElementException;

public enum UserRole {
    MANAGER,
    MENTOR,
    LECTURER,
    MENTEE;

    public static UserRole of(final String name) {
        for (final UserRole userRole : UserRole.values()) {
            if (userRole.name().equalsIgnoreCase(name)) {
                return userRole;
            }
        }
        throw new NoSuchElementException(String.format("User role '%s' does not exist", name));
    }
}