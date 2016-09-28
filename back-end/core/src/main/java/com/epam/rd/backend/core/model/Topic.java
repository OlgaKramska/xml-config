package com.epam.rd.backend.core.model;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long duration;
    private String linkToDescription;
    @ManyToOne()
    @JoinColumn(name = "module_id")
    private Module module;
    @OneToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
    @OneToOne
    @JoinColumn(name = "practicalTask_id")
    private PracticalTask practicalTask;

    public Topic() {
    }

    public Lecture getLecture() {
        return lecture;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public PracticalTask getPracticalTask() {
        return practicalTask;
    }

    public void setPracticalTask(PracticalTask practicalTask) {
        this.practicalTask = practicalTask;
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

    public String getLinkToDescription() {
        return linkToDescription;
    }

    public void setLinkToDescription(String linkToDescription) {
        this.linkToDescription = linkToDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return Objects.equals(id, topic.id) &&
                Objects.equals(name, topic.name) &&
                Objects.equals(duration, topic.duration) &&
                Objects.equals(linkToDescription, topic.linkToDescription) &&
                Objects.equals(module, topic.module) &&
                Objects.equals(lecture, topic.lecture) &&
                Objects.equals(practicalTask, topic.practicalTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, linkToDescription, module, lecture, practicalTask);
    }
}
