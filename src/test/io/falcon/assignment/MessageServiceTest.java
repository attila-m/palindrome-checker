package io.falcon.assignment;

import io.falcon.assignment.dto.MessageDTO;
import io.falcon.assignment.entity.Message;
import io.falcon.assignment.repository.MessageRepository;
import io.falcon.assignment.service.MessageServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssZ");

    @Mock
    private MessageRepository repository;

    @InjectMocks
    private MessageServiceImpl service;

    @Test
    public void getAllMessagesWithPalindromeSize_shouldReturnDTOWithCorrectCountWhenNoContent() {
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        message.setContent("");
        message.setTimestamp(ZonedDateTime.parse("2018-10-09 00:12:12+0100", DATE_TIME_FORMATTER));
        messages.add(message);

        when(repository.findAll()).thenReturn(messages);

        int actual = service.getAllMessagesWithPalindromeSize().get(0).getLongestPalindrome();
        int expected = 0;

        assertEquals(expected, actual);
    }

    @Test
    public void getAllMessagesWithPalindromeSize_shouldReturnDTOWithCorrectCountWhenOneCharContent() {
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        message.setContent("a");
        message.setTimestamp(ZonedDateTime.parse("2018-10-09 00:12:12+0100", DATE_TIME_FORMATTER));
        messages.add(message);

        when(repository.findAll()).thenReturn(messages);

        int actual = service.getAllMessagesWithPalindromeSize().get(0).getLongestPalindrome();
        int expected = 1;

        assertEquals(expected, actual);

    }

    @Test
    public void getAllMessagesWithPalindromeSize_shouldReturnDTOWithCorrectCountWhenTwoPalindromes() {
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        message.setContent("abbabbbba");
        message.setTimestamp(ZonedDateTime.parse("2018-10-09 00:12:12+0100", DATE_TIME_FORMATTER));
        messages.add(message);

        when(repository.findAll()).thenReturn(messages);

        int actual = service.getAllMessagesWithPalindromeSize().get(0).getLongestPalindrome();
        int expected = 6;

        assertEquals(expected, actual);
    }

    @Test
    public void getAllMessagesWithPalindromeSize_shouldReturnSameTimeStampWithDTO() {
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        message.setContent("abbabbbba");
        message.setTimestamp(ZonedDateTime.parse("2018-10-09 00:12:12+0100", DATE_TIME_FORMATTER));
        messages.add(message);

        when(repository.findAll()).thenReturn(messages);

        String actual = service.getAllMessagesWithPalindromeSize().get(0).getTimestamp();
        String expected = service.convertMessageToDTO(message).getTimestamp();

        assertEquals(expected, actual);
    }

    @Test(expected = DateTimeParseException.class)
    public void getAllMessagesWithPalindromeSize_shouldThrowExceptionWhenWrongDateFormat() {
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        message.setContent("a");
        message.setTimestamp(ZonedDateTime.parse("wrong format", DATE_TIME_FORMATTER));
        messages.add(message);

        when(repository.findAll()).thenReturn(messages);
        service.getAllMessagesWithPalindromeSize();
    }

    @Test(expected = DateTimeParseException.class)
    public void createMessage_shouldThrowExceptionWhenWrongDateFormat() {
        MessageDTO message = new MessageDTO();
        message.setContent("a");
        message.setTimestamp("wrong format");

        service.createMessage(message);
    }

}
