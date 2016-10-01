package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.model.Topic;
import com.epam.rd.backend.core.repository.TopicRepository;
import com.epam.rd.backend.core.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;

    @Override
    public Topic createTopic(Topic topic) {
        topicRepository.save(topic);
        return topic;
    }

    @Override
    public Topic updateTopic(Topic topic) {
        topicRepository.save(topic);
        return topic;
    }

    @Override
    public List<Topic> getAllTopic() {
        final List<Topic> topics = (List<Topic>) topicRepository.findAll();
        return topics.size() == 0 ? Collections.emptyList() : topics;
    }

    @Override
    public Topic getTopicById(Long id) {
        return topicRepository.findOne(id);
    }

    @Override
    public Topic getTopicByName(String name) {
        return topicRepository.getTopicByName(name);
    }

    @Override
    public void deleteTopicById(Long id) {
        topicRepository.delete(id);
    }

    @Override
    public void deleteTopicByName(String name) {
        topicRepository.deleteTopicByName(name);
    }

    @Override
    public void deleteAll() {
        topicRepository.deleteAll();
    }

    @Override
    public Long getCountTopic() {
        return topicRepository.count();
    }

    public void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
}
