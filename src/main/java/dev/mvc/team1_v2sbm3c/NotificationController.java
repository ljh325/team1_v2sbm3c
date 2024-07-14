package dev.mvc.team1_v2sbm3c;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.mvc.alarm.AlarmProcInter;
import dev.mvc.alarm.AlarmVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class NotificationController {
  
  @Autowired
  @Qualifier("dev.mvc.alarm.AlarmProc")
  private AlarmProcInter alarmProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
  private final SimpMessagingTemplate messagingTemplate;

  public NotificationController(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  @PostMapping("/api/notify")
  public void sendNotification(@RequestBody NotificationMessage notificationMessage) {
    logger.info("Notification received: " + notificationMessage.getTitle() + " with patchno: "
        + notificationMessage.getPatchno());
    messagingTemplate.convertAndSend("/topic/notifications", notificationMessage);
    
    ArrayList<MemberVO> list = this.memberProc.list();
    
    for (MemberVO vo : list) {
      AlarmVO alarmVO = new AlarmVO();
      alarmVO.setPatchno(notificationMessage.getPatchno());
      alarmVO.setMemberno(vo.getMemberno());
      
      this.alarmProc.create(alarmVO);
      
    }
  }
}

@Getter
@Setter
class NotificationMessage {
  private String title;
  private int patchno;
}