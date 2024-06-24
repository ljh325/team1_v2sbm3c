package dev.mvc.history;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.goals.GoalsProcInter;
import dev.mvc.goals.GoalsVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import dev.mvc.mh.MhProcInter;
import dev.mvc.mh.MhVO;
import jakarta.servlet.http.HttpSession;






@RequestMapping("/history")
@Controller
public class HistoryCont {
  
  @Autowired
  @Qualifier("dev.mvc.goals.GoalsProc")
  private GoalsProcInter goalsProc;
  
  @Autowired
  @Qualifier("dev.mvc.history.HistoryProc")  // @Service("dev.mvc.member.MemberProc")
  private HistoryProcInter historyProc;

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")  // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.mh.MhProc")
  private MhProcInter mhProc;
  
  public HistoryCont() {
    System.out.println("-> HistoryCont created.");  
  }
  
  
  /***************************************************************************************/
  /**
   * 운동 시작 체크 등록
   * @param model
   * @param historyVO 
   * @return
   */
//  @PostMapping(value="/history_start")
//  public String history_start(HttpSession session, Model model, Integer memberno , HistoryVO historyVO) {
//    
////    Integer memberno = (Integer)session.getAttribute("memberno"); // session에서 memberno가져오기
//    model.addAttribute("memberno", memberno);
//    System.out.println(memberno);
//    if (memberno != null) { // 세션에 memberno가 있으면(로그인이 되어있으면)
//      
//      int cnt = this.historyProc.history_start(historyVO); // 운동 시작시간 등록
//      model.addAttribute("cnt", cnt);
//      if (cnt == 1) { // 운동 시작시간 등록 성공시
//        session.setAttribute("workoutStarted", true); // 운동 시작 플래그 설정
//        return "redirect:/"; // 메인화면 template/index.html
//      } else { // 등록 실패시
//          return "member/login"; // /templates/member/login.html
//      }
//    } else {
//      return "member/login"; // /templates/member/login.html
//  }
//
//  }
  /***************************************************************************************/
  /**
   * 운동 기록 메인 화면
   * @param model
   * @return
   */
  @GetMapping(value="/history_form")
  public String history_form(Model model, HttpSession session) {
    
    if (this.memberProc.isMember(session)) {
      int memberno = (int)session.getAttribute("memberno");  
      MemberVO memberVO = this.memberProc.read(memberno);
      model.addAttribute("memberVO", memberVO);
      return "history/history_form";
      }
      else {
        return "member/login";
      } 
  }
  
  
  /***************************************************************************************/
  /**
   * 운동 기록 폼
   * @param model
   * @return
   */
  @GetMapping(value="/history_record_form")
  public String history_record_form(Model model) {
    
    return "history/history_record_form";
    
  }
  
  
  @PostMapping(value="/history_record_proc")
  @ResponseBody
  public String history_record_proc(Model model, @RequestBody HistoryVO historyVO) {
    
    JSONObject obj = new JSONObject();
    
    int cnt = this.historyProc.insert_history(historyVO);
    System.out.println("cnt->" + cnt);
    obj.put("cnt", cnt);
  
    return obj.toString(); 
  }
  /***************************************************************************************/
  
  /**
   * 프로필 활동
   * @param model
   * @return
   */
  @GetMapping(value="/profile_activity")
  public String profile_activity(Model model) {
    
    return "history/profile_activity";
  }
  
  /**
   * 운동 기록 성공 폼
   * @param model
   * @return
   */
  @GetMapping(value="/recored_ssuccess")
  public String recored_ssuccess(Model model) {
    
    return "history/recored_ssuccess";
  }
  
  /**
   * 신체 분석 그래프
   * @param model
   * @return
   */
  @GetMapping(value="/history_analyze")
  public String history_analyze(Model model, HttpSession session,MhVO mhVO) {
    
    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면 = 세션에 값이 있으면
      
    int memberno = (int)session.getAttribute("memberno"); 
    ArrayList<MhVO> list = this.mhProc.list_all(memberno); // 로그인한 계정에 대한 건강정보 리스트 전부 출력
    
    model.addAttribute("list", list); 
    
    mhVO = this.mhProc.read_n(memberno);  // 최근 건강정보 출력
    model.addAttribute("mhVO",mhVO );
    
    //가장 최근에 만들어진 목표 회원별 조회기능
    GoalsVO goalsVO = this.goalsProc.recent_read(memberno);
    model.addAttribute("goalsVO",goalsVO );
    
    return "/history/history_analyze"; // /mh/list_all.html
    }else{
      
    return "member/login"; // /mh/list_all.html
    
    }
  }

  @GetMapping(value="/history_analyze_json")
  @ResponseBody
  public String history_analyze_json(HttpSession session) {
    System.out.println("지나갑니다");
    JSONObject obj = new JSONObject();
    
    int memberno = (int)session.getAttribute("memberno");
    System.out.println("memberno->"  + memberno);
    
    // 운동 신체 정보 리스트
    ArrayList<MhVO> list = this.mhProc.list_all(memberno); // 로그인한 계정에 대한 건강정보 리스트 전부 출력
    System.out.println("list->"  + list);
    obj.put("list", list);
    
    
    JSONArray jsonArray = new JSONArray();
    for (MhVO mhVO : list) {
        JSONObject mhJson = new JSONObject();
        mhJson.put("insertdate", mhVO.getInsertdate());
        mhJson.put("kg", mhVO.getKg());
        mhJson.put("cm", mhVO.getCm());
        mhJson.put("ckg", mhVO.getCkg());
        mhJson.put("muscle", mhVO.getMuscle());
        jsonArray.put(mhJson);
    }
    obj.put("list", jsonArray);
    
    
    //가장 최근에 만들어진 목표 회원별 조회기능
    GoalsVO goalsVO = this.goalsProc.recent_read(memberno);
    
    // 확인을 위해 GoalsVO 객체를 JSON으로 변환하여 추가
    if (goalsVO != null) {
        JSONObject goalsJson = new JSONObject();
        goalsJson.put("goalsno", goalsVO.getGoalsno());
        goalsJson.put("kg", goalsVO.getKg());
        goalsJson.put("ckg", goalsVO.getCkg());
        goalsJson.put("cm", goalsVO.getCm());
        goalsJson.put("muscle", goalsVO.getMuscle());
        goalsJson.put("memberno", goalsVO.getMemberno());
        goalsJson.put("gdate", goalsVO.getGdate());
        obj.put("goalsVO", goalsJson);
    } else {
        obj.put("goalsVO", JSONObject.NULL);
    }
    return obj.toString();
  }
  
  
  
  
  
  
  /***************************************************************************************/
  /***************************************************************************************/
  
  /**
   * 테스트
   * @param model
   * @return
   */
  @GetMapping(value="/test")
  public String test(Model model) {
    
    return "history/test";
  }
  
  /**
   * 테스트
   * @param model
   * @return
   */
  @GetMapping(value="/test1")
  public String test1(Model model) {
    
    return "history/test1";
  }
  
  @GetMapping(value="/test2")
  public String test2(Model model) {
    
    return "history/test2";
  }
  
  
  /**
   * ajax 실습
   * @param model
   * @return
   */
  @GetMapping(value="/cycle_ajax") // http://localhost:9091/history/cycle_ajax
  public String cycle_ajax(Model model) {
    
    return "history/cycle_ajax";
  }
  
  @GetMapping(value = "/cycle_ajax_json")
  @ResponseBody
  public String interval_ajax() {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    JSONObject json = new JSONObject();
    json.put("cnt", 1);
    
    return json.toString();   
  }
  
  

  /***************************************************************************************/
  
  
  // 달력 불러오기
  @GetMapping("/calendar") // http://localhost:9093/history/calendar
  public String getCalendarPage() {
      return "history/calendar";
  }

  // 이벤트 값
  @GetMapping("/api/events")
  @ResponseBody
  public List<Map<String, Object>> getEvents() {
      List<Map<String, Object>> events = new ArrayList<>();
      
      Map<String, Object> event1 = new HashMap<>();
      event1.put("title", "Event 1");
      event1.put("start", "2024-06-10");
      
      Map<String, Object> event2 = new HashMap<>();
      event2.put("title", "Event 2");
      event2.put("start", "2024-06-15");

      events.add(event1);
      events.add(event2);
      
      return events;
  }
}
