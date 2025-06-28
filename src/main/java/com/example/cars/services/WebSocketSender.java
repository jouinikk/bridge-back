package com.example.cars.services;

import com.example.cars.dto.WebSocketNotification;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class WebSocketSender {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketSender(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendToAll(WebSocketNotification notif) {
        messagingTemplate.convertAndSend("/topic/notifications", notif);
    }
}

