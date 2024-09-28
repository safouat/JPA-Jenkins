package org.jpa.jpa.controleur;

import org.jpa.jpa.modele.Module;
import org.jpa.jpa.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @GetMapping("/getall")
    public List<Module> getModules() {
        return moduleService.getAllModules();
    }

    @GetMapping("/{id}")
    public Optional<Module> getModule(@PathVariable int id) {
        return moduleService.getModule(id);
    }

    @PostMapping("/save")
    public void ajouterModule(@RequestBody Module module) {
        moduleService.ajouterModule(module);
    }

    @PutMapping("/edit/{id}")
    public void modifierModule(@PathVariable int id, @RequestBody Module module) {
        moduleService.modifierModule(id, module);
    }

    @DeleteMapping("/delete/{id}")
    public void supprimerModule(@PathVariable int id) {
        moduleService.supprimerModule(id);
    }
}
