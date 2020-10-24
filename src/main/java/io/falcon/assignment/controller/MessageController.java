package io.falcon.assignment.controller;

import io.falcon.assignment.entity.Message;
import io.falcon.assignment.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MessageController {

    private MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RequestMapping(value = "/messages", consumes = "application/json", method = RequestMethod.POST)
    public Message createMessage(@Valid @RequestBody Message message) throws Exception {
        return messageRepository.save(message);
    }
}
