package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.model.Topic;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Long> {
    Topic getTopicByName(String name);

    void deleteTopicByName(String name);
}
