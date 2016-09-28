package com.epam.rd.backend.core.repository;

import com.epam.rd.backend.core.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    Module getModuleByName(String name);

    void deleteModuleByName(String name);

}
