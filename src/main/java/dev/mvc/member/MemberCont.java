package dev.mvc.member;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mvc.cate.CateVOMenu;
import dev.mvc.tool.Security;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/member")
@Controller
public class MemberCont {
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")  // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  Security security;
  
  
  public MemberCont() {
    System.out.println("-> MemberCont created.");  
  }
  
  
  
  /**
   * 회원 가입 폼
   * @param model
   * @param memberVO
   * @return
   */
  @GetMapping(value="/create") // http://localhost:9091/member/create
  public String create_form(Model model, MemberVO memberVO) {
    return "member/create";    // /template/member/create.html
  }
  /**
   * 회원 가입 Proc
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value="/create")
  public String create_proc(Model model, MemberVO memberVO) {
    
    int checkID_cnt = this.memberProc.checkID(memberVO.getId());
    
    if (checkID_cnt == 0) {
      memberVO.setGrade(1); 
      // 일반회원  1
      // 정지회원  2
      // 탈퇴회원  3
      int cnt = this.memberProc.create(memberVO);
      
      if (cnt == 1) {
        model.addAttribute("code", "create_success");
        model.addAttribute("mname", memberVO.getMname());
        model.addAttribute("id", memberVO.getId());
      } else {
        model.addAttribute("code", "create_fail");
      }
      
      model.addAttribute("cnt", cnt);
    } else { // id 중복
      model.addAttribute("code", "duplicte_fail");
      model.addAttribute("cnt", 0);
    }
    
    return "/index"; // /templates/member/msg.html 일단 메인화면으
  }
  
  
  
  
  /**
   * 관리자 전용 회원 조회
   * @param model
   * @param memberVO
   * @return
   */
  @GetMapping(value="/list")
  public String list(HttpSession session, Model model) {
    

      System.out.println("HttpSession ------)_)_))>>" + session);
      ArrayList<MemberVO> list = this.memberProc.list();
      
      model.addAttribute("list", list);
      
      return "member/list";  // templates/member/list.html      
// 조회 
//    @GetMapping(value="/list")
//    public String list(HttpSession session, Model model) {
//      if (this.memberProc.isMemberAdmin(session)) {
  //
//        System.out.println("HttpSession ------)_)_))>>" + session);
//        ArrayList<MemberVO> list = this.memberProc.list();
//        
//        model.addAttribute("list", list);
//        
//        return "member/list";  // templates/member/list.html      
//      } else {
//        //return "redirect:/member/login_form_need";  // redirect
//        return "redirect:/member/list";
//      }  
  //
//    }
  }

  
  
  
  
  
  

  
}
