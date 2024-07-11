package dev.mvc.team1_v2sbm3c;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/api/notify")
    public void sendNotification(@RequestBody NotificationMessage notificationMessage) {
        logger.info("Notification received: " + notificationMessage.getMessage() + " with patchno: " + notificationMessage.getPatchno());
        messagingTemplate.convertAndSend("/topic/notifications", notificationMessage);
    }
}

@Getter @Setter
class NotificationMessage {
    private String message;
    private int patchno;
}