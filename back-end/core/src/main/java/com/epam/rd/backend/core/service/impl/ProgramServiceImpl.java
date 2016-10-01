package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.model.Program;
import com.epam.rd.backend.core.repository.ProgramRepository;
import com.epam.rd.backend.core.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    private ProgramRepository programRepository;

    @Override
    public Program createProgram(Program program) {
        if (program.getName() != null) {
            programRepository.save(program);
            return program;
        }
        throw new RuntimeException("Name must by not null!");
    }

    @Override
    public Program updateProgram(Program program) {
        return programRepository.save(program);
    }

    @Override
    public List<Program> getAllProgram() {
        List<Program> programList = (List<Program>) programRepository.findAll();
        return programList.isEmpty() ? Collections.emptyList() : programList;
    }

    @Override
    public Program getProgramById(Long id) {
        return programRepository.findOne(id);
    }

    @Override
    public Program getProgramByName(String name) {
        return programRepository.getProgramByName(name);
    }

    @Override
    public void deleteProgramById(Long id) {
        programRepository.delete(id);

    }

    @Override
    public void deleteProgramByName(String name) {
        programRepository.deleteProgramByName(name);
    }

    @Override
    public void deleteAll() {
        programRepository.deleteAll();
    }

    @Override
    public Long getCountProgram() {
        return programRepository.count();
    }

    public void setProgramRepository(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }
}
