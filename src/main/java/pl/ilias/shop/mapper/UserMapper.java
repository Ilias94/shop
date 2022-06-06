package pl.ilias.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.ilias.shop.model.dao.User;
import pl.ilias.shop.model.dto.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserDto userToDto(User user);

    User userDtoToUser(UserDto userDto);
}
