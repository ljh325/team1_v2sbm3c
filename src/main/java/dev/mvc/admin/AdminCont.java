package dev.mvc.admin;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.alogin.AloginProcInter;
import dev.mvc.alogin.AloginVO;
import dev.mvc.tool.Security;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class AdminCont {
  
  //아이디 체크 
  @GetMapping(value="/checkID") // http://localhost:9093/admin/checkID?id=admin
  @ResponseBody
  public String checkID(String id) {
    System.out.println("-> id: " + id);
    int cnt = this.adminProc.checkID(id);

    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);
    
    return obj.toString();
  }

  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.alogin.AloginProc")
  private AloginProcInter aloginProc;
  
  @Autowired
  private Security security;

  public AdminCont() {
    System.out.println("-> AdminCont created.");
  }

  /**
   * 관리자 가입 폼
   * @param model
   * @param adminVO
   * @return
   */
  @GetMapping(value="/create")
  public String create_form(Model model, AdminVO adminVO) {
    model.addAttribute("adminVO", new AdminVO());
    return "admin/create"; // /template/admin/create.html
  }

  /**
   * 관리자 가입 Proc
   * @param model
   * @param adminVO
   * @return
   */
  @PostMapping(value="/create")
  public String create_proc(Model model, AdminVO adminVO) {
      // Check for duplicate ID
      int checkID_cnt = this.adminProc.checkID(adminVO.getId());
      //System.out.println("ID Duplication Check Count: " + checkID_cnt); // Logging

      if (checkID_cnt == 0) {
          // Encrypt the password before setting it in adminVO
        int cnt = this.adminProc.create(adminVO);

          // Set the grade to 1 for new admins
          adminVO.setGrade(1); // 관리자 1

          if (cnt == 1) {
            model.addAttribute("code", "create_success");
            model.addAttribute("aname", adminVO.getAname());
            model.addAttribute("id", adminVO.getId());
          } else {
            model.addAttribute("code", "create_fail");
          }
          
          model.addAttribute("cnt", cnt);
        } else { // id 중복
          model.addAttribute("code", "duplicte_fail");
          model.addAttribute("cnt", 0);
        }
        
        return "admin/msg"; // /templates/admin/msg.html
      
  }



  /**
   * 관리자 목록 조회
   * @param model
   * @param adminVO
   * @return
   */
  @GetMapping(value="/list")
  public String list(HttpSession session, Model model) {
    System.out.println("HttpSession ------)_)_))>>" + session);
    ArrayList<AdminVO> list = this.adminProc.list(); // 관리자 회원 목록 실행
    model.addAttribute("list", list); // 회원 목록 반환
    return "admin/list"; // templates/admin/list.html
  }

  /**
   * 조회
   * @param model
   * @param adminsno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/read")
  public String read(HttpSession session, Model model, int adminsno) {
    AdminVO adminVO = this.adminProc.read(adminsno);
    model.addAttribute("adminVO", adminVO);
    return "admin/read"; // templates/admin/read.html
  }

  /**
   * 수정 처리
   * @param model
   * @param adminVO
   * @return
   */
  @PostMapping(value="/update")
  public String update_proc(Model model, AdminVO adminVO) {
    int cnt = this.adminProc.update(adminVO); // 수정

    if (cnt == 1) {
      model.addAttribute("code", "update_success");
      model.addAttribute("aname", adminVO.getAname());
      model.addAttribute("id", adminVO.getId());
    } else {
      model.addAttribute("code", "update_fail");
    }
    model.addAttribute("cnt", cnt);

    return "admin/msg"; // /templates/admin/msg.html
  }

  /**
   * 삭제
   * @param model
   * @param adminsno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/delete")
  public String delete(Model model, int adminsno) {
    System.out.println("-> delete adminsno: " + adminsno);

    AdminVO adminVO = this.adminProc.read(adminsno);
    model.addAttribute("adminVO", adminVO);

    return "admin/delete"; // templates/admin/delete.html
  }

  /**
   * 회원 Delete process
   * @param model
   * @param adminsno 삭제할 레코드 번호
   * @return
   */
  @PostMapping(value="/delete")
  public String delete_process(Model model, Integer adminsno) {
    int cnt = this.adminProc.delete(adminsno);

    if (cnt == 1) {
      return "redirect:/admin/admins_list";
    } else {
      model.addAttribute("code", "delete_fail");
      return "admin/msg"; // /templates/admin/msg.html
    }
  }

  /**
   * 로그인
   * @param model
   * @param no 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/login")
  public String login_form(Model model, HttpServletRequest request) {
    return "admin/login"; // templates/admin/login.html
  }
  
  /**
   * 로그인 need
   * @param model
   * @param no 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/login_need")
  public String login_need(Model model, HttpServletRequest request) {
    return "admin/login_need"; // templates/admin/login_need.html
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
                           AloginVO aloginVO,
                           @RequestParam(value="id_save", defaultValue = "") String id_save,
                           @RequestParam(value="passwd_save", defaultValue = "") String passwd_save) {

    String ip = request.getRemoteAddr(); // IP
    System.out.println("-> 접속 IP: " + ip);

    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id", id);
    map.put("passwd", this.security.aesEncode(passwd));
    
    int cnt = this.adminProc.login(map);
    System.out.println("-> login_proc cnt: " + cnt);
    
    
    
    
    
    model.addAttribute("cnt", cnt);

    if (cnt == 1) {
     // saveLoginHistory(id, ip);
      
      // id를 이용하여 회원 정보 조회
      AdminVO adminVO = this.adminProc.read_by_id(id);
      session.setAttribute("adminsno", adminVO.getAdminsno());
      session.setAttribute("id", adminVO.getId());
      session.setAttribute("aname", adminVO.getAname());
      session.setAttribute("grade", adminVO.getGrade());
      
      int adminsno = (int)session.getAttribute("adminsno");
      aloginVO.setAdminsno(adminsno); // 세션에서 가져온 adminsno를  로그인 내역 테이블의 aloginVO의 adminsno에 저장
      aloginVO.setIp(ip);
      
      int log = this.aloginProc.alogin_insert(aloginVO); //실행
      System.out.println("로그인 내역" + log);
      
      
      if (adminVO.getGrade() >= 1 && adminVO.getGrade() <= 10) {
        session.setAttribute("grade", "admin");
      } else if (adminVO.getGrade() >= 11 && adminVO.getGrade() <= 20) {
        session.setAttribute("grade", "member");
      } else if (adminVO.getGrade() >= 21) {
        session.setAttribute("grade", "guest");
      }

      // Cookie 관련 코드
      if (id_save.equals("Y")) {
        Cookie ck_id = new Cookie("ck_id", id);
        ck_id.setPath("/");
        ck_id.setMaxAge(60 * 60 * 24 * 30); // 30 days
        response.addCookie(ck_id);
      } else {
        Cookie ck_id = new Cookie("ck_id", "");
        ck_id.setPath("/");
        ck_id.setMaxAge(0);
        response.addCookie(ck_id);
      }

      Cookie ck_id_save = new Cookie("ck_id_save", id_save);
      ck_id_save.setPath("/");
      ck_id_save.setMaxAge(60 * 60 * 24 * 30); // 30 days
      response.addCookie(ck_id_save);

      if (passwd_save.equals("Y")) {
        Cookie ck_passwd = new Cookie("ck_passwd", passwd);
        ck_passwd.setPath("/");
        ck_passwd.setMaxAge(60 * 60 * 24 * 30); // 30 days
        response.addCookie(ck_passwd);
      } else {
        Cookie ck_passwd = new Cookie("ck_passwd", "");
        ck_passwd.setPath("/");
        ck_passwd.setMaxAge(0);
        response.addCookie(ck_passwd);
      }

      Cookie ck_passwd_save = new Cookie("ck_passwd_save", passwd_save);
      ck_passwd_save.setPath("/");
      ck_passwd_save.setMaxAge(60 * 60 * 24 * 30); // 30 days
      response.addCookie(ck_passwd_save);

      return "redirect:/";
    } else {
      model.addAttribute("code", "login_fail");
      return "admin/msg";
    }
  }





  
  
  /**
   * 로그아웃
   * @param model
   * @param no 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/logout")
  public String logout(HttpSession session, Model model) {
    session.invalidate(); // 모든 세션 변수 삭제
    return "redirect:/";
  }
}
