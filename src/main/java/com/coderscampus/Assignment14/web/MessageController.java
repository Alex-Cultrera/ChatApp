package com.coderscampus.Assignment14.web;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.Message;
import com.coderscampus.Assignment14.service.ChannelService;
import com.coderscampus.Assignment14.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MessageController {

	private final MessageService messageService;
	private final ChannelService channelService;

	public MessageController(MessageService messageService, ChannelService channelService) {
		this.messageService = messageService;
		this.channelService = channelService;
	}

//	@PostMapping("/api/messages")
//	@ResponseBody
//	public ResponseEntity<Message> postSend (@RequestBody Message message) {
//		message.setMessageDate(LocalDateTime.now());
//		Channel channel = message.getChannel();
//		message.setChannel(channel);
//		Message savedMessage = messageService.save(message);
//		return ResponseEntity.ok(savedMessage);
//	}

	@PostMapping("/api/messages")
	@ResponseBody
	public ResponseEntity<Message> postSend (@RequestBody Message message) {
		message.setMessageDate(LocalDateTime.now());
		Channel existingChannel = channelService.findById(message.getChannel().getChannelId());
		List<Message> messages = messageService.findByChannelId(existingChannel.getChannelId());
		messages.add(message);
		channelService.save(existingChannel);
		Message savedMessage = messageService.save(message);
		return ResponseEntity.ok(savedMessage);
	}

	@GetMapping("/api/messages")
	public ResponseEntity<List<Message>> getMessages(@RequestParam Long channelId) {
		List<Message> messages = messageService.findByChannelId(channelId);
		return ResponseEntity.ok(messages);
	}

}

