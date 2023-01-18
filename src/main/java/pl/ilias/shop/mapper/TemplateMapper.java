package pl.ilias.shop.mapper;

import org.mapstruct.Mapper;
import pl.ilias.shop.model.dao.Template;
import pl.ilias.shop.model.dto.TemplateDto;

@Mapper(componentModel = "spring")
public interface TemplateMapper {
    TemplateDto templateToDto(Template template);

    Template templateDtoToTemplate(TemplateDto templateDto);
}
