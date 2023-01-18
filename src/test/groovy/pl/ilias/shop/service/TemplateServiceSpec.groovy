package pl.ilias.shop.service

import org.springframework.data.domain.Pageable
import pl.ilias.shop.model.dao.Template
import pl.ilias.shop.repository.TemplateRepository
import spock.lang.Specification

import javax.persistence.EntityNotFoundException


class TemplateServiceSpec extends Specification {
    def templateRepository = Mock(TemplateRepository)
    def templateService = new TemplateService(templateRepository)

    def 'should get by id'() {
        given:
        def id = 5

        when:
        templateService.getById(id)

        then:
        1 * templateRepository.getReferenceById(id)
        0 * _
    }

    def 'should save template'() {
        given:
        def template = new Template()

        when:
        templateService.save(template)

        then:
        1 * templateRepository.save(template)
        0 * _
    }

    def 'should update template'() {
        given:
        def template = new Template(3, "Welcome","Welcome email", "Welcome user")
        def id = 3
        def dbTemplate = Mock(Template)

        when:
        templateService.update(template, id)

        then:
        1 * templateRepository.getReferenceById(id) >> dbTemplate
        1 * dbTemplate.setName(template.getName())
        1 * dbTemplate.setSubject(template.getSubject())
        1 * dbTemplate.setBody(template.getBody())
        0 * _
    }

    def 'should find by name' () {
        given:
        def name = "Welcome"
        def optionalTemplate = Optional.of(new Template())

        when:
        templateService.findByName(name)

        then:
        1 * templateRepository.findByName(name) >> optionalTemplate
        0 * _
    }

    def 'should not find template by name' () {
        given:
        def name = "Welcome"
        def optionalTemplate = Optional.empty()

        when:
        templateService.findByName(name)

        then:
        1 * templateRepository.findByName(name) >> optionalTemplate
        0 * _

        def e = thrown EntityNotFoundException
        e.message == name
    }

    def 'should get template page' () {
        given:
        def pageable = Mock(Pageable)

        when:
        templateService.getPage(pageable)

        then:
        1 * templateRepository.findAll(pageable)
        0* _
    }
}
