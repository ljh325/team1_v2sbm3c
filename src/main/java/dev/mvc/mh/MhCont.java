package dev.mvc.mh;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.cate.CateVO;
import dev.mvc.foodrecom.FoodrecomProcInter;
import dev.mvc.goals.GoalsProcInter;
import dev.mvc.goals.GoalsVO;
import dev.mvc.healthrecom.HealthrecomProcInter;
import dev.mvc.mh.MhVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/mh")
@Controller
public class MhCont {
  @Autowired
  @Qualifier("dev.mvc.mh.MhProc")
  private MhProcInter mhProc;
  
  @Autowired
  @Qualifier("dev.mvc.goals.GoalsProc")
  private GoalsProcInter goalsProc;
  
  
  @Autowired
  @Qualifier("dev.mvc.foodrecom.FoodrecomProc")
  private FoodrecomProcInter foodrecomProc;
  
  @Autowired
  @Qualifier("dev.mvc.healthrecom.HealthrecomProc")
  private HealthrecomProcInter healthrecomProc;
  
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
  public int record_per_page = 3;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_block = 10;

  private Object mhVO;
  
  public MhCont() {
    System.out.println("-> Goals created.");  
  }
  


  
  /**
   * 조회폼
   * 
   * @param model
   * @param 조회할 mhno
   * @return
   */
  @GetMapping(value = "/read")
  public String read(HttpSession session,Model model,@RequestParam("mhno") int mhno ) {
    if (this.memberProc.isMember(session)) {
    int memberno = (int)session.getAttribute("memberno");  
    MhVO mhVO = this.mhProc.read(mhno);
    mhVO.setMemberno(memberno);  
    model.addAttribute("mhVO", mhVO);
    ArrayList<MhVO> list = this.mhProc.list_all(memberno);
    model.addAttribute("list", list);
    
    return "mh/read";
    }
    else {
      return "redirect:/member/login";
    }
 

     // /templates/cate/read.html
  }
  
  
  /**
   * 
   * 생성 폼 
   * @param session
   * @param model

   * @return
   */
  // create 폼 출력
  @GetMapping(value="/create") // http://localhost:9091/mh/create
  public String create(HttpSession session,Model model) {
    
   
  if (this.memberProc.isMember(session)) {
  int memberno = (int)session.getAttribute("memberno");
  MhVO mhVO = new MhVO();
  mhVO.setMemberno(memberno);
  model.addAttribute("mhVO", mhVO);
  ArrayList<MhVO> list = this.mhProc.list_all(memberno);
  model.addAttribute("list", list);

  return "/mh/create"; // /mh/list_all.html
  }else {
    return "redirect:/member/login";
  }

  }
    


//  
    
  
 
  

  /**
   * create 폼 데이터 처리
   * http://localhost:9091/mh/list_search
   * @param model
   * @param mhVO
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/create") // http://localhost:9091/mh/create
  @ResponseBody
  public String create_process(@RequestBody MhVO mhVO, HttpSession session,Model model) {

    
    if (this.memberProc.isMember(session)) {
    
    JSONObject obj = new JSONObject();
    
    int cnt = this.mhProc.create(mhVO);
    obj.put("cnt", cnt);
    System.out.println("-> cnt: " + cnt);

    if (cnt == 1) {
      obj.put("success", "success");
      return obj.toString();
    } else {
      obj.put("fail", "fail");
      return obj.toString();
    }
   }else {
     return "redirect:/member/login";
    }
   }
  
   
  /**
   * create healthrecom 생성 처리
   * http://localhost:9091/mh/list_search
   * @param model
   * @param mhVO
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/create_h") // http://localhost:9091/mh/create
  public String create_h(HttpSession session,Model model, @PathVariable("mhno") Integer mhno,@PathVariable("goalsno") Integer goalsno,BindingResult bindingResult) {
    
    if (bindingResult.hasErrors())
    {
      return "redirect:/mh/choose";
      
    }
    if (this.memberProc.isMember(session)) {
      
    MhVO mhVO = this.mhProc.read(mhno);
    GoalsVO goalsVO =this.goalsProc.read(goalsno); 
    
     
    int cnt = this.healthrecomProc.create(mhVO,goalsVO); /*Proc gpt에게 mhVO,goalsVO 정보를 보내고 새로운 healthrecom을 생성*/
    
