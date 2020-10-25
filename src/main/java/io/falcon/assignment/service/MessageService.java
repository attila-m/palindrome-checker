package io.falcon.assignment.service;

import io.falcon.assignment.dto.MessageDTO;
import io.falcon.assignment.entity.Message;

import java.util.List;

public interface MessageService {

    Message createMessage(MessageDTO messageDTO);
    /**
     * Gets all of the messages persisted in the database,
     * converts them into DTOs and enriches them with the
     * longest palindrome number
     * @return List<MessageDTO>
     */
    List<MessageDTO> getAllMessagesWithPalindromeSize();
    Message convertDTOToMessage(MessageDTO messageDTO);
    MessageDTO convertMessageToDTO(Message message);

}