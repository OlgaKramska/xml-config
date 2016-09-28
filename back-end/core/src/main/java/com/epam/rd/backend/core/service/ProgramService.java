package com.epam.rd.backend.core.service;

import com.epam.rd.backend.core.model.Program;

import java.util.List;

public interface ProgramService {

    Program createProgram(Program program);

    Program updateProgram(Program program);

    List<Program> getAllProgram();

    Program getProgramById(Long id);

    Program getProgramByName(String name);

    void deleteProgramById(Long iD);

    void deleteProgramByName(String name);

    void deleteAll();

    Long getCountProgram();

}
