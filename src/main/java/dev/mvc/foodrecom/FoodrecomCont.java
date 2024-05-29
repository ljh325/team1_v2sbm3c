package dev.mvc.foodrecom;

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



import dev.mvc.cate.CateVO;
import dev.mvc.cate.CateVOMenu;
import dev.mvc.goals.GoalsVO;
import dev.mvc.healthrecom.HealthrecomProcInter;
import dev.mvc.member.MemberProcInter;
import dev.mvc.mh.MhVO;
import dev.mvc.foodrecom.FoodrecomVO;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/foodrecom")
@Controller
public class FoodrecomCont {
  @Autowired
  @Qualifier("dev.mvc.foodrecom.FoodrecomProc")
  private FoodrecomProcInter foodrecomProc;
  

  
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  
  
  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
  public int record_per_page = 3;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_block = 10;

  
  public FoodrecomCont() {
    System.out.println("-> Foodrecom created.");  
  }
  
  
  /**
   * 조회폼
   * 
   * @param model
   * @param 조회할 foodrecomno
   * @return
   */
  @PostMapping(value = "/read")
  public String read(HttpSession session,Model model,@RequestParam("foodrecomno") int foodrecomno ) {
    if (this.memberProc.isMember(session) ) {
    int memberno = (int)session.getAttribute("memberno");  
    FoodrecomVO foodrecomVO = this.foodrecomProc.read(foodrecomno);
//    foodrecomVO.setMemberno(memberno);  
    model.addAttribute("foodrecomVO", foodrecomVO);
    ArrayList<FoodrecomVO> list = this.foodrecomProc.list_all(memberno);
    model.addAttribute("list", list);
    
    
    return "foodrecom/read";
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
  @GetMapping(value="/create") // http://localhost:9091/foodrecom/create
  public String create(HttpSession session,Model model) {
    
   
  if (this.memberProc.isMember(session)) {
  int memberno = (int)session.getAttribute("memberno");
  FoodrecomVO foodrecomVO = new FoodrecomVO();
  model.addAttribute("foodrecomVO", foodrecomVO);
  ArrayList<FoodrecomVO> list = this.foodrecomProc.list_all(memberno);
  model.addAttribute("list", list);

  return "/foodrecom/create"; // /foodrecom/list_all.html
  }else {
    return "index";
  }

  }
    


//  
    
  
 
  

  /**
   * create 폼 데이터 처리
   * http://localhost:9091/foodrecom/list_search
   * @param model
   * @param foodrecomVO
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/create") // http://localhost:9091/foodrecom/create
  public String create_process(HttpSession session,Model model, @Valid MhVO mhVO,@Valid GoalsVO goalsVO,BindingResult bindingResult) {
    
    if (bindingResult.hasErrors())
    {
      return "mh/list";
      
    }
    if (this.memberProc.isMember(session)) {
      
    int memberno = (int) session.getAttribute("memberno");
    

    

    
    int cnt = this.foodrecomProc.create(mhVO,goalsVO);
    
    System.out.println("-> cnt: " + cnt);

    model.addAttribute("cnt", cnt);
    if (cnt == 1) {
      
      return "redirect:/foodrecom/list_all";
      
    } else {
      model.addAttribute("code", "create_fail");
      return "foodrecom/msg"; // /templates/foodrecom/msg.html
      
      
    }
   }else {
     return "index";
    }
   }
  
   
   /**
   * list_all 폼,목록
   * http://localhost:9091/foodrecom/list_all
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
    ArrayList<FoodrecomVO> list = this.foodrecomProc.list_all(memberno);
    model.addAttribute("list", list);
    return "/foodrecom/list_all"; // /foodrecom/list_all.html
    }else
    {
    return "index"; // /foodrecom/list_all.html
    }
  }
  
  @GetMapping(value="/delete")
  public String delete(HttpSession session,Model model, 
                             
                               @RequestParam("foodrecomno") int foodrecomno) {
    
    if (this.memberProc.isMember(session)) {

    
    int memberno = (int)session.getAttribute("memberno");
    ArrayList<FoodrecomVO> list = this.foodrecomProc.list_all(memberno);
    model.addAttribute("list", list);
    FoodrecomVO foodrecomVO = this.foodrecomProc.read(foodrecomno);
//  foodrecomVO.setMemberno(memberno);  
  model.addAttribute("foodrecomVO", foodrecomVO);
    
 
    }
    else {
      return "index";
    }

   
    return "foodrecom/delete";  // /templates/mh/delete.html
    
  }
   
  
  @PostMapping(value="/delete")
  public String delete_process(HttpSession session,Model model, 
                             
                               @RequestParam("foodrecomno") int foodrecomno) {
    
    if (this.memberProc.isMember(session)) {

    
    

    ArrayList<FoodrecomVO> list = this.foodrecomProc.list_all(foodrecomno);
    model.addAttribute("list", list);
    
    
    int cnt = this.foodrecomProc.delete(foodrecomno);
    if (cnt == 1) {
      
      return "redirect:/foodrecom/list_all";
      
    } else {
      model.addAttribute("code", "delete_fail");
      return "mh/msg"; // /templates/mh/msg.html
       }
    }
    else {
      return "index";
    }

  
    
  }
  
  
}


