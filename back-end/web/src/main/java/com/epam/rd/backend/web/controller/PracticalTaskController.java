package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.model.PracticalTask;
import com.epam.rd.backend.core.service.PracticalTaskService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class PracticalTaskController {


    private PracticalTaskService practicalTaskService;

    @RequestMapping(value = "/practicalTask",
            method = RequestMethod.GET)
    public ResponseEntity<List<PracticalTask>> getAllPracticalTask() {
        final List<PracticalTask> practicalTasks = practicalTaskService.getAllPracticalTask();
        ResponseEntity<List<PracticalTask>> responseEntity;
        if (practicalTasks.size() == 0) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<>(practicalTasks, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/practicalTask/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<PracticalTask> getPracticalTaskById(@PathVariable("id") final Long id) {
        final PracticalTask practicalTask = practicalTaskService.getPracticalTaskById(id);
        ResponseEntity<PracticalTask> responseEntity;
        if (practicalTask != null) {
            responseEntity = new ResponseEntity<>(practicalTask, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/practicalTask",
            method = RequestMethod.POST)
    public ResponseEntity<Void> addPracticalTask(@RequestBody final PracticalTask practicalTask,
                                                 UriComponentsBuilder builder) {
        ResponseEntity<Void> responseEntity;
        if (isPracticalTaskExistByName(practicalTask)) {
            responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            final PracticalTask newPracticalTask = practicalTaskService.createPracticalTask(practicalTask);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/practicalTask/{id}").buildAndExpand(newPracticalTask.getId()).toUri());
            responseEntity = new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        return responseEntity;
    }

    private boolean isPracticalTaskExistByName(final PracticalTask practicalTask) {
        return practicalTaskService.getPracticalTaskByName(practicalTask.getName()) != null;
    }

    @RequestMapping(value = "/practicalTask/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<PracticalTask> updatePracticalTaskById(@PathVariable("id") final Long id,
                                                                 @RequestBody final PracticalTask practicalTask) {
        ResponseEntity<PracticalTask> responseEntity;
        final PracticalTask currentPracticalTask = practicalTaskService.getPracticalTaskById(id);
        if (currentPracticalTask == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            currentPracticalTask.setName(practicalTask.getName());
            currentPracticalTask.setLinkToDescription(practicalTask.getLinkToDescription());
            currentPracticalTask.setDuration(practicalTask.getDuration());
            final PracticalTask updatePracticalTask = practicalTaskService.updatePracticalTask(currentPracticalTask);
            responseEntity = new ResponseEntity<>(updatePracticalTask, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/practicalTask/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePracticalTaskById(@PathVariable("id") final Long id) {
        ResponseEntity<Void> responseEntity;
        final PracticalTask currentPracticalTask = practicalTaskService.getPracticalTaskById(id);
        if (currentPracticalTask == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            practicalTaskService.deletePracticalTaskById(id);
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/practicalTask",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAllPracticalTasks() {
        ResponseEntity<Void> responseEntity;
        final long countPracticalTask = practicalTaskService.getCountPracticalTask();
        if (countPracticalTask == 0) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            practicalTaskService.deleteAll();
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

    public void setPracticalTaskService(PracticalTaskService practicalTaskService) {
        this.practicalTaskService = practicalTaskService;
    }
}
