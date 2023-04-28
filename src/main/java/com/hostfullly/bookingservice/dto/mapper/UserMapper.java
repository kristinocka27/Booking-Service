package com.hostfullly.bookingservice.dto.mapper;

import com.hostfullly.bookingservice.dto.UserDTO;
import com.hostfullly.bookingservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO mapEntityToUserDTO(User user);

    User mapUserDTOtoEntity(UserDTO userDto);
}
