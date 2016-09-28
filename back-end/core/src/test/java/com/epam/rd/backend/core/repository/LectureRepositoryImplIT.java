package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.config.JpaConfig;
import com.epam.rd.backend.core.model.Lecture;
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

@ContextConfiguration("/spring-jpa.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@TestPropertySource(value = "classpath:app-config-test.properties")
@Transactional
public class LectureRepositoryImplIT {

    @Autowired
    private LectureRepository lectureRepository;

    private Lecture lecture;
    private Long duration;
    private String lectureName;
    private String description;

    @Before
    public void init() {
        duration = 1000L;
        lectureName = "LectureName";
        description = "Description";
        lecture = new Lecture();
        lecture.setName(lectureName);
        lecture.setDuration(duration);
        lecture.setDescription(description);
    }

    @Test
    public void should_create_and_read_lecture() throws Exception {
        lectureRepository.save(lecture);
        Lecture lectureFromDB = lectureRepository.findOne(lecture.getId());
        assertNotNull(lectureFromDB.getId());
        assertEquals(duration, lectureFromDB.getDuration());
        assertEquals(lectureName, lectureFromDB.getName());
        assertEquals(description, lectureFromDB.getDescription());
    }

    @Test
    public void should_delete_lecture() throws Exception {
        Lecture lectureFromDB = lectureRepository.save(lecture);
        lectureRepository.delete(lectureFromDB.getId());
        assertNull(lectureRepository.findOne(lectureFromDB.getId()));
    }

    @Test
    public void should_update_lecture() throws Exception {
        String newDescription = "New Description";
        Lecture lectureFromDB = lectureRepository.save(lecture);
        lectureFromDB.setDescription(newDescription);
        lectureFromDB = lectureRepository.save(lectureFromDB);
        assertEquals(newDescription, lectureFromDB.getDescription());
    }

}