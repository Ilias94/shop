package pl.ilias.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.ilias.shop.model.dao.Product;
import pl.ilias.shop.model.dto.ProductDto;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "producer.id", target = "producerId")
    ProductDto productToDto(Product product);

    Product productDtoToProduct(ProductDto productDto);
}
