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

import dev.mvc.mh.MhVO;
import dev.mvc.mh.MhVOMenu;
import dev.mvc.mh.MhVO;
import dev.mvc.mh.MhVOMenu;
import dev.mvc.cate.CateVO;
import dev.mvc.cate.CateVOMenu;
import dev.mvc.member.MemberProcInter;
import dev.mvc.mh.MhVO;
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
  @Qualifier("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
  public int record_per_page = 3;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_block = 10;

  private Object mhVO;
  
  public MhCont() {
    System.out.println("-> MhCont created.");  
  }
  
  // create 폼 출력
  @GetMapping(value="/create/{memberno}") // http://localhost:9091/mh/create
  public String create(HttpSession session,Model model,@PathVariable("memberno") int memberno) {
    
    
 

   
  if (this.memberProc.isMember(session)) {
  session.setAttribute("memberno",memberno);
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
  public String create(HttpSession session,Model model, @Valid MhVO mhVO) {

    if (this.memberProc.isMember(session)) {
      
    int memberno = (int) session.getAttribute("memberno");
    mhVO.setMemberno(memberno);
    ArrayList<MhVOMenu> menu = this.mhProc.menu();
    model.addAttribute("menu", menu);
    

    
    int cnt = this.mhProc.create(mhVO);
    System.out.println("-> cnt: " + cnt);

    model.addAttribute("cnt", cnt);

    return "index";
   }else {
     return "index";
    }
   }
  
   
//    
//    int cnt = this.mhProc.create(mhVO);
//    System.out.println("-> cnt: " + cnt);
//    
//    if (cnt == 1) {
//      model.addAttribute("code", "create_success");
//      model.addAttribute("kg", mhVO.getKg());
//      model.addAttribute("cm", mhVO.getCm());
//    } else {
//      model.addAttribute("code", "create_fail");
//    }
//    
//    model.addAttribute("cnt", cnt);
//    return "/mh/msg"; // /templates/mh/msg.html
//  }
  
  @GetMapping(value="/list_all/{memberno}")
  public String list_all(HttpSession session,Model model,@PathVariable("memberno") Integer memberno, 
      @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    if (this.memberProc.isMember(session)) {
    ArrayList<MhVO> list = this.mhProc.list_all(memberno);
    model.addAttribute("list", list);
    return "/mh/list_all"; // /mh/list_all.html
    }else
    {
    return "index"; // /mh/list_all.html
    }
  }
  
  
  
  
  
  
  
  /**
   * 수정폼
   * @param model
   * @param mhno 조회할 카테고리 번호
   * @return
   */
  @GetMapping(value="/update/{mhno}")
  public String update(HttpSession session,Model model, 
                                @PathVariable("mhno") Integer mhno) { 
    if (this.memberProc.isMember(session)) {
    

    ArrayList<MhVOMenu> menu = this.mhProc.menu();
    model.addAttribute("menu", menu);
    
    MhVO mhVO = this.mhProc.read(mhno);
    model.addAttribute("mhVO", mhVO);
    
    return "mh/update"; 
    }
    else {
    
    return "mh/list_all";  // /templates/mh/update.html
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
  public String update_process(Model model, 
                                            @Valid MhVO mhVO, BindingResult bindingResult 
                                           ) {
   
    
    if (bindingResult.hasErrors()) {
      
      
      return "mh/update";  // /templates/mh/update.html
    }
    
    int cnt = this.mhProc.update(mhVO);
//    System.out.println("-> cnt: " + cnt);

    model.addAttribute("cnt", cnt);
    if (cnt == 1) {
      return "redirect:/mh/list_all/" + mhVO.getMemberno();
      
    } else {
      model.addAttribute("code", "update_fail");
      return "mh/msg"; // /templates/mh/msg.html
    }

  }
  
  /**
   * Delete form
   * http://localhost:9091/mh/delete/1
   * @param model
   * @param mhno Mhgory number to delete.
   * @return
   */
  @GetMapping(value="/delete/{mhno}")
  public String delete(Model model, 
                               @PathVariable("mhno") Integer mhno, 
                               @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    ArrayList<MhVOMenu> menu = this.mhProc.menu();
    model.addAttribute("menu", menu);
    
    MhVO mhVO = this.mhProc.read(mhno);
    model.addAttribute("mhVO", mhVO);
    

   

   
    model.addAttribute("now_page", now_page);
    
 

    
    return "mh/delete";  // /templates/mh/delete.html
    
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



