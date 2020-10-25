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

    /**
     * Gets all of the messages persisted in the database,
     * converts them into DTOs and enriches them with the
     * longest palindrome number
     * @return List<MessageDTO>
     */
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
                    messageDTO.setLongestPalindrome(findLongestPalindrome(messageDTO.getContent()));
                    return messageDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Will find longest palindrome in content and return its length
     * after trimming non alphabetical characters.
     * @param String content
     * @return int Length of longest palindrome in content
     */
    private int findLongestPalindrome(String content) {
        if (content.isEmpty()) {
            return 0;
        }

        content.replaceAll("[^A-Za-z]","");

        if (content.length() == 1) {
            return 1;
        }

        String longest = content.substring(0, 1);
        for (int i = 0; i < content.length(); i++) {

            String tmp = checkForEquality(content, i, i);
            if (tmp.length() > longest.length()) {
                longest = tmp;
            }

            tmp = checkForEquality(content, i, i + 1);
            if (tmp.length() > longest.length()) {
                longest = tmp;
            }
        }
        return longest.length();
    }

    private String checkForEquality(String s, int begin, int end) {
        while (begin >= 0 && end <= s.length() - 1 && s.charAt(begin) == s.charAt(end)) {
            begin--;
            end++;
        }
        return s.substring(begin + 1, end);
    }
}
