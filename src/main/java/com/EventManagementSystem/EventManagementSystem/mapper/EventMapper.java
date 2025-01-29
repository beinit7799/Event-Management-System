package com.EventManagementSystem.EventManagementSystem.mapper;

import com.EventManagementSystem.EventManagementSystem.model.Event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.EventManagementSystem.EventManagementSystem.dto.EventDTO; 
@Mapper
public interface EventMapper {
	
	EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
	
	@Mapping(target = "id", ignore = true)
     Event convertDtoToEntity(EventDTO eventDTO);

    EventDTO convertEntityToDto(Event event);

}
