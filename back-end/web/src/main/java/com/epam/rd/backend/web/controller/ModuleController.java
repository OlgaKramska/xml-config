package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "/module",
            method = RequestMethod.GET)
    public ResponseEntity<List<Module>> getAllModule() {
        final List<Module> modules = moduleService.getAllModule();
        ResponseEntity<List<Module>> responseEntity;
        if (modules.size() == 0) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<>(modules, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/module/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<Module> getModuleById(@PathVariable("id") final Long id) {
        final Module module = moduleService.getModuleById(id);
        ResponseEntity<Module> responseEntity;
        if (module != null) {
            responseEntity = new ResponseEntity<>(module, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/module",
            method = RequestMethod.POST)
    public ResponseEntity<Void> addModule(@RequestBody final Module module, UriComponentsBuilder builder) {
        ResponseEntity<Void> responseEntity;
        if (isModuleExistByName(module)) {
            responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            final Module newModule = moduleService.createModule(module);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/module/{id}").buildAndExpand(newModule.getId()).toUri());
            responseEntity = new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        return responseEntity;
    }

    private boolean isModuleExistByName(final Module module) {
        return moduleService.getModuleByName(module.getName()) != null;
    }

    @RequestMapping(value = "/module/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Module> updateModuleById(@PathVariable("id") final Long id,
                                                   @RequestBody final Module module) {
        ResponseEntity<Module> responseEntity;
        final Module currentModule = moduleService.getModuleById(id);
        if (currentModule == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            currentModule.setName(module.getName());
            currentModule.setLinkToDescription(module.getLinkToDescription());
            currentModule.setDuration(module.getDuration());
            final Module updateModule = moduleService.updateModule(currentModule);
            responseEntity = new ResponseEntity<>(updateModule, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/module/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteModuleById(@PathVariable("id") final Long id) {
        ResponseEntity<Void> responseEntity;
        final Module currentModule = moduleService.getModuleById(id);
        if (currentModule == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            moduleService.deleteModuleById(id);
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/module",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAllModules() {
        ResponseEntity<Void> responseEntity;
        final long countModule = moduleService.getCountModule();
        if (countModule == 0) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            moduleService.deleteAll();
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }
}
