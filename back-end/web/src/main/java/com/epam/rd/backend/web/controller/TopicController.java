package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.backend.core.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class TopicController {


    @Autowired
    private TopicService topicService;

    @RequestMapping(value = "/topic",
            method = RequestMethod.GET)
    public ResponseEntity<List<Topic>> getAllTopic() {
        final List<Topic> topics = topicService.getAllTopic();
        ResponseEntity<List<Topic>> responseEntity;
        if (topics.size() == 0) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<>(topics, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/topic/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<Topic> getTopicById(@PathVariable("id") final Long id) {
        final Topic topic = topicService.getTopicById(id);
        ResponseEntity<Topic> responseEntity;
        if (topic != null) {
            responseEntity = new ResponseEntity<>(topic, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/topic",
            method = RequestMethod.POST)
    public ResponseEntity<Void> addTopic(@RequestBody final Topic topic, UriComponentsBuilder builder) {
        ResponseEntity<Void> responseEntity;
        if (isTopicExistByName(topic)) {
            responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            final Topic newTopic = topicService.createTopic(topic);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/topic/{id}").buildAndExpand(newTopic.getId()).toUri());
            responseEntity = new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        return responseEntity;
    }

    private boolean isTopicExistByName(final Topic topic) {
        return topicService.getTopicByName(topic.getName()) != null;
    }

    @RequestMapping(value = "/topic/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Topic> updateTopicById(@PathVariable("id") final Long id, @RequestBody final Topic topic) {
        ResponseEntity<Topic> responseEntity;
        final Topic currentTopic = topicService.getTopicById(id);
        if (currentTopic == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            currentTopic.setName(topic.getName());
            currentTopic.setLinkToDescription(topic.getLinkToDescription());
            currentTopic.setDuration(topic.getDuration());
            final Topic updateTopic = topicService.updateTopic(currentTopic);
            responseEntity = new ResponseEntity<>(updateTopic, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/topic/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteTopicById(@PathVariable("id") final Long id) {
        ResponseEntity<Void> responseEntity;
        final Topic currentTopic = topicService.getTopicById(id);
        if (currentTopic == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            topicService.deleteTopicById(id);
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/topic",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAllTopics() {
        ResponseEntity<Void> responseEntity;
        final long countTopic = topicService.getCountTopic();
        if (countTopic == 0) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            topicService.deleteAll();
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }
}
