package com.epam.rd.backend.core.service.impl;

import com.epam.rd.backend.core.model.Module;
import com.epam.rd.backend.core.repository.ModuleRepository;
import com.epam.rd.backend.core.service.ModuleService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    private ModuleRepository moduleRepository;

    @Override
    public Module createModule(Module module) {
        moduleRepository.save(module);
        return module;
    }

    @Override
    public Module updateModule(Module module) {
        moduleRepository.save(module);
        return module;
    }

    @Override
    public List<Module> getAllModule() {
        final List<Module> modules = (List<Module>) moduleRepository.findAll();
        return modules.size() == 0 ? Collections.emptyList() : modules;
    }

    @Override
    public Module getModuleById(Long id) {
        return moduleRepository.findOne(id);
    }

    @Override
    public Module getModuleByName(String name) {
        return moduleRepository.getModuleByName(name);
    }

    @Override
    public void deleteModuleById(Long id) {
        moduleRepository.delete(id);
    }

    @Override
    public void deleteModuleByName(String name) {
        moduleRepository.deleteModuleByName(name);
    }

    @Override
    public void deleteAll() {
        moduleRepository.deleteAll();
    }

    @Override
    public Long getCountModule() {
        return moduleRepository.count();
    }

    public void setModuleRepository(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

}
