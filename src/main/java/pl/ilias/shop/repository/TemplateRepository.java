package pl.ilias.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import pl.ilias.shop.model.dao.Template;

import java.util.Optional;


public interface TemplateRepository extends JpaRepository<Template, Long>, RevisionRepository<Template, Long, Integer> {
    Optional<Template> findByName(String name);
}
