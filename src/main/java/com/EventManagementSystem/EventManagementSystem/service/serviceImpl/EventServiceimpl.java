package com.EventManagementSystem.EventManagementSystem.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.EventManagementSystem.EventManagementSystem.dto.EventDTO;
import com.EventManagementSystem.EventManagementSystem.dto.UserDTO;
import com.EventManagementSystem.EventManagementSystem.exception.UserNotFoundException;
import com.EventManagementSystem.EventManagementSystem.mapper.EventMapper;
import com.EventManagementSystem.EventManagementSystem.mapper.UserMapper;
import com.EventManagementSystem.EventManagementSystem.model.Event;
import com.EventManagementSystem.EventManagementSystem.model.User;
import com.EventManagementSystem.EventManagementSystem.repository.EventRepository;
import com.EventManagementSystem.EventManagementSystem.repository.UserRepository;
import com.EventManagementSystem.EventManagementSystem.service.EventService;

@Service
public class EventServiceimpl implements EventService{

	
		@Autowired 
		private UserRepository userRepository;
		@Autowired
		private EventRepository eventRepository;


	public void createEvent(Event event) {
//		// Fetch the user by ID
//		Optional<User> userOptional = userRepository.findById(event.getUser().getId());
//
//		if (userOptional.isPresent()) {
//			User user = userOptional.get();
//
//			// Create a new Event object
//			Event e = new Event();	
//			e.setUser(user); // Associate the user
//			e.setEventName(event.getEventName());
//			e.setDescription(event.getDescription());
//			e.setDate(event.getDate());
//			e.setLocation(event.getLocation());
//			e.setParticipants(event.getParticipants());
//			e.setCreatedAt(LocalDateTime.now());
//
//			// Save the event
//			eventRepository.save(event);
//		} else {
//			throw new RuntimeException("User with ID " + event.getUser().getId() + " not found!");
//		}
		eventRepository.save(event);
	}
	@Override
	public List<Event> getEvents() {
		return eventRepository.findAll();
	}


		@Override
		public void deleteEvent(Long id) {
			
			eventRepository.deleteById(id);
			
		}
		@Override
		public Event getEventById(int id) {

			return eventRepository.findById((long) id).get();
		}
		@Override
		public List<Event> getAllEvents() {
//			List<Event> events = eventRepository.findAll();
//	        return events.stream().map(EventMapper.INSTANCE::convertEntityToDto).collect(Collectors.toList());
			return eventRepository.findAll();
		}
		

	    
	     

	public String getEventCreatorEmail() {
		Optional<Event> eventOptional = eventRepository.findById(5L); // Hardcoded eventId = 1
		return eventOptional.map(event -> event.getUser().getEmail()).orElse(null);
	}
	@Override
	public void updateDept(Event event) {
		eventRepository.save(event);
	}
	
	


}
