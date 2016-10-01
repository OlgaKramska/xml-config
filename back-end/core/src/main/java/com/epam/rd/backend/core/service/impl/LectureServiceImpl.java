package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.model.Lecture;
import com.epam.rd.backend.core.repository.LectureRepository;
import com.epam.rd.backend.core.service.LectureService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LectureServiceImpl implements LectureService {

    private LectureRepository lectureRepository;

    @Override
    public Lecture createLecture(Lecture lecture) {
        lectureRepository.save(lecture);
        return lecture;
    }

    @Override
    public Lecture updateLecture(Lecture lecture) {
        lectureRepository.save(lecture);
        return lecture;
    }

    @Override
    public List<Lecture> getAllLecture() {
        final List<Lecture> lectures = (List<Lecture>) lectureRepository.findAll();
        return lectures.size() == 0 ? Collections.emptyList() : lectures;
    }

    @Override
    public Lecture getLectureById(Long id) {
        return lectureRepository.findOne(id);
    }

    @Override
    public Lecture getLectureByName(String name) {
        return lectureRepository.getLectureByName(name);
    }

    @Override
    public void deleteLectureById(Long id) {
        lectureRepository.delete(id);
    }

    @Override
    public void deleteLectureByName(String name) {
        lectureRepository.deleteLectureByName(name);
    }

    @Override
    public void deleteAll() {
        lectureRepository.deleteAll();
    }

    @Override
    public Long getCountLecture() {
        return lectureRepository.count();
    }

    public void setLectureRepository(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }
}
