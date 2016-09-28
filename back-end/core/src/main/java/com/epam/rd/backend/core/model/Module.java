package com.epam.rd.backend.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long duration;
    private String linkToDescription;
    @ManyToOne()
    @JoinColumn(name = "program_id")
    private Program program;
    @OneToMany
    @JoinColumn(name = "topic_id")
    private List<Topic> topics = new ArrayList<>();

    public Module() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Topic> getTopics() {
        return topics == null ? Collections.unmodifiableList(new ArrayList<>()) : Collections.unmodifiableList(topics);
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
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
        Module module = (Module) o;
        return Objects.equals(id, module.id) &&
                Objects.equals(name, module.name) &&
                Objects.equals(duration, module.duration) &&
                Objects.equals(linkToDescription, module.linkToDescription) &&
                Objects.equals(program, module.program) &&
                Objects.equals(topics, module.topics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, duration, linkToDescription, program, topics);
    }
}
