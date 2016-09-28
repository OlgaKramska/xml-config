package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.config.JpaConfig;
import com.epam.rd.backend.core.model.Topic;
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
public class TopicServiceImplTest {


    @Autowired
    private TopicServiceImpl topicService;
    private Topic topic;
    private String name;
    private String linkToDescription;
    private Long duration;

    @Before
    public void init() {
        name = "TopicName4";
        linkToDescription = "LinkToDescription";
        duration = 1000L;
        topic = new Topic();
        topic.setName(name);
        topic.setDuration(duration);
        topic.setLinkToDescription(linkToDescription);
    }

    @Test
    public void should_create_and_read_Topic() throws Exception {
        topicService.createTopic(topic);
        Topic topicFromDB = topicService.getTopicById(topic.getId());
        assertNotNull(topicFromDB.getId());
        assertEquals(name, topicFromDB.getName());
        assertEquals(linkToDescription, topicFromDB.getLinkToDescription());
        assertEquals(duration, topicFromDB.getDuration());
    }

    @Test
    public void should_return_list_Topic() {
        assertFalse(topicService.getAllTopic().isEmpty());
    }

    @Test
    public void should_save_and_read_Topic_by_name() {
        topicService.createTopic(topic);
        Topic topicFromDB = topicService.getTopicByName(topic.getName());
        assertNotNull(topicFromDB.getId());
        assertEquals(name, topicFromDB.getName());
        assertEquals(linkToDescription, topicFromDB.getLinkToDescription());
        assertEquals(duration, topicFromDB.getDuration());
    }

    @Test
    public void should_create_and_update_Topic() {
        topic = topicService.createTopic(topic);
        String newName = "New Name";
        topic.setName(newName);
        topic = topicService.updateTopic(topic);
        Topic topicFromDB = topicService.getTopicById(topic.getId());
        assertEquals(newName, topicFromDB.getName());
    }

    @Test
    public void should_create_and_delete_Topic_by_name() {
        topic = topicService.createTopic(topic);
        topicService.deleteTopicByName(topic.getName());
        assertNull(topicService.getTopicById(topic.getId()));
    }

    @Test
    public void should_not_create_two_Topic_with_same_name() {
        topicService.createTopic(topic);
        topic = topicService.createTopic(topic);
        //in sql statement create constraint UNIQUE
    }

}