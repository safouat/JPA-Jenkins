package org.jpa.jpa.service;

import org.jpa.jpa.modele.Module;
import org.jpa.jpa.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service

public class ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;

    public void ajouterModule(Module module) {
        moduleRepository.save(module);

    }
}
