package com.epam.rd.backend.core.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long duration;
    private String typeOfPlace;
    private String device;
    private String linkTopicEpam;
    private String linkYoutube;
    private String linkVideoPortalEpam;
    private String agenda;
    private String description;
    @OneToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    public Lecture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getTypeOfPlace() {
        return typeOfPlace;
    }

    public void setTypeOfPlace(String typeOfPlace) {
        this.typeOfPlace = typeOfPlace;
    }

    public String getDevice() {
        return device;
    }

    public void setDeviceList(String device) {
        this.device = device;
    }

    public String getLinkTopicEpam() {
        return linkTopicEpam;
    }

    public void setLinkTopicEpam(String linkTopicEpam) {
        this.linkTopicEpam = linkTopicEpam;
    }

    public String getLinkYoutube() {
        return linkYoutube;
    }

    public void setLinkYoutube(String linkYoutube) {
        this.linkYoutube = linkYoutube;
    }

    public String getLinkVideoPortalEpam() {
        return linkVideoPortalEpam;
    }

    public void setLinkVideoPortalEpam(String linkVideoPortalEpam) {
        this.linkVideoPortalEpam = linkVideoPortalEpam;
    }

    public String getAgenda() {
        return agenda;
    }

    public void setAgenda(String agenda) {
        this.agenda = agenda;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return Objects.equals(id, lecture.id) &&
                Objects.equals(name, lecture.name) &&
                Objects.equals(duration, lecture.duration) &&
                Objects.equals(typeOfPlace, lecture.typeOfPlace) &&
                Objects.equals(device, lecture.device) &&
                Objects.equals(linkTopicEpam, lecture.linkTopicEpam) &&
                Objects.equals(linkYoutube, lecture.linkYoutube) &&
                Objects.equals(linkVideoPortalEpam, lecture.linkVideoPortalEpam) &&
                Objects.equals(agenda, lecture.agenda) &&
                Objects.equals(description, lecture.description) &&
                Objects.equals(topic, lecture.topic);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, name, duration, typeOfPlace, device, linkTopicEpam, linkYoutube, linkVideoPortalEpam,
                        agenda, description, topic);
    }
}
