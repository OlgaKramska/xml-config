package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.model.Lecture;
import org.springframework.data.repository.CrudRepository;

public interface LectureRepository extends CrudRepository<Lecture, Long> {
    Lecture getLectureByName(String name);

    void deleteLectureByName(String name);
}
