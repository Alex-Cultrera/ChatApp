package com.codercultrera.ChatApp.web;

import com.codercultrera.ChatApp.domain.User;
import com.codercultrera.ChatApp.service.ChannelService;
import com.codercultrera.ChatApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class UserController {
	
	private final UserService userService;
	private final ChannelService channelService;
	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public UserController(UserService userService, ChannelService channelService) {
		this.userService = userService;
		this.channelService = channelService;
	}

	@GetMapping("")
	public String home (ModelMap model) {
		model.put("user", new User());
		return "user/login";
	}

	@PostMapping("")
	public String postHome (User user, RedirectAttributes redirectAttributes) {
		if (user.getUsername().isEmpty() || encoder.encode(user.getPassword()).isEmpty()) {
			return "redirect:/login";
		} else {
			User validUser = userService.findByUsername(user.getUsername());
			if (validUser == null || !encoder.matches(user.getPassword(), validUser.getPassword())) {
				log.error("Invalid user credentials");
				return "redirect:/login";
			}
			redirectAttributes.addAttribute("userId", validUser.getUserId());
		}
		return "redirect:/user/{userId}";
	}

	@GetMapping("/register")
	public String create (ModelMap model) {
		model.put("user", new User());
		return "user/register";
	}

	@PostMapping("/register")
	public String postCreate (User user) {
		if (user.getUsername().isEmpty() || encoder.encode(user.getPassword()).isEmpty() || user.getName().isEmpty()) {
			return "redirect:/register";
		} else {
			boolean invalidUsername = userService.validateUsername(user.getUsername());
			if (invalidUsername) {
				log.error("The username {} already exists", user.getUsername());
				return "redirect:/register";
			} else {
				if (userService.findAll().isEmpty()) {
					channelService.createDefaultUserChannels(user);
					userService.save(user);
				} else {
					channelService.getAllChannels(user);
					userService.save(user);
				}
			}
		}
		return "redirect:/login";
	}

	@PostMapping("/user/exists")
	@ResponseBody
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
		if (user.getUsername().isEmpty() || encoder.encode(user.getPassword()).isEmpty()) {
			return "redirect:/login";
		} else {
			User validUser = userService.findByUsername(user.getUsername());
			if (validUser == null || !encoder.matches(user.getPassword(), validUser.getPassword())) {
				log.error("Invalid user credentials");
				return "redirect:/login";
			}
			redirectAttributes.addAttribute("userId", validUser.getUserId());
		}
		return "redirect:/user/{userId}";
	}

	@GetMapping("/user/{userId}")
	public String read (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		if (user == null) {
			return "redirect:/users";
		}
		model.put("user", user);
		model.put("channels", user.getChannels());
		return "user/dashboard";
	}

	@GetMapping("/user/{userId}/settings")
	public String update (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		if (user == null) {
			return "redirect:/user/{userId}";
		}
		model.put("user", user);
		return "user/settings";
	}

	@PostMapping("/user/{userId}/settings")
	public String postUpdate(@PathVariable Long userId, User user) {
		User existingUser = userService.findById(userId);
		userService.update(existingUser, user);
		return "redirect:/user/" + user.getUserId() + "/settings";
	}

}

