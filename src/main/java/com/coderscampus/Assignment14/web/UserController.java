package com.coderscampus.Assignment14.web;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.service.ChannelService;
import com.coderscampus.Assignment14.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
	
	private final UserService userService;
	private final ChannelService channelService;

	public UserController(UserService userService, ChannelService channelService) {
		this.userService = userService;
		this.channelService = channelService;
	}

	@GetMapping("/register")
	public String create (ModelMap model) {
		model.put("user", new User());
		return "user/create";
	}

	@PostMapping("/register")
	public String postCreate (User user) {
		if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getName().isEmpty()) {
			return "redirect:/register";
		} else {
			boolean invalidUsername = userService.validateUsername(user.getUsername());
			if (invalidUsername) {
				System.out.println("INVALID USERNAME");
				return "redirect:/register";
			} else {
				channelService.createDefaultUserChannels(user);
				channelService.getAllChannels(user);
				userService.save(user);
			}
		}
		return "redirect:/login";
	}

	@PostMapping("/user/exists")
	@ResponseBody // tells Spring to treat this method like a RestController endpoint (aka RESTful endpoint) so it returns an object and not a view
	public Boolean postExists(@RequestBody User user) {
		User potentialUser = userService.findByUsername(user.getUsername());
		return (potentialUser != null);
	}

	@GetMapping("/login")
	public String login (ModelMap model) {
		model.put("user", new User());
		return "user/login";
	}

	@PostMapping("/login")
	public String postLogin (User user, RedirectAttributes redirectAttributes) {
		if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
			return "redirect:/login";
		} else {
			// Validate user credentials
			User validUser = userService.findByUsername(user.getUsername());
			if (validUser == null || !validUser.getPassword().equals(user.getPassword())) {
				System.out.println("NOT A VALID USER");
				return "redirect:/login"; // Invalid credentials, redirect back to login
			}
			System.out.println("USER IS VALID");
//			userService.setCurrentUser(validUser); // Store the user in the session
			redirectAttributes.addAttribute("userId", validUser.getUserId());
		}
		return "redirect:/user/{userId}"; // Successful login
	}

	@GetMapping("/user/{userId}")
	public String read (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		if (user == null) {
			return "redirect:/users";
		}
		model.put("user", user);
		model.put("channels", user.getChannels());
		return "user/read";
	}

	@GetMapping("/user/{userId}/update")
	public String update (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		if (user == null) {
			return "redirect:/user/{userId}";
		}
		model.put("user", user);
		return "user/update";
	}

	@PostMapping("/user/{userId}/update")
	public String postUpdate(@PathVariable Long userId, User user) {
		User existingUser = userService.findById(userId);
		userService.update(existingUser, user);
		return "redirect:/user/" + user.getUserId() + "/update";
	}



}

