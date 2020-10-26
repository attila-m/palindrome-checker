package io.falcon.assignment.service;

import io.falcon.assignment.dto.MessageDTO;
import io.falcon.assignment.entity.Message;
import io.falcon.assignment.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssZ");

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
        message.setTimestamp(parseStringTimeStampToLocalDateTime(messageDTO.getTimestamp()));
        return message;
    }

    private ZonedDateTime parseStringTimeStampToLocalDateTime(String timestamp) {
        return ZonedDateTime.parse(timestamp, DATE_TIME_FORMATTER);
    }

    @Override
    public MessageDTO convertMessageToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent(message.getContent());
        messageDTO.setTimestamp(message.getTimestamp().format(DATE_TIME_FORMATTER));
        return messageDTO;
    }

    private List<MessageDTO> getEnrichedMessages() {
        return messageRepository.findAll().stream()
                .map(m -> {
                    MessageDTO messageDTO = convertMessageToDTO(m);
                    messageDTO.setLongestPalindrome(getLongestPalindromeLength(messageDTO.getContent()));
                    return messageDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Will find longest palindrome in content and return its length
     * after trimming non alphabetical characters.
     * @param  s : String
     * @return Length of longest palindrome in content : int
     */
    private int getLongestPalindromeLength(String s) {
        String content = getOnlyAlphabet(s).toLowerCase();

        if (content.isEmpty()) {
            return 0;
        }

        if (content.length() == 1) {
            return 1;
        }

        String longest = content.substring(0, 1);
        for (int i = 0; i < content.length(); i++) {

            String temp = getEqual(content, i, i);
            if (temp.length() > longest.length()) {
                longest = temp;
            }

            temp = getEqual(content, i, i + 1);
            if (temp.length() > longest.length()) {
                longest = temp;
            }
        }
        return longest.length();
    }

    private String getEqual(String s, int start, int end) {
        while (start >= 0 && end <= s.length() - 1 && s.charAt(start) == s.charAt(end)) {
            start--;
            end++;
        }
        return s.substring(start + 1, end);
    }

    private String getOnlyAlphabet(String s) {
        char[] chars = s.toCharArray();
        String onlyAlphabet = "";

        for (char c : chars) {
            if (Character.isLetter(c)) {
                onlyAlphabet += c;
            }
        }
        return onlyAlphabet;
    }
}
