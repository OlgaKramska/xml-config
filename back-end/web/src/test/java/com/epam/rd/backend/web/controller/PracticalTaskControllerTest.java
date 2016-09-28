package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.model.PracticalTask;
import com.epam.rd.backend.core.service.PracticalTaskService;
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
public class PracticalTaskControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private PracticalTaskService practicalTaskService;

    @Autowired
    private PracticalTaskController practicalTaskController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(practicalTaskController).build();
    }

    @Test
    public void should_return_all_practicalTask() throws Exception {
        //GIVEN
        final String urlTemplate = "/practicalTask";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("PracticalTaskName1")))
                .andExpect(jsonPath("$[0].linkToDescription", is("LinkToDescription1")))
                .andExpect(jsonPath("$[0].duration", is(1000)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("PracticalTaskName2")))
                .andExpect(jsonPath("$[1].linkToDescription", is("LinkToDescription2")))
                .andExpect(jsonPath("$[1].duration", is(2000)));
    }

    @Test
    public void should_return_practicalTask_with_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/practicalTask/1";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("PracticalTaskName1")))
                .andExpect(jsonPath("$.linkToDescription", is("LinkToDescription1")))
                .andExpect(jsonPath("$.duration", is(1000)));
    }

    @Test
    public void should_return_status_Not_Found_if_practicalTaskId_absent() throws Exception {
        //GIVEN
        final String urlTemplate = "/practicalTask/" + Integer.MAX_VALUE;
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void add_practicalTask_to_database() throws Exception {
        //GIVEN
        final String urlTemplate = "/practicalTask";
        final PracticalTask practicalTask = new PracticalTask();
        practicalTask.setName("New PracticalTask");
        practicalTask.setLinkToDescription("new Link to Description");
        practicalTask.setDuration(3000L);
        final ObjectMapper mapper = new ObjectMapper();
        final String practicalTaskJson = mapper.writeValueAsString(practicalTask);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(practicalTaskJson));
        //THEN
        final PracticalTask newPracticalTask = practicalTaskService.getPracticalTaskByName("New PracticalTask");
        final String value = "http://localhost/practicalTask/" + newPracticalTask.getId();
        resultActions.andExpect(status().isCreated())
                .andExpect(header().string("location", value));
    }

    @Test
    public void try_add_practicalTask_with_existed_name() throws Exception {
        //GIVEN
        final String urlTemplate = "/practicalTask/";
        final PracticalTask practicalTask = new PracticalTask();
        practicalTask.setName("PracticalTaskName1");
        final ObjectMapper mapper = new ObjectMapper();
        final String practicalTaskJson = mapper.writeValueAsString(practicalTask);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(practicalTaskJson));
        //THEN
        resultActions.andExpect(status().isConflict());
    }

    @Test
    public void update_practicalTask_in_database_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/practicalTask/2";
        final PracticalTask practicalTask = practicalTaskService.getPracticalTaskById(2L);
        practicalTask.setName("new PracticalTask Name");
        final ObjectMapper mapper = new ObjectMapper();
        final String practicalTaskJson = mapper.writeValueAsString(practicalTask);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(practicalTaskJson));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("new PracticalTask Name")))
                .andExpect(jsonPath("$.linkToDescription", is("LinkToDescription2")))
                .andExpect(jsonPath("$.duration", is(2000)));
    }

    @Test
    public void try_update_not_existed_practicalTask_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/practicalTask/" + Integer.MAX_VALUE;
        final PracticalTask practicalTask = new PracticalTask();
        final ObjectMapper mapper = new ObjectMapper();
        final String practicalTaskJson = mapper.writeValueAsString(practicalTask);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(practicalTaskJson));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void delete_practicalTask_in_database_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/practicalTask/2";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void try_delete_practicalTask_not_existed_practicalTask_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/practicalTask/" + Integer.MAX_VALUE;
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void delete_all_practicalTask_in_database() throws Exception {
        //GIVEN
        final String urlTemplate = "/practicalTask";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void try_delete_all_practicalTask_in_empty_database() throws Exception {
        //GIVEN
        final String urlTemplate = "/practicalTask";
        //WHEN
        mockMvc.perform(delete(urlTemplate));
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }


}