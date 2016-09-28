package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.config.JpaConfig;
import com.epam.rd.backend.core.model.User;
import com.epam.rd.backend.core.model.UserRole;
import com.epam.rd.backend.core.service.UserService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-jpa.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@TestPropertySource(value = "classpath:app-config-test.properties")
@DatabaseSetup(value = "/rd_ms_db_test.xml")
public class UserServiceImplTest {
    @Autowired
    private UserService service;

    @Test
    public void get_user_by_email() {

        //GIVEN
        final String email = "admin@mail.com";
        //WHEN
        final User user = service.getUserByEmail(email);
        //THEN
        assertEquals(email, user.getEmail());
    }

    @Test
    public void delete_user_by_email() {

        //GIVEN
        final String email = "user@mail.com";
        //WHEN
        service.deleteUserByEmail(email);
        //THEN
        assertNull(service.getUserByEmail(email));
    }

    @Test
    public void should_return_list_users_by_role() {
        //GIVEN
        final Set<UserRole> roles = Collections.singleton(UserRole.MANAGER);
        //WHEN
        final List<User> users = service.getUsersByRoles(roles);
        //THEN
        assertTrue(users.size() == 2);
    }

    @Test
    public void should_return_empty_list_users_by_not_existed_role() {
        //GIVEN
        final Set<UserRole> roles = Collections.singleton(UserRole.LECTURER);
        //WHEN
        final List<User> users = service.getUsersByRoles(roles);
        //THEN
        assertTrue(users.isEmpty());
    }

}