package com.epam.rd.backend.core.model;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserRoleTest {
    @Test
    public void should_return_user_role_manager() {
        //GIVEN
        final String name = "manager";
        //WHEN
        final UserRole userRole = UserRole.of(name);
        //THEN
        assertEquals(UserRole.MANAGER, userRole);
    }

    @Test(expected = NoSuchElementException.class)
    public void should_return_exception_for_null_argument() {
        //GIVEN
        final String name = null;
        //WHEN
        UserRole.of(name);
        //THEN
        fail("Method should throw NoSuchElementException");
    }

    @Test(expected = NoSuchElementException.class)
    public void should_return_exception_for_not_exist_enum_value() {
        //GIVEN
        final String name = "Not exist User role";
        //WHEN
        UserRole.of(name);
        //THEN
        fail("Method should throw NoSuchElementException");
    }
}