package dev.mvc.admin;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mvc.member.MemberVO;
import dev.mvc.tool.Security;
import jakarta.servlet.http.HttpSession;


@RequestMapping("/admin")
@Controller
public class AdminCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") // "dev.mvc.admin.AdminProc"라고 명명된 클래스
  private AdminProcInter adminProc; // AdminProcInter를 구현한 AdminProc 클래스의 객체를 자동으로 생성하여 할당
  
  @Autowired
  // dev.mvc.tool.Tool.java
  // dev.mvc.team1_v2sbm3c.SecurityConfig.java
  Security security; 
  
  
  public AdminCont() {
    System.out.println("-> AdminCont created.");
  }
  
  
  /**
   * 관리자 가입 폼
   * @param model
   * @param VO
   * @return
   */
  @GetMapping(value="/create") // http://localhost:9093/admin/create
  public String create_form(Model model, AdminVO adminVO) {
    return "admin/create";    // /template/admin/create.html
  }
  
  /**
   * 관리자 가입 Proc
   * @param model
   * @param VO
   * @return
   */
  @PostMapping(value="/create")
  public String create_proc(Model model, AdminVO adminVO) {
    
    int checkID_cnt = this.adminProc.checkID(adminVO.getId());
    
    if (checkID_cnt == 0) { // 디비에서 id를 조회 후 없으면
      adminVO.setGrade(1);  // adminVO의 grade에 1 (일반회원) 저장
      // 일반회원  1
      // 정지회원  2
      // 탈퇴회원  3
      int cnt = this.adminProc.create(adminVO); //회원가입 실행 성공: 1, 실패: 0
      
      if (cnt == 1) { // 회원가입 성공하면
        model.addAttribute("code", "create_success");
        model.addAttribute("aname", adminVO.getAname());
        model.addAttribute("id", adminVO.getId());
      } else { // 실패하면
        model.addAttribute("code", "create_fail");
      }
      
      model.addAttribute("cnt", cnt);
    } else { // id 가 디비에 존재(id중복)
      model.addAttribute("code", "duplicte_fail");
      model.addAttribute("cnt", 0);
    }
    
    return "admin/msg"; // /templates//msg.html 일단 메인화면으
  }
  /**
   * 관리자 전용 회원목록 조회
   * @param model
   * @param adminVO
   * @return
   */
  @GetMapping(value="/admins_list") // http://localhost:9093/member/list
  public String admins_list(HttpSession session, Model model) {
    

      System.out.println("HttpSession ------)_)_))>>" + session);
      ArrayList<AdminVO> admins_list = this.adminProc.admins_list(); //관리자가 회원 목록 실행
      
      model.addAttribute("admins_list", admins_list); // 회원목록들 반환
      
      return "admin/admins_list";  // templates/member/list.html      
  
  }
 
}
  

