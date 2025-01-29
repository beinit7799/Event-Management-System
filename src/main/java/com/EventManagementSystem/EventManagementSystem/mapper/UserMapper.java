package com.EventManagementSystem.EventManagementSystem.mapper;

import com.EventManagementSystem.EventManagementSystem.dto.UserDTO;
import com.EventManagementSystem.EventManagementSystem.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "id", ignore = true)
    User convertDtoToEntity(UserDTO userDTO);

    UserDTO convertEntityToDto(User user);
}