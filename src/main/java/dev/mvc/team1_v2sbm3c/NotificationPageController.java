package dev.mvc.team1_v2sbm3c;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationPageController {

    @GetMapping("/notification")
    public String notificationPage() {
        return "notification";
    }
}