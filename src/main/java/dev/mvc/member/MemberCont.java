package dev.mvc.member;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
  @GetMapping(value="/checkID") // http://localhost:9093/member/checkID?id=admin
  @ResponseBody
  public String checkID(String id) {
    System.out.println("-> id: " + id);
    int cnt = this.memberProc.checkID(id);
    
    // return "cnt: " + cnt;
    // return "{\"cnt\": " + cnt + "}";    // {"cnt": 1} JSON
    
    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);
    
    return obj.toString();
  }
  /***************************************************************************************/
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
    
    return "member/msg"; // /templates/member/msg.html 일단 메인화면으
  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
   * 관리자 전용 회원목록 조회
   * @param model
   * @param memberVO
   * @return
   */
  @GetMapping(value="/list") // http://localhost:9093/member/list
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
  @GetMapping(value="/login") // http://localhost:9093/member/login
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
  /**
   * 로그아웃
   * @param model
   * @param memberno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/logout")
  public String logout(HttpSession session, Model model) {
    session.invalidate();  // 모든 세션 변수 삭제
    return "redirect:/";
  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
   * 수정 처리
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value="/update")
  public String update_proc(Model model, MemberVO memberVO) {
    int cnt = this.memberProc.update(memberVO); // 수정
    
    if (cnt == 1) {
      model.addAttribute("code", "update_success");
      model.addAttribute("mname", memberVO.getMname());
      model.addAttribute("id", memberVO.getId());
    } else {
      model.addAttribute("code", "update_fail");
    }
    
    model.addAttribute("cnt", cnt);
    
    return "member/msg"; // /templates/member/msg.html
  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
   * 삭제
   * @param model
   * @param memberno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/delete")
  public String delete(Model model, int memberno) {
    System.out.println("-> delete memberno: " + memberno);
    
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO);
    
    return "member/delete";  // templates/member/delete.html
  }
  /**
   * 회원 Delete process
   * @param model
   * @param memberno 삭제할 레코드 번호
   * @return
   */
  @PostMapping(value="/delete")
  public String delete_process(Model model, Integer memberno) {
    int cnt = this.memberProc.delete(memberno);
    
    if (cnt == 1) {
      return "redirect:/member/list";
    } else {
      model.addAttribute("code", "delete_fail");
      return "member/msg"; // /templates/member/msg.html
    }
  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
   * 패스워드 수정 폼
   * @param model
   * @param memberno
   * @return
   */
  @GetMapping(value="/passwd_update_form")
  public String passwd_update_form(HttpSession session, Model model) {
    int memberno = (int)session.getAttribute("memberno"); // session에서 가져오기
    
    MemberVO memberVO = this.memberProc.read(memberno);
    
    model.addAttribute("memberVO", memberVO);
    
    return "member/passwd_update_form"; // /member/passwd_update_form.html   
  }
  /**
   * 
   * 현재 패스워드 확인 - 기존 패스워드 비교 대상
   * @param session
   * @param current_passwd
   * @return 1: 일치, 0: 불일치
   * 
   *   <select id="passwd_check" parameterType="HashMap" resultType="int">
    SELECT COUNT(memberno) as cnt
    FROM member
    WHERE memberno=#{memberno} AND passwd=#{passwd}
    </select>
   
  <!-- (o) 패스워드 변경 -->
  <update id="passwd_update" parameterType="HashMap">
    UPDATE member
    SET passwd=#{passwd}
    WHERE memberno=#{memberno}
  </update>
   */
  @PostMapping(value="/passwd_check")
  @ResponseBody
  public String passwd_check(HttpSession session, @RequestBody String json_src) {
    System.out.println("-> json_src: " + json_src); // json_src: {"current_passwd":"1234"}
    JSONObject src = new JSONObject(json_src); // String -> JSON
    String current_passwd =  (String)src.get("current_passwd"); // 값 가져오기
    // System.out.println("-> current_passwd: " + current_passwd);
    
    try {
      Thread.sleep(3000);
    } catch(Exception e) {
      
    }
    
    int memberno = (int)session.getAttribute("memberno"); // session에서 가져오기
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("memberno", memberno);
    // map.put("passwd", new Security().aesEncode(current_passwd));
    map.put("passwd", this.security.aesEncode(current_passwd));
    
    int cnt = this.memberProc.passwd_check(map);
    
    JSONObject json = new JSONObject();
    json.put("cnt", cnt);
    
    return json.toString();   
  }
  /**
   * 패스워드 변경
   * http://localhost:9091/member/passwd_update_proc?current_passwd=00000000000&passwd=7777
   * @param session
   * @param model
   * @param current_passwd 현재 패스워드
   * @param passwd 새로운 패스워드
   * @return
   */
  @PostMapping(value="/passwd_update_proc")
  public String passwd_update_proc(HttpSession session, 
                                                    Model model, 
                                                    String current_passwd, 
                                                    String passwd) {
    
    if (this.memberProc.isMember(session)) {
      int memberno = (int)session.getAttribute("memberno"); // session에서 가져오기
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("memberno", memberno);
      // map.put("passwd", new Security().aesEncode(current_passwd)); // 현재 패스워드 이름 주의
      map.put("passwd", this.security.aesEncode(current_passwd)); // 현재 패스워드 이름 주의
      
      // System.out.println("-> passwd_update_proc current_passwd: " + current_passwd);
      // Form 없이 해커가 해킹 했을 경우를 대비하여 다시 패스워드 검사
      int cnt = this.memberProc.passwd_check(map); 
      
      if (cnt == 0) { // 현재 패스워드 불일치
        model.addAttribute("code", "passwd_not_equal");
        model.addAttribute("cnt", 0);
        
      } else { // 현재 패스워드 일치
        HashMap<String, Object> map_new_passwd = new HashMap<String, Object>();
        map_new_passwd.put("memberno", memberno);
        // map_new_passwd.put("passwd", new Security().aesEncode(passwd)); // 새로운 패스워드
        map_new_passwd.put("passwd", this.security.aesEncode(passwd)); // 새로운 패스워드
        // System.out.println("-> passwd_update_proc passwd 변경: " + passwd);
        
        int passwd_change_cnt = this.memberProc.passwd_update(map_new_passwd);
        
        if (passwd_change_cnt == 1) {
          model.addAttribute("code", "passwd_change_success");
          model.addAttribute("cnt", 1);
        } else {
          model.addAttribute("code", "passwd_change_fail");
          model.addAttribute("cnt", 0);
        }
      }

      return "member/msg";   // /templates/member/msg.html
    } else {
      return "redirect:/member/login_form_need";
    }

  }
  /***************************************************************************************/
}
