package io.falcon.assignment.controller;

import io.falcon.assignment.dto.MessageDTO;
import io.falcon.assignment.entity.Message;
import io.falcon.assignment.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    private MessageService messageService;
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @RequestMapping(value = "/messages", consumes = "application/json", method = RequestMethod.POST)
    public Message newMessage(@RequestBody MessageDTO messageDTO) throws Exception {
        messagingTemplate.convertAndSend("/topic/messages", messageDTO);
        return messageService.createMessage(messageDTO);
    }

    @RequestMapping(value = "/messages/palindrome", method = RequestMethod.GET)
    public List<MessageDTO> getAllMessagesWithPalindromeSize() {
        return messageService.getAllMessagesWithPalindromeSize();
    }

}
