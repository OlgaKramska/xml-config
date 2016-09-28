package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.config.JpaConfig;
import com.epam.rd.backend.core.model.Program;
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
public class ProgramServiceImplIT {

    @Autowired
    private ProgramServiceImpl programService;
    private String name;
    private String linkToDescription;
    private Long duration;
    private Program program;

    @Before
    public void init() {
        name = "Program Name";
        linkToDescription = "Link ToDescription";
        duration = 1000L;
        program = new Program();
        program.setName(name);
        program.setDuration(duration);
        program.setLinkToDescription(linkToDescription);
    }

    @Test
    public void should_save_and_read_Program_by_id() {
        program = programService.createProgram(program);
        Program programFromDB = programService.getProgramById(program.getId());
        assertNotNull(programFromDB.getId());
        assertEquals(name, programFromDB.getName());
        assertEquals(linkToDescription, programFromDB.getLinkToDescription());
        assertEquals(duration, programFromDB.getDuration());
    }

    @Test
    public void should_return_list_Program() {
        program = programService.createProgram(program);
        assertFalse(programService.getAllProgram().isEmpty());
        assertTrue(programService.getAllProgram().contains(program));
    }

    @Test
    public void should_save_and_read_Program_by_name() {
        program = programService.createProgram(program);
        Program programFromDB = programService.getProgramByName(program.getName());
        assertNotNull(programFromDB.getId());
        assertEquals(name, programFromDB.getName());
        assertEquals(linkToDescription, programFromDB.getLinkToDescription());
        assertEquals(duration, programFromDB.getDuration());
    }

    @Test
    public void should_create_and_update_program() {
        program = programService.createProgram(program);
        String newName = "New Name";
        program.setName(newName);
        program = programService.updateProgram(program);
        Program programFromDB = programService.getProgramById(program.getId());
        assertEquals(newName, programFromDB.getName());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void should_create_and_delete_Program_by_id() {
        program = programService.createProgram(program);
        programService.deleteProgramById(program.getId());
        programService.deleteProgramById(program.getId());
    }

    @Test
    public void should_create_and_delete_Program_by_name() {
        program = programService.createProgram(program);
        programService.deleteProgramByName(program.getName());
        assertNull(programService.getProgramById(program.getId()));
    }

    @Test
    public void should_not_create_two_Program_with_same_name() {
        programService.createProgram(program);
        program = programService.createProgram(program);
        //in sql statement create constraint UNIQUE
    }
}