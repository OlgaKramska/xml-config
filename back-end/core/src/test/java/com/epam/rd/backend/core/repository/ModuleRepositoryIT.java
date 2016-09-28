package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.config.JpaConfig;
import com.epam.rd.backend.core.model.Module;
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


@ContextConfiguration("/spring-jpa.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class})
@TestPropertySource(value = "classpath:app-config-test.properties")
@DatabaseSetup(value = "/rd_ms_db_test.xml")
@Transactional
public class ModuleRepositoryIT {

    @Autowired
    private ModuleRepository moduleRepository;
    private Module module;
    private String name;
    private Long duration;
    private String linkToDescription;

    @Before
    public void init() {
        name = "Name";
        duration = 1000L;
        linkToDescription = "Link to Description";
        module = new Module();
        module.setName(name);
        module.setDuration(duration);
        module.setLinkToDescription(linkToDescription);
    }

    @Test
    public void should_create_and_read_module() {
        moduleRepository.save(module);
        Module moduleFromDB = moduleRepository.findOne(module.getId());
        assertNotNull(moduleFromDB.getId());
        assertEquals(name, moduleFromDB.getName());
        assertEquals(duration, moduleFromDB.getDuration());
        assertEquals(linkToDescription, moduleFromDB.getLinkToDescription());
    }

    @Test
    public void should_update_module() {
        moduleRepository.save(module);
        String newName = "New Name";
        module.setName(newName);
        moduleRepository.save(module);
        assertEquals(newName, moduleRepository.findOne(module.getId()).getName());
    }

    @Test
    public void should_delete_module() {
        moduleRepository.save(module);
        moduleRepository.delete(module.getId());
        assertNull(moduleRepository.findOne(module.getId()));
    }
}