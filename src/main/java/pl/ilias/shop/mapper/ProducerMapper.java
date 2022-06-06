package pl.ilias.shop.mapper;

import org.mapstruct.Mapper;
import pl.ilias.shop.model.dao.Producer;
import pl.ilias.shop.model.dto.ProducerDto;
import pl.ilias.shop.model.dto.ProductDto;

@Mapper(componentModel = "spring")
public interface ProducerMapper {
    ProducerDto producerToDto(Producer producer);

    Producer producerDtoToProducer(ProductDto productDto);
}
