package com.tts.TechTalentTwitter.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tts.TechTalentTwitter.model.Tweet;
import com.tts.TechTalentTwitter.model.User;
import com.tts.TechTalentTwitter.service.TweetService;
import com.tts.TechTalentTwitter.service.UserService;

//Create end point which will allow us to view profile for a user with a particular name
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TweetService tweetService;
	
	@GetMapping(value = "/user/{username}")
	public String getUser(@PathVariable(value="username") String username, Model model) {
		//userService find the user based on their username
		User user = userService.findByUsername(username);
		//tweetService find all tweets that are linked to that user
		List<Tweet> tweets = tweetService.findAllByUser(user);
		model.addAttribute("tweetList", tweets);
		model.addAttribute("user", user);
		return "user";
	}
	@GetMapping(value = "/users")
	public String getUsers(Model model) {
	    List<User> users = userService.findAll();
	    model.addAttribute("users", users);
	    //model.addAttribute("tweetList", tweets);
	    SetTweetCounts(users, model);
	    return "users";
	}
	
	private void SetTweetCounts(List<User> users, Model model) {
	    HashMap<String,Integer> tweetCounts = new HashMap<>();
	
	for (User user : users) {
        List<Tweet> tweets = tweetService.findAllByUser(user);
        tweetCounts.put(user.getUsername(), tweets.size());
    }
    model.addAttribute("tweetCounts", tweetCounts);
}

}
