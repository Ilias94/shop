package pl.ilias.shop.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import pl.ilias.shop.model.dao.Auditable;
import pl.ilias.shop.model.dto.AuditableDto;
import pl.ilias.shop.security.SecurityUtils;

public interface AuditableMapper<DAO extends Auditable, DTO extends AuditableDto> {
    @AfterMapping
    default void mapAuditableFields(DAO dao, @MappingTarget DTO.AuditableDtoBuilder<?, ?> dto) {
        if (!SecurityUtils.hasRole("ADMIN")) {
            dto.createdBy(null)
                    .createdDate(null)
                    .lastModifiedBy(null)
                    .lastModifiedDate(null);
        }
    }
}
