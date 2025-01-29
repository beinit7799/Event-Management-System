package com.EventManagementSystem.EventManagementSystem.controller;

import com.EventManagementSystem.EventManagementSystem.dto.EventDTO;
import com.EventManagementSystem.EventManagementSystem.model.Event;
import com.EventManagementSystem.EventManagementSystem.service.EmailService;
import com.EventManagementSystem.EventManagementSystem.service.EventService;
import com.EventManagementSystem.EventManagementSystem.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserService userService;
    
    @GetMapping("/event")
    public String addEvent() {
    	return"EventCreate";
    }

    @PostMapping("/create")
    public String createEvent(@ModelAttribute Event event) {
       eventService.createEvent(event);
        return"EventCreate";
    }
    
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void>deleteEvent(@PathVariable Long id){
//    	
//    	eventService.deleteEvent(id);
//    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @PostMapping("/express-interest")
    public String expressInterest(Model model) {
        // Use eventId = 1 directly
//        String eventCreatorEmail = eventService.getEventCreatorEmail(); // No need to pass eventId anymore
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        String loggedInUserEmail = userService.getUserEmailByUsername(loggedInUsername); // Assume this method fetches the user's email
    	String eventCreatorEmail =loggedInUserEmail; // No need to pass eventId anymore

        // Compose and send the email as before
		String subject = "User Interested in Your Event";
	    String text = "Hello,\n\nUser with email " + loggedInUserEmail + " has expressed interest in your event!";
		emailService.sendEmail(eventCreatorEmail, subject, text);
	    // Add success message to the model
		return "redirect:/";
    }
    
	


}
