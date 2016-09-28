package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.model.PracticalTask;
import org.springframework.data.repository.CrudRepository;

public interface PracticalTaskRepository extends CrudRepository<PracticalTask, Long> {
    PracticalTask getPracticalTaskByName(String name);

    void deletePracticalTaskByName(String name);
}
