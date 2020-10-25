package io.falcon.assignment.service;

import io.falcon.assignment.dto.MessageDTO;
import io.falcon.assignment.entity.Message;

import java.util.List;

public interface MessageService {

    Message createMessage(MessageDTO messageDTO);
    List<MessageDTO> getAllMessagesWithPalindromeSize();
    Message convertDTOToMessage(MessageDTO messageDTO);
    MessageDTO convertMessageToDTO(Message message);

}