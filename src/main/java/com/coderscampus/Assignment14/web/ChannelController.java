package com.coderscampus.Assignment14.web;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.service.ChannelService;
import com.coderscampus.Assignment14.service.MessageService;
import com.coderscampus.Assignment14.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/user/{userId}/channel/{channelId}")
	public String getChannel(ModelMap model, @PathVariable Long userId, @PathVariable Long channelId) {
		User user = userService.findById(userId);
		Channel channel = channelService.findById(channelId);
		model.put("user", user);
		model.put("channel", channel);
		return "channel/read";
	}



}

