package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.model.User;
import com.epam.rd.backend.core.service.UserService;
import com.epam.rd.backend.web.config.WebConfigTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfigTest.class})
@WebAppConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@TestPropertySource(value = "classpath:app-config-test.properties")
@DatabaseSetup(value = "/rd_ms_db_test.xml")
public class UserControllerIT {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService service;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_return_all_users() throws Exception {
        //GIVEN
        final String urlTemplate = "/users";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Admin")))
                .andExpect(jsonPath("$[0].email", is("admin@mail.com")))
                .andExpect(jsonPath("$[0].password", is("123456")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("User")))
                .andExpect(jsonPath("$[1].email", is("user@mail.com")))
                .andExpect(jsonPath("$[1].password", is("123")));
    }

    @Test
    public void should_return_list_users_by_role() throws Exception {
        //GIVEN
        final String urlTemplate = "/users/manager";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                     .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                     .andExpect(jsonPath("$", hasSize(2)))
                     .andExpect(jsonPath("$[0].id", is(1)))
                     .andExpect(jsonPath("$[0].name", is("Admin")))
                     .andExpect(jsonPath("$[0].email", is("admin@mail.com")))
                     .andExpect(jsonPath("$[0].password", is("123456")))
                     .andExpect(jsonPath("$[1].id", is(3)))
                     .andExpect(jsonPath("$[1].name", is("Oleg")))
                     .andExpect(jsonPath("$[1].email", is("oleg@mail.com")))
                     .andExpect(jsonPath("$[1].password", is("123")));
    }

    @Test
    public void should_return_user_with_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/user/1";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Admin")))
                .andExpect(jsonPath("$.email", is("admin@mail.com")))
                .andExpect(jsonPath("$.password", is("123456")));
    }

    @Test
    public void should_return_status_Not_Found_if_userId_absent() throws Exception {
        //GIVEN
        final String urlTemplate = "/user/" + Integer.MAX_VALUE;
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void add_user() throws Exception {
        //GIVEN
        final String urlTemplate = "/user/";
        final User user = new User();
        user.setName("New User");
        user.setEmail("new_user@mail.com");
        user.setPassword("111");
        final ObjectMapper mapper = new ObjectMapper();
        final String userJson = mapper.writeValueAsString(user);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(userJson));
        //THEN
        final User newUser = service.getUserByEmail("new_user@mail.com");
        final String value = "http://localhost/user/" + newUser.getId();
        resultActions.andExpect(status().isCreated())
                .andExpect(header().string("location", value));
    }

    @Test
    public void try_add_user_with_existed_email() throws Exception {
        //GIVEN
        final String urlTemplate = "/user/";
        final User user = new User();
        user.setEmail("admin@mail.com");
        final ObjectMapper mapper = new ObjectMapper();
        final String userJson = mapper.writeValueAsString(user);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(userJson));
        //THEN
        resultActions.andExpect(status().isConflict());
    }

    @Test
    public void update_user_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/user/2";
        final User user = service.getUserById(2);
        user.setEmail("new_user@mail.com");
        final ObjectMapper mapper = new ObjectMapper();
        final String userJson = mapper.writeValueAsString(user);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(userJson));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("User")))
                .andExpect(jsonPath("$.email", is("new_user@mail.com")))
                .andExpect(jsonPath("$.password", is("123")));
    }

    @Test
    public void try_update_not_existed_user_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/user/" + Integer.MAX_VALUE;
        final User user = new User();
        final ObjectMapper mapper = new ObjectMapper();
        final String userJson = mapper.writeValueAsString(user);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(userJson));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void delete_user_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/user/2";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void try_delete_user_not_existed_user_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/user/" + Integer.MAX_VALUE;
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void delete_all_user() throws Exception {
        //GIVEN
        final String urlTemplate = "/users";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void try_delete_all_user() throws Exception {
        //GIVEN
        final String urlTemplate = "/users";
        //WHEN
        mockMvc.perform(delete(urlTemplate));
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }
}