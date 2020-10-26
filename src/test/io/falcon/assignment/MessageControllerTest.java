package io.falcon.assignment;

import io.falcon.assignment.controller.MessageController;
import io.falcon.assignment.dto.MessageDTO;
import io.falcon.assignment.service.MessageServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MessageControllerTest {

    @InjectMocks
    MessageController messageController;

    @Mock
    private MessageServiceImpl service;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    MockHttpServletRequest request;

    @Before
    public void setup() {
        request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void getAllMessagesWithPalindromeSize_shouldReturnOK() throws Exception {
        when(service.getAllMessagesWithPalindromeSize()).thenReturn(Collections.emptyList());
        ResponseEntity<List<MessageDTO>> responseEntity = messageController.getAllMessagesWithPalindromeSize();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
