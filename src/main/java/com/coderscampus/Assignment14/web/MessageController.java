package com.coderscampus.Assignment14.web;

import com.coderscampus.Assignment14.domain.Message;
import com.coderscampus.Assignment14.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MessageController {

	private final MessageService messageService;

	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	@PostMapping("/api/messages")
	@ResponseBody
	public ResponseEntity<Message> postSend (@RequestBody Message message) {
		message.setMessageDate(LocalDateTime.now());
		Message savedMessage = messageService.save(message);
		return ResponseEntity.ok(savedMessage);
	}

	@GetMapping("/api/messages")
	public ResponseEntity<List<Message>> getMessages() {
		List<Message> messages = messageService.findAll();
		return ResponseEntity.ok(messages);
	}

}

