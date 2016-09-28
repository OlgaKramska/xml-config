package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.config.JpaConfig;
import com.epam.rd.backend.core.model.PracticalTask;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class PracticalTaskServiceImplTest {


    @Autowired
    private PracticalTaskServiceImpl practicalTaskService;
    private PracticalTask practicalTask;
    private String name;
    private String linkToDescription;
    private Long duration;

    @Before
    public void init() {
        name = "PracticalTaskName4";
        linkToDescription = "LinkToDescription";
        duration = 1000L;
        practicalTask = new PracticalTask();
        practicalTask.setName(name);
        practicalTask.setDuration(duration);
        practicalTask.setLinkToDescription(linkToDescription);
    }

    @Test
    public void should_create_and_read_PracticalTask() throws Exception {
        practicalTaskService.createPracticalTask(practicalTask);
        PracticalTask practicalTaskFromDB = practicalTaskService.getPracticalTaskById(practicalTask.getId());
        assertNotNull(practicalTaskFromDB.getId());
        assertEquals(name, practicalTaskFromDB.getName());
        assertEquals(linkToDescription, practicalTaskFromDB.getLinkToDescription());
        assertEquals(duration, practicalTaskFromDB.getDuration());
    }

    @Test
    public void should_return_list_Program() {
        practicalTask = practicalTaskService.createPracticalTask(practicalTask);
        assertFalse(practicalTaskService.getAllPracticalTask().isEmpty());
    }

    @Test
    public void should_save_and_read_PracticalTask_by_name() {
        practicalTaskService.createPracticalTask(practicalTask);
        PracticalTask practicalTaskFromDB = practicalTaskService.getPracticalTaskByName(practicalTask.getName());
        assertNotNull(practicalTaskFromDB.getId());
        assertEquals(name, practicalTaskFromDB.getName());
        assertEquals(linkToDescription, practicalTaskFromDB.getLinkToDescription());
        assertEquals(duration, practicalTaskFromDB.getDuration());
    }

    @Test
    public void should_create_and_update_program() {
        practicalTask = practicalTaskService.createPracticalTask(practicalTask);
        String newName = "New Name";
        practicalTask.setName(newName);
        practicalTask = practicalTaskService.updatePracticalTask(practicalTask);
        PracticalTask practicalTaskFromDB = practicalTaskService.getPracticalTaskById(practicalTask.getId());
        assertEquals(newName, practicalTaskFromDB.getName());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void should_create_and_delete_Program_by_id() {
        practicalTask = practicalTaskService.createPracticalTask(practicalTask);
        practicalTaskService.deletePracticalTaskById(practicalTask.getId());
        practicalTaskService.deletePracticalTaskById(practicalTask.getId());
    }

    @Test
    public void should_create_and_delete_Program_by_name() {
        practicalTask = practicalTaskService.createPracticalTask(practicalTask);
        practicalTaskService.deletePracticalTaskByName(practicalTask.getName());
        assertNull(practicalTaskService.getPracticalTaskById(practicalTask.getId()));
    }

    @Test
    public void should_not_create_two_Program_with_same_name() {
        practicalTaskService.createPracticalTask(practicalTask);
        practicalTask = practicalTaskService.createPracticalTask(practicalTask);
        //in sql statement create constraint UNIQUE
    }


}