package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.config.JpaConfig;
import com.epam.rd.backend.core.model.Module;
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
public class ModuleServiceImplTest {

    @Autowired
    private ModuleServiceImpl moduleService;
    private Module module;
    private String name;
    private String linkToDescription;
    private Long duration;

    @Before
    public void init() {
        name = "ModuleName";
        linkToDescription = "LinkToDescription";
        duration = 1000L;
        module = new Module();
        module.setName(name);
        module.setDuration(duration);
        module.setLinkToDescription(linkToDescription);
    }

    @Test
    public void should_create_and_read_Module() throws Exception {
        moduleService.createModule(module);
        Module moduleFromDB = moduleService.getModuleById(module.getId());
        assertNotNull(moduleFromDB.getId());
        assertEquals(name, moduleFromDB.getName());
        assertEquals(linkToDescription, moduleFromDB.getLinkToDescription());
        assertEquals(duration, moduleFromDB.getDuration());
    }

    @Test
    public void should_return_list_Module() {
        module = moduleService.createModule(module);
        assertFalse(moduleService.getAllModule().isEmpty());
        assertTrue(moduleService.getAllModule().contains(module));
    }

    @Test
    public void should_save_and_read_Module_by_name() {
        module = moduleService.createModule(module);
        Module moduleFromDB = moduleService.getModuleByName(module.getName());
        assertNotNull(moduleFromDB.getId());
        assertEquals(name, moduleFromDB.getName());
        assertEquals(linkToDescription, moduleFromDB.getLinkToDescription());
        assertEquals(duration, moduleFromDB.getDuration());
    }

    @Test
    public void should_create_and_update_Module() {
        module = moduleService.createModule(module);
        String newName = "New Name";
        module.setName(newName);
        module = moduleService.updateModule(module);
        Module moduleFromDB = moduleService.getModuleById(module.getId());
        assertEquals(newName, moduleFromDB.getName());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void should_create_and_delete_Module_by_id() {
        module = moduleService.createModule(module);
        moduleService.deleteModuleById(module.getId());
        moduleService.deleteModuleById(module.getId());
    }

    @Test
    public void should_create_and_delete_Module_by_name() {
        module = moduleService.createModule(module);
        moduleService.deleteModuleByName(module.getName());
        assertNull(moduleService.getModuleById(module.getId()));
    }

    @Test
    public void should_not_create_two_Module_with_same_name() {
        moduleService.createModule(module);
        module = moduleService.createModule(module);
        //in sql statement create constraint UNIQUE
    }
}