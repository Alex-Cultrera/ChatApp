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

	@PostMapping("/api/messages")
	@ResponseBody
	public ResponseEntity<Message> postSend (@RequestBody Message message) {
		Message newMessage = new Message();
		newMessage.setContent(message.getContent());
		newMessage.setMessageDate(LocalDateTime.now());
		newMessage.setSender(message.getSender());
		newMessage.setChannel(message.getChannel());

		Channel existingChannel = channelService.findById(message.getChannel().getChannelId());
		List<Message> channelMessages = existingChannel.getMessages();
		channelMessages.add(newMessage);
		existingChannel.setMessages(channelMessages);
		messageService.save(newMessage);
		channelService.save(existingChannel);
		System.out.println(newMessage);
		return ResponseEntity.ok(newMessage);
	}

	@GetMapping("/api/messages")
	public ResponseEntity<List<Message>> getMessages(@RequestParam Long channelId) {
		Channel theChannel = channelService.findById(channelId);
		List<Message> messages = theChannel.getMessages();

		return ResponseEntity.ok(messages);
	}

}

