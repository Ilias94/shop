package pl.ilias.shop.mapper;

import org.mapstruct.Mapper;
import pl.ilias.shop.model.dao.Address;
import pl.ilias.shop.model.dto.AddressDto;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto addressToDto(Address address);

    Address addressDtoToAddress(AddressDto addressDto);
}
