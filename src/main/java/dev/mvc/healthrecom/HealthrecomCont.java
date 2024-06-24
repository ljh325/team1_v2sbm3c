package dev.mvc.healthrecom;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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



import dev.mvc.cate.CateVO;
import dev.mvc.cate.CateVOMenu;
import dev.mvc.foodrecom.FoodrecomVO;
import dev.mvc.goals.GoalsVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.mh.MhVO;
import dev.mvc.healthrecom.HealthrecomVO;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/healthrecom")
@Controller
public class HealthrecomCont {
  @Autowired
  @Qualifier("dev.mvc.healthrecom.HealthrecomProc")
  private HealthrecomProcInter healthrecomProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  
  
  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
  public int record_per_page = 10;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_block = 10;

  private Object healthrecomVO;
  
  public HealthrecomCont() {
    System.out.println("-> Healthrecom created.");  
  }
  
  
  /**
   * 조회폼
   * 
   * @param model
   * @param 조회할 healthrecomno
   * @return
   */
//  @GetMapping(value = "/read")
//  public String read(HttpSession session,Model model,@RequestParam("healthrecomno") int healthrecomno ) {
//    if (this.memberProc.isMember(session) ) {
//    int memberno = (int)session.getAttribute("memberno");  
//    HealthrecomVO healthrecomVO = this.healthrecomProc.read(healthrecomno);
////    healthrecomVO.setMemberno(memberno);  
//    model.addAttribute("healthrecomVO", healthrecomVO);
//    ArrayList<HealthrecomVO> list = this.healthrecomProc.list_all(memberno);
//    model.addAttribute("list", list);
//    
//    
//    return "healthrecom/read";
//    }
//    else {
//      return "index";
//    }
// 
//
//     // /templates/cate/read.html
//  }
//  
//  
  
  @GetMapping(value = "/read")
  public String read(HttpSession session,Model model,@RequestParam("healthrecomno") int healthrecomno,
      @RequestParam(name="word", defaultValue = "") String word,
      @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    if (this.memberProc.isMember(session) ) {
    int memberno = (int)session.getAttribute("memberno");  
    
    ArrayList<HealthrecomVO> list = this.healthrecomProc.list_search_paging(word,memberno, now_page, this.record_per_page);    
    model.addAttribute("list", list);
    

    int search_count = this.healthrecomProc.list_search_count(word,memberno);
    String paging = this.healthrecomProc.pagingBox(now_page, 
        word, "list_all", search_count, this.record_per_page, this.page_per_block);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    model.addAttribute("word", word);
    
    int no = search_count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);
    
    
    HealthrecomVO healthrecomVO = this.healthrecomProc.read(healthrecomno);

    model.addAttribute("healthrecomVO", healthrecomVO);
    String hrecom = healthrecomVO.getHrecom();
 

    model.addAttribute("hrecom", hrecom);
//    ArrayList<FoodrecomVO> list = this.foodrecomProc.list_all(memberno);
//    model.addAttribute("list", list);
//    
    
    return "healthrecom/read";
    }
    else {
      return "index";
    }
  
  }
  /**
   * 
   * 생성 폼 
   * @param session
   * @param model

   * @return
   */
  // create 폼 출력
  @GetMapping(value="/create") // http://localhost:9091/healthrecom/create
  public String create(HttpSession session,Model model) {
    
   
  if (this.memberProc.isMember(session)) {
  int memberno = (int)session.getAttribute("memberno");
  HealthrecomVO healthrecomVO = new HealthrecomVO();
  model.addAttribute("healthrecomVO", healthrecomVO);
  ArrayList<HealthrecomVO> list = this.healthrecomProc.list_all(memberno);
  model.addAttribute("list", list);

  return "/healthrecom/create"; // /healthrecom/list_all.html
  }else {
    return "index";
  }

  }
    


//  
    
  
 
  

  /**
   * create 폼 데이터 처리
   * http://localhost:9091/healthrecom/list_search
   * @param model
   * @param healthrecomVO
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/create") // http://localhost:9091/healthrecom/create
  public String create_process(HttpSession session,Model model, @Valid MhVO mhVO,@Valid GoalsVO goalsVO,BindingResult bindingResult) {
    
    if (bindingResult.hasErrors())
    {
      return "mh/list";
      
    }
    if (this.memberProc.isMember(session)) {
      
    int memberno = (int) session.getAttribute("memberno");
    

    

    
    int cnt = this.healthrecomProc.create(mhVO,goalsVO);
    
    System.out.println("-> cnt: " + cnt);

    model.addAttribute("cnt", cnt);
    if (cnt == 1) {
      
      return "redirect:/healthrecom/list_all";
      
    } else {
      model.addAttribute("code", "create_fail");
      return "healthrecom/msg"; // /templates/healthrecom/msg.html
      
      
    }
   }else {
     return "index";
    }
   }
  
   
//   /**
//   * list_all 폼,목록
//   * http://localhost:9091/healthrecom/list_all
//   * @param model
//   * @param m
//   * @param bindingResult
//   * @return
//    */
//  
//  @GetMapping(value="/list_all")
//  public String list_all(HttpSession session,Model model) {
//    if (this.memberProc.isMember(session)) {
//    int memberno = (int)session.getAttribute("memberno");
//    ArrayList<HealthrecomVO> list = this.healthrecomProc.list_all(memberno);
//    model.addAttribute("list", list);
//    return "/healthrecom/list_all"; // /healthrecom/list_all.html
//    }else
//    {
//    return "index"; // /healthrecom/list_all.html
//    }
//  }
//  

  /**
  * list_all 폼,목록
  * http://localhost:9093/healthrecom/list_all
  * @param model
  * @param m
  * @param bindingResult
  * @return
   */
 
 @GetMapping(value="/list_all")
 public String list_all(HttpSession session,Model model,
   @RequestParam(name="word", defaultValue = "") String word,
   @RequestParam(name="now_page", defaultValue = "1") int now_page) {
   if (this.memberProc.isMember(session)) {
     
   int memberno = (int)session.getAttribute("memberno");
  
   ArrayList<HealthrecomVO> list = this.healthrecomProc.list_search_paging(word,memberno, now_page, this.record_per_page);    
   model.addAttribute("list", list);
  
   int search_count = this.healthrecomProc.list_search_count(word,memberno);
   String paging = this.healthrecomProc.pagingBox(now_page, 
       word, "list_all", search_count, this.record_per_page, this.page_per_block);
   model.addAttribute("paging", paging);
   model.addAttribute("now_page", now_page);
   model.addAttribute("word", word);
   
   int no = search_count - ((now_page - 1) * this.record_per_page);
   model.addAttribute("no", no);
   
   return "/healthrecom/list_all"; // /healthrecom/list_all.html
   }else
   {
   return "index"; // /healthrecom/list_all.html
   }
 }
  
