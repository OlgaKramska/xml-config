package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.config.JpaConfig;
import com.epam.rd.backend.core.model.Lecture;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
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

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-jpa.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@TestPropertySource(value = "classpath:app-config-test.properties")
@DatabaseSetup(value = "/rd_ms_db_test.xml")
@Transactional
public class LectureServiceImplTest {

    @Autowired
    private LectureServiceImpl lectureService;
    private Lecture lecture;
    private String name;
    private Long duration;
    private String description;

    @Before
    public void init() {
        name = "LectureName4";
        duration = 1000L;
        description = "description";
        lecture = new Lecture();
        lecture.setName(name);
        lecture.setDescription(description);
        lecture.setDuration(duration);
    }

    @Test
    public void should_create_and_read_lecture() throws Exception {
        lectureService.createLecture(lecture);
        Lecture lectureFromDB = lectureService.getLectureById(lecture.getId());
        assertNotNull(lectureFromDB.getId());
        assertEquals(name, lectureFromDB.getName());
        assertEquals(description, lectureFromDB.getDescription());
        assertEquals(duration, lectureFromDB.getDuration());
    }

    @Test
    public void should_return_list_lecture() {
        lecture = lectureService.createLecture(lecture);
        assertFalse(lectureService.getAllLecture().isEmpty());
        assertTrue(lectureService.getAllLecture().contains(lecture));
    }

    @Test
    public void should_save_and_read_lecture_by_name() {
        lectureService.createLecture(lecture);
        Lecture lectureFromDB = lectureService.getLectureByName(lecture.getName());
        assertNotNull(lectureFromDB.getId());
        assertEquals(name, lectureFromDB.getName());
        assertEquals(description, lectureFromDB.getDescription());
        assertEquals(duration, lectureFromDB.getDuration());
    }

    @Test
    public void should_create_and_update_program() {
        lecture = lectureService.createLecture(lecture);
        String newName = "New Name";
        lecture.setName(newName);
        lecture = lectureService.updateLecture(lecture);
        Lecture lectureFromDB = lectureService.getLectureById(lecture.getId());
        assertEquals(newName, lectureFromDB.getName());
    }

    @Test
    public void should_create_and_delete_Program_by_id() {
        lecture = lectureService.createLecture(lecture);
        lectureService.deleteLectureById(lecture.getId());
        lectureService.getLectureById(lecture.getId());
    }

    @Test
    public void should_create_and_delete_Program_by_name() {
        lecture = lectureService.createLecture(lecture);
        lectureService.deleteLectureByName(lecture.getName());
        assertNull(lectureService.getLectureById(lecture.getId()));
    }

    @Test
    public void should_not_create_two_Program_with_same_name() {
        lectureService.createLecture(lecture);
        lecture = lectureService.createLecture(lecture);
        //in sql statement create constraint UNIQUE
    }

}