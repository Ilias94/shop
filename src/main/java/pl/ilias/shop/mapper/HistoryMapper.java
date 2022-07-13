package pl.ilias.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.history.Revision;
import pl.ilias.shop.model.dao.Product;
import pl.ilias.shop.model.dao.User;
import pl.ilias.shop.model.dto.ProductDto;
import pl.ilias.shop.model.dto.UserDto;

@Mapper(componentModel = "spring")
public interface HistoryMapper {
    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.firstName", target = "firstName")
    @Mapping(source = "entity.lastName", target = "lastName")
    @Mapping(source = "entity.email", target = "email")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    @Mapping(source = "metadata.revisionType", target = "revisionType")
    UserDto revisionToUserDto(Revision<Integer, User> revision);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source ="entity.name", target = "name")
    @Mapping(source = "entity.description", target = "description")
    @Mapping(source = "entity.price", target = "price")
    @Mapping(source = "entity.quantity", target = "quantity")
    @Mapping(source = "requiredRevisionNumber", target = "revisionNumber")
    @Mapping(source = "metadata.revisionType", target = "revisionType")
    ProductDto revisionToProductDto(Revision<Integer, Product> revision);
}
