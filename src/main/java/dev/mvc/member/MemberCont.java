package dev.mvc.member;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mvc.cate.CateVOMenu;
import dev.mvc.tool.Security;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/member")
@Controller
public class MemberCont {
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")  // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  // dev.mvc.tool.Tool.java
  // dev.mvc.team1_v2sbm3c.SecurityConfig.java
  Security security; 
  
  
  public MemberCont() {
    System.out.println("-> MemberCont created.");  
  }
  
  
  /***************************************************************************************/
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
    
    if (checkID_cnt == 0) { // 디비에서 id를 조회 후 없으면
      memberVO.setGrade(1);  // memberVO의 grade에 1 (일반회원) 저장
      // 일반회원  1
      // 정지회원  2
      // 탈퇴회원  3
      int cnt = this.memberProc.create(memberVO); //회원가입 실행 성공: 1, 실패: 0
      
      if (cnt == 1) { // 회원가입 성공하면
        model.addAttribute("code", "create_success");
        model.addAttribute("mname", memberVO.getMname());
        model.addAttribute("id", memberVO.getId());
      } else { // 실패하면
        model.addAttribute("code", "create_fail");
      }
      
      model.addAttribute("cnt", cnt);
    } else { // id 가 디비에 존재(id중복)
      model.addAttribute("code", "duplicte_fail");
      model.addAttribute("cnt", 0);
    }
    
    return "/index"; // /templates/member/msg.html 일단 메인화면으
  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
   * 관리자 전용 회원목록 조회
   * @param model
   * @param memberVO
   * @return
   */
  @GetMapping(value="/list") // http://localhost:9091/member/list
  public String list(HttpSession session, Model model) {
    

      System.out.println("HttpSession ------)_)_))>>" + session);
      ArrayList<MemberVO> list = this.memberProc.list(); //관리자가 회원 목록 실행
      
      model.addAttribute("list", list); // 회원목록들 반환
      
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
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
   * 로그인
   * @param model
   * @param memberno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/login") // http://localhost:9091/member/login
  public String login_form(Model model, HttpServletRequest request) {
  
    
    return "member/login";  // templates/member/login.html
  }
  /**
   * Cookie 기반 로그인 처리
   * @param session
   * @param request
   * @param response
   * @param model
   * @param id 아이디
   * @param passwd 패스워드
   * @param id_save 아이디 저장 여부
   * @param passwd_save 패스워드 저장 여부
   * @return
   */
  @PostMapping(value="/login")
  public String login_proc(HttpSession session,
                                     HttpServletRequest request,
                                     HttpServletResponse response,
                                     Model model, 
                                     String id, 
                                     String passwd,
                                     @RequestParam(value="id_save", defaultValue = "") String id_save,
                                     @RequestParam(value="passwd_save", defaultValue = "") String passwd_save
                                     ) {
    
    String ip = request.getRemoteAddr(); // ip 조회
    System.out.println("-> 접속 IP: " + ip);
    
    
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id", id); // map에 id 저장
    map.put("passwd", this.security.aesEncode(passwd)); // map에 암호화된 패스워드 저장
    int cnt = this.memberProc.login(map); // mybatis 로그인 처리
    model.addAttribute("cnt", cnt); // 결과값 반환 1: 로그인성공, 0: 실패
    
    if (cnt == 1) { // { 로그인 성공 1 }
      // id를 이용하여 회원 정보 조회
      MemberVO memverVO = this.memberProc.readById(id); // id로 회원정보를 찾음
      session.setAttribute("memberno", memverVO.getMemberno()); // 세션에 memberno 저장
      session.setAttribute("id", memverVO.getId()); //세션에 id 저장
      session.setAttribute("mname", memverVO.getMname()); //세션에 mname 저장
      session.setAttribute("grade", memverVO.getGrade()); //세션에 grade 저장
      
      
      // grade 범위 (1 ~ 3)
      // 일반회원  1  == member
      // 정지회원  2  == black
      // 탈퇴회원  3  == exit
      if (memverVO.getGrade() == 1 ) {
        session.setAttribute("grade", "member");  
      } else if (memverVO.getGrade() == 2 ) {
        session.setAttribute("grade", "black");
      } else if (memverVO.getGrade() == 3) {
        session.setAttribute("grade", "exit");
      }
      return "redirect:/";  // 로그인이 성공하면 Home으로 리다이렉트
    } else { //   { 로그인 실패 0 }
      model.addAttribute("code", "login_fail"); // 로그인 실패시 실패코드
      return "member/msg"; // templates/member/msg.html로 이동  
    }
  
  }
  /***************************************************************************************/
}
