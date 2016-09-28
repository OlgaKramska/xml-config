package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    Program getProgramByName(String name);

    void deleteProgramByName(String name);
}