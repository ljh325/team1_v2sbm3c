package dev.mvc.alarm;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.comments.CommentsVO;
import dev.mvc.member.MemberProcInter;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/alarm")
public class AlarmCont {

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  @Qualifier("dev.mvc.alarm.AlarmProc")
  private AlarmProcInter alarmProc;

  public AlarmCont() {
    System.out.println("-> AlarmCont created.");
  }

  /**
   * 회원별 알림 목록
   * 
   * @param contentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/alarm_list")
  public String alarm_member_list(int memberno) {

    ArrayList<AlarmPatchVO> list = this.alarmProc.alarm_list(memberno);
    JSONArray jsonArray = new JSONArray();

    for (AlarmPatchVO vo : list) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("alarmno", vo.getAlarmno());
      jsonObject.put("membeno", vo.getMemberno());
      jsonObject.put("patchno", vo.getPatchno());
      jsonObject.put("rdate", vo.getRdate());
      jsonObject.put("title", vo.getTitle());
      jsonArray.put(jsonObject);

    }

    JSONObject obj = new JSONObject();
    obj.put("res", jsonArray);

    return obj.toString();
  }

  /**
   * 회원별 알림 갯수
   * 
   * @param memberno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/alarm_cnt")
  public String alarm_cnt(int memberno) {
    int cnt = this.alarmProc.alarm_cnt(memberno);

    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);

    return obj.toString();
  }

  /**
   * 알림 삭제 처리
   * 
   * @param session
   * @param commentsVO
   * @return
   */
  @PostMapping(value = "/delete")
  @ResponseBody
  public String delete(HttpSession session, @RequestBody AlarmVO alarmVO) {

    int cnt = this.alarmProc.delete(alarmVO.getAlarmno());

    JSONObject json = new JSONObject();
    json.put("res", cnt); // 1: 성공, 0: 실패

    return json.toString();
  }

}
