package com.coderscampus.Assignment14.web;

import com.coderscampus.Assignment14.domain.Channel;
import com.coderscampus.Assignment14.domain.Message;
import com.coderscampus.Assignment14.domain.User;
import com.coderscampus.Assignment14.dto.ChannelDTO;
import com.coderscampus.Assignment14.dto.MessageDTO;
import com.coderscampus.Assignment14.dto.UserDTO;
import com.coderscampus.Assignment14.service.ChannelService;
import com.coderscampus.Assignment14.service.MessageService;
import com.coderscampus.Assignment14.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MessageController {

	private final MessageService messageService;
	private final ChannelService channelService;
	private final UserService userService;
	private Long i = 1L;

	public MessageController(MessageService messageService, ChannelService channelService, UserService userService) {
		this.messageService = messageService;
		this.channelService = channelService;
		this.userService = userService;
	}

	@PostMapping("/api/messages")
	@ResponseBody
	public ResponseEntity<MessageDTO> postSend (@RequestBody MessageDTO messageDTO) {
		Message newMessage = new Message();
		newMessage.setMessageId(i);
		i++;
		newMessage.setContent(messageDTO.getContent());
		newMessage.setMessageDate(LocalDateTime.now());

		User sender = userService.findById(messageDTO.getSender().getUserId());
		Channel channel = channelService.findById(messageDTO.getChannel().getChannelId());

		newMessage.setSender(sender);
		newMessage.setChannel(channel);

		messageService.save(newMessage);

		messageDTO.setMessageId(newMessage.getMessageId());
		messageDTO.setMessageDate(newMessage.getMessageDate());
		messageDTO.setContent(newMessage.getContent());
		messageDTO.setSender(new UserDTO(sender.getUserId(), sender.getUsername(), sender.getName()));
		messageDTO.setChannel(new ChannelDTO(channel.getChannelId(), channel.getChannelName()));

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

		System.out.println("Messages fetched: " + channelMessages.size());
		System.out.println("Messages fetched: " + messageDTOs.size());
		return ResponseEntity.ok(messageDTOs);
	}



}

