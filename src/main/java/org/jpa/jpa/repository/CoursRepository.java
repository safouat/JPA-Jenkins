package org.jpa.jpa.repository;

import org.jpa.jpa.modele.Cours;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CoursRepository extends CrudRepository<Cours, Integer>{
//en plus des méthodes implémentées par défaut au niveau de la classe CrudRepository, nous pouvons //définir d’autres en cas de besoin tel que c’est le cas ici
    public List<Cours> findByModuleId(Integer moduleId);
}