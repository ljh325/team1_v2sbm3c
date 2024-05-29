package dev.mvc.history;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;




@RequestMapping("/history")
@Controller
public class HistoryCont {
  
  
  @Autowired
  @Qualifier("dev.mvc.history.HistoryProc")  // @Service("dev.mvc.member.MemberProc")
  private HistoryProcInter historyProc;

  
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
  @GetMapping(value="/history_start")
  @ResponseBody
  public String history_start(Model model, HistoryVO historyVO) {
    
    int cnt = this.historyProc.history_start(historyVO); // 운동 시작시간 등록
    model.addAttribute("cnt", cnt); // 성공시 cnt = 1, 실패시 cnt = 0 
    System.out.println(cnt+ "cnt");
    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);
    
    return obj.toString();
  }
  /***************************************************************************************/
  /**
   * 운동 종료 시간체크
   * 
   * @param model
   * @param historyVO
   * @return
   */
  @GetMapping(value = "/history_end")
  @ResponseBody
  public String history_end(Model model, int memberno) {

    int cnt = historyProc.history_end(memberno); // 운동 종료 시간 등록
    System.out.println("cnt" + cnt);
    System.out.println("memberno" + memberno);

    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);

    return obj.toString();

  }
  /***************************************************************************************/
}
