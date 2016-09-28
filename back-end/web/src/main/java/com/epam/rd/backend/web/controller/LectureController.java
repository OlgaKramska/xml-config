package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.model.Lecture;
import com.epam.rd.backend.core.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class LectureController {


    @Autowired
    private LectureService lectureService;

    @RequestMapping(value = "/lecture",
            method = RequestMethod.GET)
    public ResponseEntity<List<Lecture>> getAllLecture() {
        final List<Lecture> lectures = lectureService.getAllLecture();
        ResponseEntity<List<Lecture>> responseEntity;
        if (lectures.size() == 0) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<>(lectures, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/lecture/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<Lecture> getLectureById(@PathVariable("id") final Long id) {
        final Lecture lecture = lectureService.getLectureById(id);
        ResponseEntity<Lecture> responseEntity;
        if (lecture != null) {
            responseEntity = new ResponseEntity<>(lecture, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/lecture",
            method = RequestMethod.POST)
    public ResponseEntity<Void> addLecture(@RequestBody final Lecture lecture, UriComponentsBuilder builder) {
        ResponseEntity<Void> responseEntity;
        if (isLectureExistByName(lecture)) {
            responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            final Lecture newLecture = lectureService.createLecture(lecture);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/lecture/{id}").buildAndExpand(newLecture.getId()).toUri());
            responseEntity = new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        return responseEntity;
    }

    private boolean isLectureExistByName(final Lecture lecture) {
        return lectureService.getLectureByName(lecture.getName()) != null;
    }

    @RequestMapping(value = "/lecture/{id}",
            method = RequestMethod.PUT)
    public ResponseEntity<Lecture> updateLectureById(@PathVariable("id") final Long id,
                                                     @RequestBody final Lecture lecture) {
        ResponseEntity<Lecture> responseEntity;
        final Lecture currentLecture = lectureService.getLectureById(id);
        if (currentLecture == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            currentLecture.setName(lecture.getName());
            currentLecture.setDescription(lecture.getDescription());
            currentLecture.setDuration(lecture.getDuration());
            final Lecture updateLecture = lectureService.updateLecture(currentLecture);
            responseEntity = new ResponseEntity<>(updateLecture, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/lecture/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteLectureById(@PathVariable("id") final Long id) {
        ResponseEntity<Void> responseEntity;
        final Lecture currentLecture = lectureService.getLectureById(id);
        if (currentLecture == null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            lectureService.deleteLectureById(id);
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/lecture",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAllLectures() {
        ResponseEntity<Void> responseEntity;
        final long countLecture = lectureService.getCountLecture();
        if (countLecture == 0) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            lectureService.deleteAll();
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }
}
