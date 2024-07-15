package dev.mvc.goals;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

import dev.mvc.foodrecom.FoodrecomProcInter;
import dev.mvc.foodrecom.FoodrecomVO;
import dev.mvc.healthrecom.HealthrecomProcInter;
import dev.mvc.healthrecom.HealthrecomVO;
import dev.mvc.cate.CateVO;
import dev.mvc.cate.CateVOMenu;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import dev.mvc.mh.MhVO;
import dev.mvc.goals.GoalsVO;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/goals")
@Controller
public class GoalsCont {
  @Autowired
  @Qualifier("dev.mvc.goals.GoalsProc")
  private GoalsProcInter goalsProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.foodrecom.FoodrecomProc")
  private FoodrecomProcInter foodrecomProc;
  
  @Autowired
  @Qualifier("dev.mvc.healthrecom.HealthrecomProc")
  private HealthrecomProcInter healthrecomProc;
  
  

  
  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
  public int record_per_page = 3;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_block = 10;

 
  
  public GoalsCont() {
    System.out.println("-> Goals created.");  
  }
  
  
  /**
   * 조회폼
   * 
   * @param model
   * @param 조회할 goalsno
   * @return
   */
  @PostMapping(value = "/read")
  public String read(HttpSession session,Model model,@RequestParam("goalsno") int goalsno ) {
    if (this.memberProc.isMember(session)) {
    int memberno = (int)session.getAttribute("memberno");  
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO);
    GoalsVO goalsVO = this.goalsProc.read(goalsno);
    goalsVO.setMemberno(memberno);  
    model.addAttribute("goalsVO", goalsVO);
    ArrayList<GoalsVO> list = this.goalsProc.list_all(memberno);
    model.addAttribute("list", list);
    
    return "goals/read";
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
  @GetMapping(value="/create") // http://localhost:9091/goals/create
  public String create(HttpSession session,Model model) {
    
   
  if (this.memberProc.isMember(session)) {
  int memberno = (int)session.getAttribute("memberno");
  MemberVO memberVO = this.memberProc.read(memberno);
  model.addAttribute("memberVO", memberVO);
  GoalsVO goalsVO = new GoalsVO();
  goalsVO.setMemberno(memberno);
  model.addAttribute("goalsVO", goalsVO);
  ArrayList<GoalsVO> list = this.goalsProc.list_all(memberno);
  model.addAttribute("list", list);

  return "/goals/create"; // /goals/list_all.html
  }else {
    return "redirect:/member/login";
  }

  }
    


//  
    
  
 
  

  /**
   * create 폼 데이터 처리
   * http://localhost:9091/goals/list_search
   * @param model
   * @param goalsVO
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/create") // http://localhost:9091/goals/create
  public String create_process(HttpSession session,Model model, @Valid GoalsVO goalsVO,BindingResult bindingResult) {
    
    if (bindingResult.hasErrors())
    {
      return "goals/create";
      
    }
    if (this.memberProc.isMember(session)) {
      
    int memberno = (int) session.getAttribute("memberno");
    goalsVO.setMemberno(memberno);

    

    
    int cnt = this.goalsProc.create(goalsVO);
    
    System.out.println("-> cnt: " + cnt);

    model.addAttribute("cnt", cnt);
    if (cnt == 1) {
      
      return "redirect:/goals/list_all";
      
    } else {
      model.addAttribute("code", "create_fail");
      return "goals/msg"; // /templates/goals/msg.html
      
      
    }
   }else {
     return "redirect:/member/login";
    }
   }
  
   
   /**
   * list_all 폼,목록
   * http://localhost:9091/goals/list_all
   * @param model
   * @param m
   * @param bindingResult
   * @return
    */
  
  @GetMapping(value="/list_all")
  public String list_all(HttpSession session,Model model,
      @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    if (this.memberProc.isMember(session)) {
      
      
      
      
      
      
    int memberno = (int)session.getAttribute("memberno");
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO);
    
    ArrayList<GoalsVO> list = this.goalsProc.list_all(memberno);
    model.addAttribute("list", list);
    GoalsVO goalsVO = this.goalsProc.read_n(memberno);
    model.addAttribute("goalsVO", goalsVO);
    

    
    
    if (goalsVO!= null) {
      float kg = goalsVO.getKg();
      float cm = goalsVO.getCm() / 100;
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
    
    
//    if (goalsVO != null) {
//      float kg = goalsVO.getKg();
//      float cm = goalsVO.getCm() / 100;
//      float bmi_float = kg / (cm * cm); // 소수점 제한 안한 값
//      String bmi = String.format("%.2f", bmi_float); //소숫점 2째자리까지
//      
//      System.out.println("kg -> " + kg);
//      System.out.println("cm -> " + cm);
//      System.out.println("bmi -> " + bmi);
//      
//      
//        String result = "";
//        String risk = "";
//        String exercise_recommendation = "";
//        String diet_recommendation = "";
//        
//        if (bmi_float < 18.5) {
//            result += " 저체중";
//            risk += "영양 결핍, 빈혈 위험이 있습니다.";
//            exercise_recommendation += "근육을 증가시키기 위한 저강도 운동과 단백질 섭취를 권장합니다.";
//            diet_recommendation += "고단백 식품과 영양가 있는 식사를 권장합니다.";
//        } else if (bmi_float < 23) {
//            result += "정상";
//            risk = "건강한 상태입니다.";
//            exercise_recommendation += "유산소 운동과 근력 운동을 균형 있게 하십시오.";
//            diet_recommendation += "균형 잡힌 식단을 유지하십시오.";
//        } else if (bmi_float < 25) {
//            result += "과체중";
//            risk = "심혈관 질환 및 당뇨병 위험이 있습니다.";
//            exercise_recommendation += "체중 감량을 위한 유산소 운동을 권장합니다.";
//            diet_recommendation += "저칼로리 식단과 적절한 영양 섭취를 권장합니다.";
//        } else {
//            result += "비만";
//            risk = "고혈압, 당뇨병, 심혈관 질환 및 관절염 위험이 있습니다.";
//            exercise_recommendation += "체중 감량을 위한 저강도 유산소 운동과 식이 조절을 권장합니다.";
//            diet_recommendation += "저칼로리 식단과 함께 식사량을 조절하십시오.";
//        }
//        //if (gender == 'male' and waist >= 102) or (gender == 'female' and waist >= 88):
//        //  risk += " 또한, 복부비만으로 심혈관 질환 위험이 증가합니다."
//
//        //if (gender == 'male' and muscle_mass <= 34) or (gender == 'female' and muscle_mass <= 24):
//        //  risk += " 근감소증 위험이 있습니다."
//      
//        
//        model.addAttribute("bmi", bmi);
//        model.addAttribute("result" ,result);
//        model.addAttribute("risk" ,risk);
//        model.addAttribute("exercise_recommendation" ,exercise_recommendation);
//        model.addAttribute("diet_recommendation" ,diet_recommendation);   
//  
//    }
    return "/goals/list_all"; // /goals/list_all.html
    }else
    {
    return "redirect:/member/login"; // /goals/list_all.html
    }
  }
  
  
  
  
  
  
  
  /**
   * 수정폼
   * @param model
   * @param goalsno 조회할 goals 번호
   * @return
   */
  @GetMapping(value="/update")
  public String update(HttpSession session,Model model, @RequestParam("goalsno") int goalsno) { 

    if (this.memberProc.isMember(session)) {
    int memberno = (int)session.getAttribute("memberno");
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO);
    ArrayList<GoalsVO> list = this.goalsProc.list_all(memberno);
    model.addAttribute("list", list);
    GoalsVO goalsVO = this.goalsProc.read(goalsno);
    model.addAttribute("goalsVO", goalsVO);
   
  
    
    return "goals/update"; 
    }
    else {
    
    return "goals/list_all";  
    }
  }
  
