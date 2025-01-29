package com.EventManagementSystem.EventManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.EventManagementSystem.EventManagementSystem.service.UserService;
import com.EventManagementSystem.EventManagementSystem.utils.MailUtils;

@Controller
public class ContactController {

	@Autowired
	private MailUtils mailUtils;
	@Autowired
	private UserService userService;
	
	@GetMapping("/contact")
	public String getContactForm() {
		return"ContactForm";
	}
	@PostMapping("/contact")
	public String postContact( @RequestParam("subject") String subject, @RequestParam("message") String message,Model model) {
	   
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        String loggedInUserEmail = userService.getUserEmailByUsername(loggedInUsername); 
    	
    	
		mailUtils.sendEmail("info.eventmagmt@gmail.com", subject, message,loggedInUserEmail);
		model.addAttribute("message","Email send sucessfully");
		
		return"ContactForm";
	}
}
