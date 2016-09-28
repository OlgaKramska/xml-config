package com.epam.rd.backend.core.repository;


import com.epam.rd.backend.core.config.JpaConfig;
import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.model.Program;
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

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

@ContextConfiguration("/spring-jpa.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@TestPropertySource(value = "classpath:app-config-test.properties")
@DatabaseSetup(value = "/rd_ms_db_test.xml")
@Transactional
public class ProgramRepositoryIT {

    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    private Program program;
    private String programName;
    private String programLinkToDescription;
    private Long programDuration;
    private Module module;
    private String moduleName;
    private String moduleLinkToDescription;
    private Long moduleDuration;

    @Before
    public void init() {
        programName = "ProgramName3";
        programLinkToDescription = "Program Link to description3";
        programDuration = 3000L;
        program = new Program();
        program.setName(programName);
        program.setDuration(programDuration);
        program.setLinkToDescription(programLinkToDescription);
        moduleName = "Module Name";
        moduleLinkToDescription = "Module Link To Description";
        moduleDuration = 300L;
        module = new Module();
        module.setName(moduleName);
        module.setDuration(moduleDuration);
        module.setLinkToDescription(moduleLinkToDescription);
        moduleRepository.save(module);
        program.addModule(module);
    }

    @Test
    public void should_create_and_read_program() {
        programRepository.save(program);
        Program programFromDB = programRepository.findOne(program.getId());
        assertNotNull(programFromDB.getId());
        assertEquals(programName, programFromDB.getName());
        assertEquals(programDuration, programFromDB.getDuration());
        assertEquals(programLinkToDescription, programFromDB.getLinkToDescription());
        assertTrue(programFromDB.getModules().contains(module));
        assertEquals(module, programFromDB.getModules().get(0));
    }

    @Test
    public void should_update_program() {
        programRepository.save(program);
        module = program.getModules().get(0);
        String newName = "New Program Name";
        program.setName(newName);
        program.deleteModule(module);
        Module module1 = new Module();
        moduleRepository.save(module1);
        program.addModule(module1);
        programRepository.save(program);
        Program programFromDB = programRepository.findOne(program.getId());
        assertEquals(newName, programFromDB.getName());
        assertFalse(programFromDB.getModules().contains(module));
        assertTrue(programFromDB.getModules().contains(module1));
    }
}