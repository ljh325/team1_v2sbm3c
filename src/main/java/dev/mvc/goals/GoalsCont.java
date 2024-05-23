package dev.mvc.goals;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    GoalsVO goalsVO = this.goalsProc.read(goalsno);
    goalsVO.setMemberno(memberno);  
    model.addAttribute("goalsVO", goalsVO);
    ArrayList<GoalsVO> list = this.goalsProc.list_all(memberno);
    model.addAttribute("list", list);
    
    return "goals/read";
    }
    else {
      return "index";
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
  GoalsVO goalsVO = new GoalsVO();
  goalsVO.setMemberno(memberno);
  model.addAttribute("goalsVO", goalsVO);
  ArrayList<GoalsVO> list = this.goalsProc.list_all(memberno);
  model.addAttribute("list", list);

  return "/goals/create"; // /goals/list_all.html
  }else {
    return "index";
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
     return "index";
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
    ArrayList<GoalsVO> list = this.goalsProc.list_all(memberno);
    model.addAttribute("list", list);
    return "/goals/list_all"; // /goals/list_all.html
    }else
    {
    return "index"; // /goals/list_all.html
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
      return "index";
    }

  }

  /**
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
  public String delete_process(HttpSession session,Model model, 
                             
                               @RequestParam("goalsno") int goalsno) {
  
    if (this.memberProc.isMember(session)) {

    
      
    
    int memberno = (int)session.getAttribute("memberno");
    ArrayList<GoalsVO> list = this.goalsProc.list_all(memberno);
    model.addAttribute("list", list);
    
   
        
    int cnt = this.goalsProc.delete(goalsno);
    
   
    if (cnt == 1) {
      
      return "redirect:/goals/list_all";
      
    } else {
      model.addAttribute("code", "delete_fail");
      return "goals/msg"; // /templates/goals/msg.html
       }
    }
    else {
      return "list_all";
    }
   

  
    
  }
  

  
}



