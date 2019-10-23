package com.tts.TechTalentTwitter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.TechTalentTwitter.model.User;
import com.tts.TechTalentTwitter.service.UserService;

@Controller
public class AuthorizationController {

	//Create UserService: serve up login page
	@Autowired
	private UserService userService;
	
	@GetMapping
	public String login() {
		return"login";
	}
	
	//Methods to serve up the signup page (GET)
	@GetMapping(value="/signup")
	public String registration(Model model){
	    User user = new User();
	    model.addAttribute("user", user);
	    return "registration";
	}
	
	//handle form submissions when user submit on signup page(POST)
	@PostMapping(value = "/signup")
	public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {
	    User userExists = userService.findByUsername(user.getUsername());
	    if (userExists != null) {
	        bindingResult.rejectValue("username", "error.user", "Username is already taken");
	    }
	    if (!bindingResult.hasErrors()) {
	        userService.saveNewUser(user);
	        model.addAttribute("success", "Sign up successful!");
	        model.addAttribute("user", new User());
	    }
	    return "registration";
	}
}
