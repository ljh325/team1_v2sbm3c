package dev.mvc.mh;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
  @PostMapping(value = "/read")
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
    return "index";
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
  public String create_process(HttpSession session,Model model, @Valid MhVO mhVO,BindingResult bindingResult) {
    
    if (bindingResult.hasErrors())
    {
      return "mh/create";
      
    }
    if (this.memberProc.isMember(session)) {
      
    int memberno = (int) session.getAttribute("memberno");
    mhVO.setMemberno(memberno);

    

    
    int cnt = this.mhProc.create(mhVO);
    
    System.out.println("-> cnt: " + cnt);

    model.addAttribute("cnt", cnt);
    if (cnt == 1) {
      
      return "redirect:/mh/list_all";
      
    } else {
      model.addAttribute("code", "create_fail");
      return "mh/msg"; // /templates/mh/msg.html
      
      
    }
   }else {
     return "index";
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
  public String list_all(HttpSession session,Model model,
      @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    if (this.memberProc.isMember(session)) {
    int memberno = (int)session.getAttribute("memberno");
    ArrayList<MhVO> list = this.mhProc.list_all(memberno);
    model.addAttribute("list", list);
    return "/mh/list_all"; // /mh/list_all.html
    }else
    {
    return "index"; // /mh/list_all.html
    }
  }
  
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
    mhVO.setMemberno(memberno);  
    model.addAttribute("mhVO", mhVO);

    model.addAttribute("list", list);
    
    return "mh/choose";
    }
    else {
      return "index";
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
      return "index";
    }

  }

  /**
   * Delete form
   * http://localhost:9091/mh/delete/1
   * @param model
   * @param mhno Mhgory number to delete.
   * @return
   */
  @GetMapping(value="/delete")
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
      return "index";
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
  public String delete_process(HttpSession session,Model model, 
                             
                               @RequestParam("mhno") int mhno) {
    
    if (this.memberProc.isMember(session)) {

    
    
    int memberno = (int)session.getAttribute("memberno");
    ArrayList<MhVO> list = this.mhProc.list_all(memberno);
    model.addAttribute("list", list);
    
//    this.healthrecomProc.delete(mhno);
//    this.mhProc.delete(mhno);
    int cnt = this.mhProc.delete(mhno);
    if (cnt == 1) {
      
      return "redirect:/mh/list_all";
      
    } else {
      model.addAttribute("code", "delete_fail");
      return "mh/msg"; // /templates/mh/msg.html
       }
    }
    else {
      return "list_all";
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



