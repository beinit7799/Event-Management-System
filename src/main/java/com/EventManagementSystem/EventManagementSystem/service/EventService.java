package com.EventManagementSystem.EventManagementSystem.service;

import java.util.List;

import com.EventManagementSystem.EventManagementSystem.model.Event;

import org.springframework.stereotype.Service;

import com.EventManagementSystem.EventManagementSystem.dto.EventDTO;
import com.EventManagementSystem.EventManagementSystem.dto.UserDTO;

import java.util.List;

@Service
public interface EventService {
	
    List<Event> getAllEvents();
	List<Event> getEvents();

	void createEvent(Event event);

	void deleteEvent(Long id);


	String getEventCreatorEmail();
	Event getEventById(int id);
	void updateDept(Event dept);
	

}