  /**
   * 수정 처리
   * @param model
   * @param goalsVO
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/update") // http://localhost:9091/goals/update
  public String update_process(HttpSession session,
                                       Model model, 
                                            @Valid GoalsVO goalsVO, BindingResult bindingResult 
                                           ) {
    if (this.memberProc.isMember(session)) {
    
    if (bindingResult.hasErrors()) {
      
      
      return "goals/update";  // /templates/goals/update.html
    }
   
    int cnt = this.goalsProc.update(goalsVO);
//    System.out.println("-> cnt: " + cnt);

    model.addAttribute("cnt", cnt);
    if (cnt == 1) {
      
      return "redirect:/goals/list_all";
      
    } else {
      model.addAttribute("code", "update_fail");
      return "goals/msg"; // /templates/goals/msg.html
    }
    }else {
      return "redirect:/member/login";
    }

  }

  /**
   * fetch로 바로 삭제하기 때문에 사용되지 않음
   * Delete form
   * http://localhost:9091/goals/delete/1
   * @param model
   * @param goalsno Goalsgory number to delete.
   * @return
   */
  @GetMapping(value="/delete")
  public String delete(HttpSession session,Model model, 
                             
                               @RequestParam("goalsno") int goalsno) {
    
    if (this.memberProc.isMember(session)) {

    
    int memberno = (int)session.getAttribute("memberno");
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO);
    ArrayList<GoalsVO> list = this.goalsProc.list_all(memberno);
    model.addAttribute("list", list);
    
    
    GoalsVO goalsVO = this.goalsProc.read(goalsno);
    model.addAttribute("goalsVO", goalsVO);
    
    
    }
    else {
      return "list_all";
    }

   
    return "goals/delete";  // /templates/goals/delete.html
    
  }
  
  
  /**
   * Delete form
   * http://localhost:9091/goals/delete/1 삭제 프로스
   * @param model
   * @param goalsno Goalsgory number to delete.
   * @return
   */
  @PostMapping(value="/delete")
  @ResponseBody
  public String delete_process(@RequestBody Map<String, Integer> requestBody,HttpSession session,Model model ) {
  
    if (this.memberProc.isMember(session)) {
      int goalsno = requestBody.get("goalsno");
      
    
      int memberno = (int)session.getAttribute("memberno");
      ArrayList<GoalsVO> list = this.goalsProc.list_all(memberno);
      model.addAttribute("list", list);
      
     
  //    this.healthrecomProc.delete_g(goalsno);
  //    this.foodrecomProc.delete_g(goalsno); 
      int cnt = this.goalsProc.delete(goalsno);
      
   
    
      JSONObject obj = new JSONObject();
      
      if (cnt == 1) {
        obj.put("success", "success");
      } else {
        obj.put("errors", "errors");
      }
      return obj.toString();
      }
    
    else {
      return "list_all";
    }
   

  
    
  }
  

  
}