    System.out.println("-> cnt: " + cnt);

    model.addAttribute("cnt", cnt);
    if (cnt == 1) {
      
      return "healthrecom/list_all";
      
    } else {
      model.addAttribute("code", "create_fail");
      return "mh/msg"; // /templates/mh/msg.html
      
      
    }
   }else {
     return "redirect:/member/login";
    }
   }
  
  
  /**
   * create foodrecom 생성 처리
   * http://localhost:9091/mh/list_search
   * @param model
   * @param mhVO
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/create_f") // http://localhost:9091/mh/create
  public String create_f(HttpSession session,Model model, @PathVariable("mhno") Integer mhno,@PathVariable("goalsno") Integer goalsno,BindingResult bindingResult) {
    
    if (bindingResult.hasErrors())
    {
      return "redirect:/mh/choose";
      
    }
    if (this.memberProc.isMember(session)) {
      
    MhVO mhVO = this.mhProc.read(mhno);
    GoalsVO goalsVO =this.goalsProc.read(goalsno); 
    
     
    int cnt = this.foodrecomProc.create(mhVO,goalsVO); /*Proc gpt에게 mhVO,goalsVO 정보를 보내고 새로운 foodrecom을 생성*/
    
    System.out.println("-> cnt: " + cnt);

    model.addAttribute("cnt", cnt);
    if (cnt == 1) {
      
      return "healthrecom/list_all";
      
    } else {
      model.addAttribute("code", "create_fail");
      return "mh/msg"; // /templates/mh/msg.html
      
      
    }
   }else {
     return "redirect:/member/login";
    }
   }
  
  
   /**
   * list_all 폼,목록
   * http://localhost:9091/mh/list_all
   * @param model
   * @param m
   * @param bindingResult
   * @return
    */
  
  @GetMapping(value="/list_all")
  public String list_all(HttpSession session,Model model, MhVO mhVO) {
    
    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면 = 세션에 값이 있으면
      
    int memberno = (int)session.getAttribute("memberno"); 
    ArrayList<MhVO> list = this.mhProc.list_all(memberno); // 로그인한 계정에 대한 건강정보 리스트 전부 출력
    model.addAttribute("list", list); 
    

    
    
    mhVO = this.mhProc.read_n(memberno);  // 최근 건강정보 출력
    model.addAttribute("mhVO",mhVO );
    
    
    if (mhVO != null) {
      float kg = mhVO.getKg();
      float cm = mhVO.getCm() / 100;
      float bmi_float = kg / (cm * cm); // 소수점 제한 안한 값
      String bmi = String.format("%.2f", bmi_float); //소숫점 2째자리까지
      
      System.out.println("kg -> " + kg);
      System.out.println("cm -> " + cm);
      System.out.println("bmi -> " + bmi);
      
      
        String result = "";
        String risk = "";
        String exercise_recommendation = "";
        String diet_recommendation = "";
        
        if (bmi_float < 18.5) {
            result += " 저체중";
            risk += "영양 결핍, 빈혈 위험이 있습니다.";
            exercise_recommendation += "근육을 증가시키기 위한 저강도 운동과 단백질 섭취를 권장합니다.";
            diet_recommendation += "고단백 식품과 영양가 있는 식사를 권장합니다.";
        } else if (bmi_float < 23) {
            result += "정상";
            risk = "건강한 상태입니다.";
            exercise_recommendation += "유산소 운동과 근력 운동을 균형 있게 하십시오.";
            diet_recommendation += "균형 잡힌 식단을 유지하십시오.";
        } else if (bmi_float < 25) {
            result += "과체중";
            risk = "심혈관 질환 및 당뇨병 위험이 있습니다.";
            exercise_recommendation += "체중 감량을 위한 유산소 운동을 권장합니다.";
            diet_recommendation += "저칼로리 식단과 적절한 영양 섭취를 권장합니다.";
        } else {
            result += "비만";
            risk = "고혈압, 당뇨병, 심혈관 질환 및 관절염 위험이 있습니다.";
            exercise_recommendation += "체중 감량을 위한 저강도 유산소 운동과 식이 조절을 권장합니다.";
            diet_recommendation += "저칼로리 식단과 함께 식사량을 조절하십시오.";
        }
        //if (gender == 'male' and waist >= 102) or (gender == 'female' and waist >= 88):
        //  risk += " 또한, 복부비만으로 심혈관 질환 위험이 증가합니다."

        //if (gender == 'male' and muscle_mass <= 34) or (gender == 'female' and muscle_mass <= 24):
        //  risk += " 근감소증 위험이 있습니다."
      
        
        model.addAttribute("bmi", bmi);
        model.addAttribute("result" ,result);
        model.addAttribute("risk" ,risk);
        model.addAttribute("exercise_recommendation" ,exercise_recommendation);
        model.addAttribute("diet_recommendation" ,diet_recommendation);   
    } else {
      model.addAttribute("nulls","등록된 건강정보가 없습니다.");
    }

    return "/mh/list_all"; // /mh/list_all.html
    
    }else{
      
    return "redirect:/member/login"; // /mh/list_all.html
    
    }
  }
  
  //-------------주찬 수정------------------------------------
  @GetMapping(value="/test") // http:localhost:9093/mh/test
  public String test( ) {
  
    return "/mh/test"; // /mh/list_all.html

  }
  
  // 달력 불러오기
  @GetMapping("/calendar")
  public String getCalendarPage() {
      return "mh/calendar";
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
  //-------------주찬 수정------------------------------------
  
  /**
   * 조회폼
   * 
   * @param model
   * @param 조회할 mhno
   * @return
   */
  @GetMapping(value = "/choose")
  public String choose(HttpSession session,Model model,@RequestParam("mhno") int mhno ) {
    if (this.memberProc.isMember(session)) {
    int memberno = (int)session.getAttribute("memberno");  
    MhVO mhVO = this.mhProc.read(mhno);
    ArrayList<GoalsVO> list = this.goalsProc.list_all(memberno);
    GoalsVO goalsVO = this.goalsProc.read_n(memberno);
    model.addAttribute("goalsVO", goalsVO);
    mhVO.setMemberno(memberno);  
    model.addAttribute("mhVO", mhVO);

    model.addAttribute("list", list);
    
    return "mh/choose";
    }
    else {
      return "redirect:/member/login";
    }
 

     // /templates/cate/read.html
  }
  
 
  
  
  
  
  
  /**
   * 수정폼
   * @param model
   * @param mhno 조회할 mh 번호
   * @return
   */
  @GetMapping(value="/update")
  public String update(HttpSession session,Model model, @RequestParam("mhno") int mhno) { 

    if (this.memberProc.isMember(session)) {
    int memberno = (int)session.getAttribute("memberno");
    ArrayList<MhVO> list = this.mhProc.list_all(memberno);
    model.addAttribute("list", list);
    MhVO mhVO = this.mhProc.read(mhno);
    model.addAttribute("mhVO", mhVO);

    
    return "mh/update"; 
    }
    else {
    
    return "mh/list_all";  
    }
  }
  
  /**
   * 수정 처리
   * @param model
   * @param mhVO
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/update") // http://localhost:9091/mh/update
  public String update_process(HttpSession session,
                                       Model model, 
                                            @Valid MhVO mhVO, BindingResult bindingResult 
                                           ) {
    if (this.memberProc.isMember(session)) {
    
    if (bindingResult.hasErrors()) {
      
      
      return "mh/update";  // /templates/mh/update.html
    }
   
    int cnt = this.mhProc.update(mhVO);
//    System.out.println("-> cnt: " + cnt);

    model.addAttribute("cnt", cnt);
    if (cnt == 1) {
      
      return "redirect:/mh/list_all";
      
    } else {
      model.addAttribute("code", "update_fail");
      return "mh/msg"; // /templates/mh/msg.html
    }
    }else {
      return "redirect:/member/login";
    }

  }

  /**
   * Delete form
   * http://localhost:9091/mh/delete/1
   * @param model
   * @param mhno Mhgory number to delete.
   * @return
   */
  @GetMapping(value="/delete_form")
  public String delete(HttpSession session,Model model, 
                             
                               @RequestParam("mhno") int mhno) {
    
    if (this.memberProc.isMember(session)) {

    
    int memberno = (int)session.getAttribute("memberno");
    ArrayList<MhVO> list = this.mhProc.list_all(memberno);
    model.addAttribute("list", list);
    
    
    MhVO mhVO = this.mhProc.read(mhno);
    model.addAttribute("mhVO", mhVO);
    }
    else {
      return "redirect:/member/login";
    }

   
    return "mh/delete";  // /templates/mh/delete.html
    
  }
  
  
  /**
   * Delete form
   * http://localhost:9091/mh/delete/1 삭제 프로스
   * @param model
   * @param mhno Mhgory number to delete.
   * @return
   */
  @PostMapping(value="/delete")
  @ResponseBody
  public String delete_process(@RequestBody MhVO mhVO,HttpSession session,Model model) {
    int mhno = mhVO.getMhno();
    System.out.println("mhno--->" + mhno);
    if (this.memberProc.isMember(session)) {
      
      JSONObject obj = new JSONObject();
    
      int cnt = this.mhProc.delete(mhno); //부모 테이블에 
      obj.put("cnt", cnt);
      System.out.println("cnt" + cnt);
      if (cnt == 1) {
        obj.put("success", "success");
      } else {
        obj.put("errors", "errors");
      }
      return obj.toString();
    } else {
      return "redirect:/mh/list_all";
    }
  }
  
//  /**
//   * Delete process
//   * @param model
//   * @param mhno 삭제할 레코드 번호
//   * @param bindingResult
//   * @return
//   */
//  @PostMapping(value="/delete")
//  public String delete_process(Model model, Integer mhno
//                                          ) {
//    int cnt = this.mhProc.delete(mhno); // 삭제
//    // System.out.println("-> cnt: " + cnt);
//   MhVO mhVO = this.mhProc.read(mhno);
//   
//    model.addAttribute("cnt", cnt);
//    
//    // ----------------------------------------------------------------------------------------------------------
//    // 마지막 페이지에서 모든 레코드가 삭제되면 페이지수를 1 감소 시켜야함.
//   
//    // ----------------------------------------------------------------------------------------------------------
//    
//    if (cnt == 1) {
//      return "redirect:/mh/list_all/" +this.mhVO.getMemberno ;
//    } else {
//      model.addAttribute("code", "delete_fail");
//      return "mh/msg"; // /templates/mh/msg.html
//    }
//  }



  

//  /**
//   * 등록폼 + 검색 목록
//   * http://localhost:9091/mh/list_search?word=개발  ← GET Form
//   * http://localhost:9091/mh/list_search/개발  ← @PathVariable
//   * @param model
//   * @param mhVO
//   * @return
//   */
//  @GetMapping(value="/list_search")
//  public String list_search(Model model, MhVO mhVO, String word) {
//    // mhVO.setNamesub("-"); // 폼 초기값 설정
//    word = Tool.checkNull(word).trim();    
//    System.out.println("--> word: " + word);
//    
//    ArrayList<MhVOMenu> menu = this.mhProc.menu();
//    model.addAttribute("menu", menu);
//    
//    ArrayList<MhVO> list = this.mhProc.list_search(word);
//    model.addAttribute("list", list);
//    
//    model.addAttribute("word", word);
//    
//    return "mh/list_search"; // /mh/list_search.html
//  }

 
  
}



