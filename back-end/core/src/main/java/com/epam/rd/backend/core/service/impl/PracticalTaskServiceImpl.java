package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.model.PracticalTask;
import com.epam.rd.backend.core.repository.PracticalTaskRepository;
import com.epam.rd.backend.core.service.PracticalTaskService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PracticalTaskServiceImpl implements PracticalTaskService {

    private PracticalTaskRepository practicalTaskRepository;

    @Override
    public PracticalTask createPracticalTask(PracticalTask practicalTask) {
        practicalTaskRepository.save(practicalTask);
        return practicalTask;
    }

    @Override
    public PracticalTask updatePracticalTask(PracticalTask practicalTask) {
        practicalTaskRepository.save(practicalTask);
        return practicalTask;
    }

    @Override
    public List<PracticalTask> getAllPracticalTask() {
        final List<PracticalTask> practicalTasks = (List<PracticalTask>) practicalTaskRepository.findAll();
        return practicalTasks.size() == 0 ? Collections.emptyList() : practicalTasks;
    }

    @Override
    public PracticalTask getPracticalTaskById(Long id) {
        return practicalTaskRepository.findOne(id);
    }

    @Override
    public PracticalTask getPracticalTaskByName(String name) {
        return practicalTaskRepository.getPracticalTaskByName(name);
    }

    @Override
    public void deletePracticalTaskById(Long id) {
        practicalTaskRepository.delete(id);
    }

    @Override
    public void deletePracticalTaskByName(String name) {
        practicalTaskRepository.deletePracticalTaskByName(name);
    }

    @Override
    public void deleteAll() {
        practicalTaskRepository.deleteAll();
    }

    @Override
    public Long getCountPracticalTask() {
        return practicalTaskRepository.count();
    }

    public void setPracticalTaskRepository(PracticalTaskRepository practicalTaskRepository) {
        this.practicalTaskRepository = practicalTaskRepository;
    }
}
