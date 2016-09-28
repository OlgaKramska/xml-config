package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.service.ModuleService;
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
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class ModuleControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ModuleController moduleController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(moduleController).build();
    }

    @Test
    public void should_return_all_module() throws Exception {
        //GIVEN
        final String urlTemplate = "/module";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("ModuleName1")))
                .andExpect(jsonPath("$[0].linkToDescription", is("LinkToDescription1")))
                .andExpect(jsonPath("$[0].duration", is(1000)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("ModuleName2")))
                .andExpect(jsonPath("$[1].linkToDescription", is("LinkToDescription2")))
                .andExpect(jsonPath("$[1].duration", is(2000)));
    }

    @Test
    public void should_return_module_with_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/module/1";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("ModuleName1")))
                .andExpect(jsonPath("$.linkToDescription", is("LinkToDescription1")))
                .andExpect(jsonPath("$.duration", is(1000)));
    }

    @Test
    public void should_return_status_Not_Found_if_moduleId_absent() throws Exception {
        //GIVEN
        final String urlTemplate = "/module/" + Integer.MAX_VALUE;
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void add_module_to_database() throws Exception {
        //GIVEN
        final String urlTemplate = "/module";
        final Module module = new Module();
        module.setName("New Module");
        module.setLinkToDescription("new Link to Description");
        module.setDuration(3000L);
        final ObjectMapper mapper = new ObjectMapper();
        final String moduleJson = mapper.writeValueAsString(module);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(moduleJson));
        //THEN
        final Module newModule = moduleService.getModuleByName("New Module");
        final String value = "http://localhost/module/" + newModule.getId();
        resultActions.andExpect(status().isCreated())
                .andExpect(header().string("location", value));
    }

    @Test
    public void try_add_module_with_existed_name() throws Exception {
        //GIVEN
        final String urlTemplate = "/module/";
        final Module module = new Module();
        module.setName("ModuleName1");
        final ObjectMapper mapper = new ObjectMapper();
        final String moduleJson = mapper.writeValueAsString(module);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(moduleJson));
        //THEN
        resultActions.andExpect(status().isConflict());
    }

    @Test
    public void update_module_in_database_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/module/2";
        final Module module = moduleService.getModuleById(2L);
        module.setName("new Module Name");
        final ObjectMapper mapper = new ObjectMapper();
        final String moduleJson = mapper.writeValueAsString(module);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(moduleJson));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("new Module Name")))
                .andExpect(jsonPath("$.linkToDescription", is("LinkToDescription2")))
                .andExpect(jsonPath("$.duration", is(2000)));
    }

    @Test
    public void try_update_not_existed_module_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/module/" + Integer.MAX_VALUE;
        final Module module = new Module();
        final ObjectMapper mapper = new ObjectMapper();
        final String moduleJson = mapper.writeValueAsString(module);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(moduleJson));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void delete_module_in_database_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/module/2";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void try_delete_module_not_existed_module_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/module/" + Integer.MAX_VALUE;
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void delete_all_module_in_database() throws Exception {
        //GIVEN
        final String urlTemplate = "/module";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void try_delete_all_module_in_empty_database() throws Exception {
        //GIVEN
        final String urlTemplate = "/module";
        //WHEN
        mockMvc.perform(delete(urlTemplate));
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

}