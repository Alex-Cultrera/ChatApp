package com.codercultrera.ChatApp.web;

import com.codercultrera.ChatApp.domain.Channel;
import com.codercultrera.ChatApp.domain.User;
import com.codercultrera.ChatApp.service.ChannelService;
import com.codercultrera.ChatApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class ChannelController {

	private final UserService userService;
	private final ChannelService channelService;

	public ChannelController(UserService userService, ChannelService channelService) {
		this.userService = userService;
		this.channelService = channelService;
	}

	@PostMapping("/channel/exists")
	@ResponseBody
	public Boolean postExists(@RequestBody Channel channel) {
		Channel potentialChannel = channelService.findByChannelName(channel.getChannelName());
		return (potentialChannel != null);
	}

	@GetMapping("/user/{userId}/channel/{channelId}")
	public String read(ModelMap model, @PathVariable Long userId, @PathVariable Long channelId) {
		User user = userService.findById(userId);
		Channel channel = channelService.findById(channelId);
		model.put("user", user);
		model.put("channel", channel);
		return "user/channel";
	}

	@PostMapping("/api/channels")
	@ResponseBody
	public ResponseEntity<Channel> create(@RequestBody Channel channel) {
		if (channel.getChannelName().isEmpty()) {
			return null;
		} else {
			boolean invalidChannelName = channelService.validateChannelName(channel.getChannelName());
			if (invalidChannelName) {
				log.error("The channel name {} already exists", channel.getChannelName());
				return null;
			} else {
				List<User> users = userService.findAll();
				Channel createdChannel = channelService.createChannel(channel, users);
				createdChannel.setCreatedBy(channel.getCreatedBy());
				return ResponseEntity.status(HttpStatus.CREATED).body(createdChannel);
			}
		}
	}

	@GetMapping("/api/channels")
	@ResponseBody
	public ResponseEntity<List<Channel>> getAllChannels() {
		List<Channel> channels = channelService.findAll();
		return ResponseEntity.ok(channels);
	}

}
