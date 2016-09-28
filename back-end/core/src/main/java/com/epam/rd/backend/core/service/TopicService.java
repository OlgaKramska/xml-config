package com.epam.rd.backend.core.service;

import com.epam.rd.backend.core.model.Topic;

import java.util.List;

public interface TopicService {

    Topic createTopic(Topic topic);

    Topic updateTopic(Topic topic);

    List<Topic> getAllTopic();

    Topic getTopicById(Long id);

    Topic getTopicByName(String name);

    void deleteTopicById(Long iD);

    void deleteTopicByName(String name);

    void deleteAll();

    Long getCountTopic();
}
