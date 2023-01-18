package pl.ilias.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.ilias.shop.mapper.TemplateMapper;
import pl.ilias.shop.model.dto.TemplateDto;
import pl.ilias.shop.service.TemplateService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/templates")
@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
public class TemplateController {
    private final TemplateService templateService;
    private final TemplateMapper templateMapper;

    @GetMapping
    public Page<TemplateDto> getTemplates(@RequestParam int page, @RequestParam int size) {
        return templateService.getPage(PageRequest.of(page, size)).map(templateMapper::templateToDto);
    }

    @GetMapping("{id}")
    public TemplateDto findTemplateById(@PathVariable Long id) {
        return templateMapper.templateToDto(templateService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TemplateDto saveTemplate(@RequestBody @Valid TemplateDto template) {
        return templateMapper.templateToDto(templateService.save(templateMapper.templateDtoToTemplate(template)));
    }

    @PutMapping("{id}")
    public TemplateDto updateTemplate(@RequestBody @Valid TemplateDto template, @PathVariable Long id) {
        return templateMapper.templateToDto(templateService.update(templateMapper.templateDtoToTemplate(template), id));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTemplateById(@PathVariable Long id) {
        templateService.deleteById(id);
    }
}
