package com.epam.rd.backend.core.service;

import com.epam.rd.backend.core.model.Module;

import java.util.List;

public interface ModuleService {

    Module createModule(Module module);

    Module updateModule(Module module);

    List<Module> getAllModule();

    Module getModuleById(Long id);

    Module getModuleByName(String name);

    void deleteModuleById(Long iD);

    void deleteModuleByName(String name);

    void deleteAll();

    Long getCountModule();
}
