package io.falcon.assignment;

import io.falcon.assignment.entity.Message;
import io.falcon.assignment.repository.MessageRepository;
import io.falcon.assignment.service.MessageServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    @Mock
    private MessageRepository repository;

    @InjectMocks
    private MessageServiceImpl service;

    @Test
    public void getAllMessagesWithPalindromeSize_shouldReturnDTOWithCorrectCountWhenNoContent() {
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        message.setContent("");
        message.setTimestamp("2018-10-09 00:12:12+0100");
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
        message.setTimestamp("2018-10-09 00:12:12+0100");
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
        message.setTimestamp("2018-10-09 00:12:12+0100");
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
        message.setTimestamp("2018-10-09 00:12:12+0100");
        messages.add(message);

        when(repository.findAll()).thenReturn(messages);

        String actual = service.getAllMessagesWithPalindromeSize().get(0).getTimestamp();
        String expected = message.getTimestamp();

        assertEquals(expected, actual);
    }

}
