package pl.ilias.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.ilias.shop.model.dao.Template;
import pl.ilias.shop.repository.TemplateRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;

    public Template findByName(String name) {
        return templateRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException(name));
    }

    public Template save(Template template) {
        templateRepository.save(template);
        return template;
    }

    public Template getById(Long id) {
        return templateRepository.getReferenceById(id);
    }

    public Template update(Template template, Long id) {
        Template templateDb = getById(id);
        templateDb.setName(template.getName());
        templateDb.setSubject(template.getSubject());
        templateDb.setBody(template.getBody());
        return templateDb;
    }

    public void deleteById(Long id) {
        templateRepository.deleteById(id);
    }

    public Page<Template> getPage(Pageable pageable) {
        return templateRepository.findAll(pageable);
    }

    public List<Template> getTemplates() {
        return templateRepository.findAll();
    }
}
