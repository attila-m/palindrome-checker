package io.falcon.assignment.controller;

import io.falcon.assignment.dto.MessageDTO;
import io.falcon.assignment.entity.Message;
import io.falcon.assignment.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    private MessageService messageService;
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @RequestMapping(value = "/messages", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<String> newMessage(@Valid @RequestBody MessageDTO messageDTO) throws IllegalArgumentException {
        messagingTemplate.convertAndSend("/topic/messages", messageDTO);

        try {
            Message newMessage = messageService.createMessage(messageDTO);
            LOGGER.info("Message created with id" + newMessage.getId());
            return new ResponseEntity<>("Message created with id: " + newMessage.getId() , HttpStatus.CREATED);
        } catch (DateTimeParseException e) {
            LOGGER.error("Error during message creation: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during message creation: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/messages/palindrome", method = RequestMethod.GET)
    public ResponseEntity<List<MessageDTO>> getAllMessagesWithPalindromeSize() throws IllegalArgumentException {

        try {
            return new ResponseEntity<>(messageService.getAllMessagesWithPalindromeSize(), HttpStatus.OK);
        } catch (DateTimeParseException e) {
            LOGGER.error("Error during message creation: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error during message creation: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            LOGGER.error(errorMessage);
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

}
