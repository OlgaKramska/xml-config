package com.epam.rd.backend.web.controller;


import com.epam.rd.backend.core.model.Program;
import com.epam.rd.backend.core.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @RequestMapping(value = "/program",
            method = RequestMethod.GET)
    public ResponseEntity<List<Program>> getAllProgram() {
        final List<Program> programs = programService.getAllProgram();
        ResponseEntity<List<Program>> responseEntity;
        if (programs.size() == 0) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<>(programs, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/program/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<Program> getProgramById(@PathVariable("id") final Long id) {
        final Program program = programService.getProgramById(id);
        ResponseEntity<Program> responseEntity;
        if (program != null) {
            responseEntity = new ResponseEntity<>(program, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/program",
            method = RequestMethod.POST)
    public ResponseEntity<Void> addProgram(@RequestBody final Program program, UriComponentsBuilder builder) {
        ResponseEntity<Void> responseEntity;
        if (isProgramExistByName(program)) {
            responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            final Program newProgram = programService.createProgram(program);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/program/{id}").buildAndExpand(newProgram.getId()).toUri());
            responseEntity = new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        return responseEntity;
    }

    private boolean isProgramExistByName(final Program program) {
        return programService.getProgramByName(program.getName()) != null;
    }

    @RequestMapping(value = "/program/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Program> updateProgramById(@PathVariable("id") final Long id,
                                                     @RequestBody final Program program) {
        ResponseEntity<Program> responseEntity;
        final Program currentProgram = programService.getProgramById(id);
        if (currentProgram == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            currentProgram.setName(program.getName());
            currentProgram.setLinkToDescription(program.getLinkToDescription());
            currentProgram.setDuration(program.getDuration());
            final Program updateProgram = programService.updateProgram(currentProgram);
            responseEntity = new ResponseEntity<>(updateProgram, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/program/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteProgramById(@PathVariable("id") final Long id) {
        ResponseEntity<Void> responseEntity;
        final Program currentProgram = programService.getProgramById(id);
        if (currentProgram == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            programService.deleteProgramById(id);
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/program",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAllPrograms() {
        ResponseEntity<Void> responseEntity;
        final long programsCount = programService.getCountProgram();
        if (programsCount == 0) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            programService.deleteAll();
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }
}
