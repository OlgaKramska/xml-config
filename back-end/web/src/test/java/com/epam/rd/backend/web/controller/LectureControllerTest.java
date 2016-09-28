package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.model.Lecture;
import com.epam.rd.backend.core.service.LectureService;
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
public class LectureControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureController lectureController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(lectureController).build();
    }

    @Test
    public void should_return_all_lecture() throws Exception {
        //GIVEN
        final String urlTemplate = "/lecture";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("LectureName1")))
                .andExpect(jsonPath("$[0].description", is("description")))
                .andExpect(jsonPath("$[0].duration", is(1000)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("LectureName2")))
                .andExpect(jsonPath("$[1].description", is("description")))
                .andExpect(jsonPath("$[1].duration", is(2000)));
    }

    @Test
    public void should_return_lecture_with_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/lecture/1";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("LectureName1")))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(jsonPath("$.duration", is(1000)));
    }

    @Test
    public void should_return_status_Not_Found_if_lectureId_absent() throws Exception {
        //GIVEN
        final String urlTemplate = "/lecture/" + Integer.MAX_VALUE;
        //WHEN
        final ResultActions resultActions = mockMvc.perform(get(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void add_lecture_to_database() throws Exception {
        //GIVEN
        final String urlTemplate = "/lecture";
        final Lecture lecture = new Lecture();
        lecture.setName("New Lecture");
        lecture.setDescription("new Link to Description");
        lecture.setDuration(3000L);
        final ObjectMapper mapper = new ObjectMapper();
        final String lectureJson = mapper.writeValueAsString(lecture);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(lectureJson));
        //THEN
        final Lecture newLecture = lectureService.getLectureByName("New Lecture");
        final String value = "http://localhost/lecture/" + newLecture.getId();
        resultActions.andExpect(status().isCreated())
                .andExpect(header().string("location", value));
    }

    @Test
    public void try_add_lecture_with_existed_name() throws Exception {
        //GIVEN
        final String urlTemplate = "/lecture/";
        final Lecture lecture = new Lecture();
        lecture.setName("LectureName1");
        final ObjectMapper mapper = new ObjectMapper();
        final String lectureJson = mapper.writeValueAsString(lecture);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(lectureJson));
        //THEN
        resultActions.andExpect(status().isConflict());
    }

    @Test
    public void update_lecture_in_database_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/lecture/2";
        final Lecture lecture = lectureService.getLectureById(2L);
        lecture.setName("new Lecture Name");
        final ObjectMapper mapper = new ObjectMapper();
        final String lectureJson = mapper.writeValueAsString(lecture);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(lectureJson));
        //THEN
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("new Lecture Name")))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(jsonPath("$.duration", is(2000)));
    }

    @Test
    public void try_update_not_existed_lecture_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/lecture/" + Integer.MAX_VALUE;
        final Lecture lecture = new Lecture();
        final ObjectMapper mapper = new ObjectMapper();
        final String lectureJson = mapper.writeValueAsString(lecture);
        //WHEN
        final ResultActions resultActions = mockMvc.perform(put(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(lectureJson));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void delete_lecture_in_database_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/lecture/2";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void try_delete_lecture_not_existed_lecture_by_id() throws Exception {
        //GIVEN
        final String urlTemplate = "/lecture/" + Integer.MAX_VALUE;
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void delete_all_lecture_in_database() throws Exception {
        //GIVEN
        final String urlTemplate = "/lecture";
        //WHEN
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void try_delete_all_lecture_in_empty_database() throws Exception {
        //GIVEN
        final String urlTemplate = "/lecture";
        //WHEN
        mockMvc.perform(delete(urlTemplate));
        final ResultActions resultActions = mockMvc.perform(delete(urlTemplate));
        //THEN
        resultActions.andExpect(status().isNotFound());
    }


}