package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.config.JpaConfig;
import com.epam.rd.backend.core.model.Lecture;
import com.epam.rd.backend.core.model.PracticalTask;
import com.epam.rd.backend.core.model.Topic;
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
public class TopicRepositoryIT {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private PracticalTaskRepository practicalTaskRepository;

    private String nameTopic;
    private Long durationTopic;
    private String linkToDescriptionOfTopic;
    private Topic topic;

    private String namePracticalTask;
    private Long durationPracticalTask;
    private String linkToDescriptionPracticalTask;
    private PracticalTask practicalTask;

    private String nameLecture;
    private Long durationLecture;
    private String typeOfPlaceLecture;
    private String deviceLecture;
    private String linkTopicEpamLecture;
    private String linkYoutubeLecture;
    private String linkVideoPortalEpamLecture;
    private String agendaLecture;
    private String descriptionLecture;
    private Lecture lecture;


    @Before
    public void init() {
        nameTopic = "Name Topic";
        durationTopic = 1000L;
        linkToDescriptionOfTopic = "LinkToDescriptionOfTopic";
        topic = new Topic();
        topic.setName(nameTopic);
        topic.setDuration(durationTopic);
        topic.setLinkToDescription(linkToDescriptionOfTopic);
        namePracticalTask = "Name Practical Tesk";
        durationPracticalTask = 50000L;
        linkToDescriptionPracticalTask = "Link to description practicalTask";
        practicalTask = new PracticalTask();
        practicalTask.setName(namePracticalTask);
        practicalTask.setDuration(durationPracticalTask);
        practicalTask.setLinkToDescription(linkToDescriptionOfTopic);
        nameLecture = "Name Lecture";
        durationLecture = 50000L;
        typeOfPlaceLecture = "Meeting room";
        deviceLecture = "Laptop";
        linkTopicEpamLecture = "Link To Topic Epam";
        linkYoutubeLecture = "Link youtube";
        linkVideoPortalEpamLecture = "Link video portal epam";
        agendaLecture = "Agenda";
        descriptionLecture = "Description Lecture";
        lecture = new Lecture();
    }

    @Test
    public void should_create_topic() {
        practicalTaskRepository.save(practicalTask);
        lectureRepository.save(lecture);
        topic.setLecture(lecture);
        topic.setPracticalTask(practicalTask);
        topicRepository.save(topic);
        Topic topicFromDB = topicRepository.findOne(topic.getId());
        assertNotNull(topicFromDB.getId());
        assertEquals(nameTopic, topicFromDB.getName());
        assertEquals(lecture, topicFromDB.getLecture());
        assertEquals(practicalTask, topicFromDB.getPracticalTask());
    }

    @Test
    public void should_update_topic() {
        practicalTaskRepository.save(practicalTask);
        lectureRepository.save(lecture);
        topic.setLecture(lecture);
        topic.setPracticalTask(practicalTask);
        topicRepository.save(topic);
        String newLinkToDescriptionTopic = "New Topic Description";
        topic.setLinkToDescription(newLinkToDescriptionTopic);
        lecture.setName("New Lecture Name");
        topic.setLecture(lecture);
        topicRepository.save(topic);
        Topic topicFromDB = topicRepository.findOne(topic.getId());
        assertEquals(newLinkToDescriptionTopic, topicFromDB.getLinkToDescription());
        assertEquals(lecture.getName(), topicFromDB.getLecture().getName());
    }

    @Test
    public void should_delete_topic() {
        topicRepository.save(topic);
        topicRepository.delete(topic.getId());
        assertNull(topicRepository.findOne(topic.getId()));
    }

}