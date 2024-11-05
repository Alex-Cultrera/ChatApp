package com.codercultrera.ChatApp.web;

import com.codercultrera.ChatApp.domain.Message;
import com.codercultrera.ChatApp.dto.ChannelDTO;
import com.codercultrera.ChatApp.dto.MessageDTO;
import com.codercultrera.ChatApp.dto.UserDTO;
import com.codercultrera.ChatApp.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MessageController {

	private final MessageService messageService;
	private Long i = 1L;

	public MessageController(MessageService messageService) {
		this.messageService = messageService;
	}

	@PostMapping("/api/messages")
	@ResponseBody
	public ResponseEntity<MessageDTO> postSend (@RequestBody MessageDTO messageDTO) {
		messageService.recordMessage(i,  messageDTO);
		i++;
		return ResponseEntity.ok(messageDTO);
	}

	@GetMapping("/api/messages")
	@ResponseBody
	public ResponseEntity<List<MessageDTO>> getMessages(@RequestParam Long channelId) {
		List<Message> channelMessages = messageService.findByChannelId(channelId);
		List<MessageDTO> messageDTOs = channelMessages.stream().map(message -> {
			MessageDTO dto = new MessageDTO();
			dto.setMessageId(message.getMessageId());
			dto.setMessageDate(message.getMessageDate());
			dto.setContent(message.getContent());

			UserDTO senderDTO = new UserDTO();
			senderDTO.setUserId(message.getSender().getUserId());
			senderDTO.setUsername(message.getSender().getUsername());
			senderDTO.setName(message.getSender().getName());
			dto.setSender(senderDTO);

			ChannelDTO channelDTO = new ChannelDTO();
			channelDTO.setChannelId(message.getChannel().getChannelId());
			channelDTO.setChannelName(message.getChannel().getChannelName());

			dto.setChannel(channelDTO);
			return dto;
		}).collect(Collectors.toList());

		return ResponseEntity.ok(messageDTOs);
	}



}

