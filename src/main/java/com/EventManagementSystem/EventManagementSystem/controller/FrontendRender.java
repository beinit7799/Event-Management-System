package com.EventManagementSystem.EventManagementSystem.controller;

import com.EventManagementSystem.EventManagementSystem.dto.EventDTO;
import com.EventManagementSystem.EventManagementSystem.model.Event;
import com.EventManagementSystem.EventManagementSystem.model.User;
import com.EventManagementSystem.EventManagementSystem.service.EventService;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;

import com.EventManagementSystem.EventManagementSystem.dto.EventDTO;
import com.EventManagementSystem.EventManagementSystem.dto.UserDTO;
import com.EventManagementSystem.EventManagementSystem.service.EventService;
import com.EventManagementSystem.EventManagementSystem.service.UserService;
import com.EventManagementSystem.EventManagementSystem.utils.MailUtils;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class FrontendRender {
	
    private final UserService userService;
    public FrontendRender(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }
    
    @Autowired
    private EventService eventService;
    @Autowired
    private MailUtils mailUtils;
    private User user;
    
    @GetMapping("/admin/admin-dashboard")
    public String adminDashboard() {
        return "admin/admin__dashboard";
    }
    @GetMapping("/admin/admin-all-users")
    public String adminAllUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers(); // Fetch users from the service
        model.addAttribute("users", users);
        return "admin/admin__all__users";
    }
    @GetMapping("/deleteUser")
   	public String deleteUser(@RequestParam("id") Long id) {
   		userService.deleteUser(id);
   		return "redirect:admin/admin-all-users";	
   	}
    @GetMapping("/admin/admin-all-events")
    public String adminAllEvents(Model model) {
    	List<Event> events = eventService.getAllEvents();
    	model.addAttribute("events",events);
    	return "admin/admin_all_events";
    }
    @GetMapping("/deleteEvent")
	public String deleteEvent(@RequestParam("id") Long id) {
		eventService.deleteEvent(id);
		return "redirect:admin/admin-all-events";	
	}
    @GetMapping("/editEvent")
	public String edit(@RequestParam("id") int id , Model model) {
		
		model.addAttribute("eEdit",eventService.getEventById(id));
		
		return"EventEditForm";
	}
    @PostMapping("/updateEvent")
	public String update(@ModelAttribute Event event) {
		
		eventService.updateDept(event);
		return "redirect:admin/admin-all-events";	
	}
    @GetMapping("/")
    public String home(Model model) {
        List<Event> events = eventService.getEvents();
        model.addAttribute("events", events);
        return "index";
    }
    @GetMapping("/sign-in")
    public String signin() {
        return "signin";
    }
  
    @GetMapping("/sign-up")
    public String signup(Model model) {
        return "signup";
    }
    @PostMapping("/sign-up")
    public String registerUser(@RequestParam String email, @RequestParam String userName,@ModelAttribute("user") UserDTO userDTO) {
        userService.createUser(userDTO); // Save user using service
        String emailContent = "Dear "+userName+",\r\n"
        		+ "\r\n"
        		+ "Thank you for registering with Event Arc! We’re thrilled to have you on board :).";
        mailUtils.sendEmail1(email,"Registered",emailContent);
        return "redirect:/sign-in";
    } 
    @GetMapping("/profile")
	public String getProfile(Model model) {
    	model.addAttribute("userName",user.getUserName());
    	model.addAttribute("email",user.getEmail());
		return"Profile";
	}
}
