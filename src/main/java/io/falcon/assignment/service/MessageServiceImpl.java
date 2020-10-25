package io.falcon.assignment.service;

import io.falcon.assignment.dto.MessageDTO;
import io.falcon.assignment.entity.Message;
import io.falcon.assignment.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message createMessage(MessageDTO messageDTO) {
        return messageRepository.save(convertDTOToMessage(messageDTO));
    }

    @Override
    public List<MessageDTO> getAllMessagesWithPalindromeSize() {
        return getEnrichedMessages();
    }

    @Override
    public Message convertDTOToMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setTimestamp(messageDTO.getTimestamp());
        return message;
    }

    @Override
    public MessageDTO convertMessageToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent(message.getContent());
        messageDTO.setTimestamp(message.getTimestamp());
        return messageDTO;
    }

    private List<MessageDTO> getEnrichedMessages() {
        return messageRepository.findAll().stream()
                .map(m -> {
                    MessageDTO messageDTO = convertMessageToDTO(m);
                    messageDTO.setLongestPalindrome(calculateLongestPalindrome(messageDTO.getContent()));
                    return messageDTO;
                })
                .collect(Collectors.toList());
    }

    private int calculateLongestPalindrome(String content) {
        return 0;
    }

}
