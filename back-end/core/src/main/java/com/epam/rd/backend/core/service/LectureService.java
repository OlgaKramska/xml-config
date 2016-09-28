package com.epam.rd.backend.core.service;

import com.epam.rd.backend.core.model.Lecture;

import java.util.List;

public interface LectureService {

    Lecture createLecture(Lecture lecture);

    Lecture updateLecture(Lecture lecture);

    List<Lecture> getAllLecture();

    Lecture getLectureById(Long id);

    Lecture getLectureByName(String name);

    void deleteLectureById(Long iD);

    void deleteLectureByName(String name);

    void deleteAll();

    Long getCountLecture();
}