//  @GetMapping(value="/delete")
//  public String delete(HttpSession session,Model model, 
//                             
//                               @RequestParam("healthrecomno") int healthrecomno) {
//    
//    if (this.memberProc.isMember(session)) {
//
//    
//    int memberno = (int)session.getAttribute("memberno");
//    ArrayList<HealthrecomVO> list = this.healthrecomProc.list_all(memberno);
//    model.addAttribute("list", list);
//    HealthrecomVO healthrecomVO = this.healthrecomProc.read(healthrecomno);
////  healthrecomVO.setMemberno(memberno);  
//  model.addAttribute("healthrecomVO", healthrecomVO);
//    
// 
//    }
//    else {
//      return "index";
//    }
//
//   
//    return "healthrecom/delete";  // /templates/mh/delete.html
//    
//  }
//  
  
  
  
  @GetMapping(value="/delete")
  public String delete(HttpSession session,Model model, 
      @RequestParam("healthrecomno") int healthrecomno,
      @RequestParam(name="word", defaultValue = "") String word,
      @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    
    if (this.memberProc.isMember(session)) {
      int memberno = (int) session.getAttribute("memberno");
      ArrayList<HealthrecomVO> list = this.healthrecomProc.list_search_paging(word,memberno,now_page, this.record_per_page);    
      model.addAttribute("list", list);
   
      

      int search_count = this.healthrecomProc.list_search_count(word,memberno);
      String paging = this.healthrecomProc.pagingBox(now_page, 
          word, "list_all", search_count, this.record_per_page, this.page_per_block);
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);
      model.addAttribute("word", word);
      
      int no = search_count - ((now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);
      
      HealthrecomVO healthrecomVO = this.healthrecomProc.read(healthrecomno);

      model.addAttribute("healthrecomVO", healthrecomVO);
      String hrecom = healthrecomVO.getHrecom();
    
 
    }
    else {
      return "index";
    }

   
    return "healthrecom/delete";  // /templates/mh/delete.html
    
  }
  
  
  
   
  
//  @PostMapping(value="/delete")
//  public String delete_process(HttpSession session,Model model, 
//                             
//                               @RequestParam("healthrecomno") int healthrecomno) {
//    
//    if (this.memberProc.isMember(session)) {
//
//    
//    
//
//    ArrayList<HealthrecomVO> list = this.healthrecomProc.list_all(healthrecomno);
//    model.addAttribute("list", list);
//    
//    
//    int cnt = this.healthrecomProc.delete(healthrecomno);
//    if (cnt == 1) {
//      
//      return "redirect:/healthrecom/list_all";
//      
//    } else {
//      model.addAttribute("code", "delete_fail");
//      return "mh/msg"; // /templates/mh/msg.html
//       }
//    }
//    else {
//      return "index";
//    }
//
//  
//    
//  }
  
  
  @PostMapping(value="/delete")
  @ResponseBody
  public String delete_process(HttpSession session,Model model,@RequestBody Map<String, Integer> requestBody,
      @RequestParam(name="word", defaultValue = "") String word,
      @RequestParam(name="now_page", defaultValue = "1") int now_page
                              ) {
    
    if (this.memberProc.isMember(session)) {
      int memberno = (int) session.getAttribute("memberno");
      int healthrecomno = requestBody.get("healthrecomno");
      ArrayList<HealthrecomVO> list = this.healthrecomProc.list_search_paging(word,memberno,now_page, this.record_per_page);    
      model.addAttribute("list", list);
   
      

      int search_count = this.healthrecomProc.list_search_count(word,memberno);
      String paging = this.healthrecomProc.pagingBox(now_page, 
          word, "list_all", search_count, this.record_per_page, this.page_per_block);
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);
      model.addAttribute("word", word);
      
      int no = search_count - ((now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);
      
      HealthrecomVO healthrecomVO = this.healthrecomProc.read(healthrecomno);

      model.addAttribute("healthrecomVO", healthrecomVO);
      String frecom = healthrecomVO.getHrecom();
  
    
    
   JSONObject obj = new JSONObject();
      
      int cnt = this.healthrecomProc.delete(healthrecomno); //부모 테이블에 
      obj.put("cnt", cnt);
      System.out.println("cnt" + cnt);
      if (cnt == 1) {
        obj.put("success", "success");
      } else {
        obj.put("errors", "errors");
      }
      return obj.toString();
      } else {
      return "index";
      }
  
  }
  
}


