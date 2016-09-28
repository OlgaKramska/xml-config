package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.config.JpaConfig;
import com.epam.rd.backend.core.model.User;
import com.epam.rd.backend.core.model.UserRole;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
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
@Transactional
public class UserRepositoryTest {
    private static final String NAME = "Bob";
    private static final String EMAIL = "bob@mail.com";
    private static final String PASSWORD = "123456";

    @Autowired
    private UserRepository repository;

    @Test
    public void checked_rows_count_in_users_table() {
        //GIVEN
        final long zeroRowsCount = 0L;
        long currentRowsCount;
        //WHEN
        currentRowsCount = repository.count();
        //THEN
        assertNotEquals(zeroRowsCount, currentRowsCount);
    }

    @Test
    public void should_return_user_with_id_after_added_to_database() {
        //GIVEN
        User user = new User();
        user.setName(NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        //WHEN
        user = repository.save(user);
        //THEN
        assertNotNull(user.getId());
    }

    @Test
    public void should_return_user() {
        //GIVEN
        final int userId = 1;
        //WHEN
        User user = repository.findOne(userId);
        //THEN
        assertNotNull(user);
    }

    @Test
    public void should_update_user() {
        //GIVEN
        final Integer userId = 1;
        final User user = repository.findOne(userId);
        final String newName = "NEW_NAME";
        user.setName(newName);
        //WHEN
        final User updateUser = repository.save(user);
        //THEN
        assertEquals(userId, updateUser.getId());
        assertEquals(newName, updateUser.getName());
    }

    @Test
    public void should_add_user_with_two_roles() {
        //GIVEN
        final Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.MENTOR);
        roles.add(UserRole.LECTURER);
        final User user = new User();
        user.setName("Andy");
        user.setEmail("andy@mail.com");
        user.setPassword("123");
        user.setRoles(roles);
        //WHEN
        repository.save(user);
        final User currentUser = repository.findUserByEmail("andy@mail.com");
        //THEN
        assertTrue(currentUser.getRoles().size() == roles.size());
    }

    @Test
    public void should_return_list_users_by_role() {
        //GIVEN
        final Set<UserRole> roles = Collections.singleton(UserRole.MANAGER);
        //WHEN
        final List<User> users = repository.findUsersByRoles(roles);
        //THEN
        assertTrue(users.size() == 2);
    }

    @Test
    public void should_return_empty_list_users_by_not_existed_role() {
        //GIVEN
        final Set<UserRole> roles = Collections.singleton(UserRole.LECTURER);
        //WHEN
        final List<User> users = repository.findUsersByRoles(roles);
        //THEN
        assertTrue(users.isEmpty());
    }
}