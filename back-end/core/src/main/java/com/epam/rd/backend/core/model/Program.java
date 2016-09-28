package com.epam.rd.backend.core.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String LinkToDescription;
    private Long duration;
    @OneToMany(mappedBy = "program", fetch = FetchType.EAGER)
    private List<Module> modules = new ArrayList<>();

    public Program() {
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules == null ? Collections.unmodifiableList(new ArrayList<>()) :
                Collections.unmodifiableList(modules);
    }

    public void deleteModule(Module module) {
        modules.remove(module);
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

    public String getLinkToDescription() {
        return LinkToDescription;
    }

    public void setLinkToDescription(String linkToDescription) {
        LinkToDescription = linkToDescription;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Program program = (Program) o;
        return Objects.equals(id, program.id) &&
                Objects.equals(name, program.name) &&
                Objects.equals(LinkToDescription, program.LinkToDescription) &&
                Objects.equals(duration, program.duration) &&
                Objects.equals(modules, program.modules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, LinkToDescription, duration, modules);
    }
}
