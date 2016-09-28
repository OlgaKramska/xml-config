package com.epam.rd.backend.core.repository;


import com.epam.rd.backend.core.config.JpaConfig;
import com.epam.rd.backend.core.model.PracticalTask;
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
public class PracticalTaskRepositoryIT {

    @Autowired
    private PracticalTaskRepository practicalTaskRepository;

    private PracticalTask practicalTask;
    private String name;
    private Long duration;
    private String linkToDescription;

    @Before
    public void init() {
        name = "Practical Task";
        duration = 1000L;
        linkToDescription = "LinkToDescription";
        practicalTask = new PracticalTask();
        practicalTask.setName(name);
        practicalTask.setDuration(duration);
        practicalTask.setLinkToDescription(linkToDescription);
    }

    @Test
    public void should_create_practical_task() {
        practicalTaskRepository.save(practicalTask);
        assertNotNull(practicalTask.getId());
        assertEquals(name, practicalTask.getName());
        assertEquals(duration, practicalTask.getDuration());
        assertEquals(linkToDescription, practicalTask.getLinkToDescription());
    }

    @Test
    public void should_delete_practicalTask() {
        practicalTaskRepository.save(practicalTask);
        practicalTaskRepository.delete(practicalTask.getId());
        assertNull(practicalTaskRepository.findOne(practicalTask.getId()));
    }

    @Test
    public void should_update_practicalTask() {
        practicalTaskRepository.save(practicalTask);
        String newName = "New Name";
        practicalTask.setName(newName);
        practicalTaskRepository.save(practicalTask);
        assertEquals(newName, practicalTaskRepository.findOne(practicalTask.getId()).getName());
    }
}