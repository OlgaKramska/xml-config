package com.epam.rd.backend.core.service;

import com.epam.rd.backend.core.model.PracticalTask;

import java.util.List;

public interface PracticalTaskService {

    PracticalTask createPracticalTask(PracticalTask practicalTask);

    PracticalTask updatePracticalTask(PracticalTask practicalTask);

    List<PracticalTask> getAllPracticalTask();

    PracticalTask getPracticalTaskById(Long id);

    PracticalTask getPracticalTaskByName(String name);

    void deletePracticalTaskById(Long iD);

    void deletePracticalTaskByName(String name);

    void deleteAll();

    Long getCountPracticalTask();
}
