package com.coderscampus.Assignment14.web;

import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.service.ChannelService;
import com.coderscampus.Assignment14.service.MessageService;
import com.coderscampus.Assignment14.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ChannelController {

	private final UserService userService;
	private final ChannelService channelService;
	private final MessageService messageService;

	public ChannelController(UserService userService, ChannelService channelService, MessageService messageService) {
		this.userService = userService;
		this.channelService = channelService;
		this.messageService = messageService;
	}

	@GetMapping("/register")
	public String getCreateUser (ModelMap model) {
		model.put("user", new User());
		return "user/create";
	}

	@PostMapping("/register")
	public String postCreateUser (User user) {
		if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getName().isEmpty()) {
			return "redirect:/register";
		} else {
			boolean invalidUsername = userService.validateUsername(user.getUsername());
			if (invalidUsername) {
				System.out.println("INVALID USERNAME");
				return "redirect:/register";
			} else {
				channelService.createDefaultUserChannels(user);
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
	public String getLogin (ModelMap model) {
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
			redirectAttributes.addAttribute("userId", validUser.getUserId());
		}
		return "redirect:/user/{userId}/dashboard"; // Successful login
	}

	@GetMapping("/user/{userId}/dashboard")
	public String getDashboard (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		if (user == null) {
			return "redirect:/users";
		}
		model.put("user", user);
		model.put("channels", user.getChannels());
		return "user/dashboard";
	}

	@GetMapping("/user/{userId}")
	public String editUser (ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		if (user == null) {
			return "redirect:/user/{userId}/dashboard";
		}
		model.put("user", user);
		return "user/editUserProfile";
	}

	@PostMapping("/user/{userId}/update")
	public String updateUser(@PathVariable Long userId, User user) {
		User existingUser = userService.findById(userId);
		userService.update(existingUser, user);
		return "redirect:/user/" + user.getUserId();
	}
//
//	@PostMapping("/users/{userId}/delete")
//	public String deleteUser(@PathVariable Long userId) {
//		userService.delete(userId);
//		return "redirect:/users";
//	}

	//	@GetMapping("/users")
//	public String getAllUsers(ModelMap model) {
//		Set<User> users = userService.findAll();
//		model.put("users", users);
//		if (users.size() == 1) {
//			model.put("user", users.iterator().next());
//		}
//		return "users";
////	}
//
//	@PostMapping("/users")
//	public String postAllUsers(ModelMap model) {
//		return "redirect:/users";
//	}


}

