package com.coderscampus.Assignment14.web;

import com.coderscampus.Assignment14.domain.Message;
import com.coderscampus.Assignment14.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Controller
public class MessageController {

	private final MessageService messageService;
	private final BlockingQueue<List<Message>> messageQueue = new LinkedBlockingQueue<>();

	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	@PostMapping("/api/messages")
	@ResponseBody
	public ResponseEntity<Message> postSendMessage (@RequestBody Message message) {
		Message savedMessage = messageService.save(message);
		// notify any waiting clients
		messageQueue.add(List.of(savedMessage));
		return ResponseEntity.ok(savedMessage);
	}

	@GetMapping("/api/messages")
	public ResponseEntity<List<Message>> getMessages() {
		List<Message> messages = messageService.findAll();
		return ResponseEntity.ok(messages);
	}

	@GetMapping("/api/messages/long-poll")
	public ResponseEntity<List<Message>> longPoll() {
		List<Message> messages = null;
		try {
			// Wait for new messages to arrive
			messages = messageQueue.take(); // Blocking call
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return ResponseEntity.ok(messages);
	}


}

