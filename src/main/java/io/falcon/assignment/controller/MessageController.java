package io.falcon.assignment.controller;

import io.falcon.assignment.dto.MessageDTO;
import io.falcon.assignment.entity.Message;
import io.falcon.assignment.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(value = "/messages", consumes = "application/json", method = RequestMethod.POST)
    public Message newMessage(@Valid @RequestBody MessageDTO messageDTO) throws Exception {
        return messageService.createMessage(messageDTO);
    }

    @RequestMapping(value = "/messages/palindrome", method = RequestMethod.GET)
    public List<MessageDTO> getAllMessagesWithPalindromeSize() {
        return messageService.getAllMessagesWithPalindromeSize();
    }
}
