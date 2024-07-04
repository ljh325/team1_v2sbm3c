package dev.jpa.admins;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.admin.AdminVO;
import dev.mvc.tool.Security;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/admins")
public class AdminsController {
 
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  @Autowired
  private AdminsService service;
  
  @Autowired
  Security security;
  
  public AdminsController() {
    System.out.println("-> AdminsController created.");
  }
  
  /**
   * 로그인 처리
   * http://localhost:9100/admins/login_proc
   * @param admins
   * @return
   */
  @PostMapping(value = "/login_proc")
  public String login_proc(@RequestBody Admins admins) {
    System.out.println("-> id: " + admins.getId());
    System.out.println("-> passwd: " + admins.getPasswd());
    
    Optional<Admins> optional = service.find_by_id_and_passwd(admins.getId(), this.security.aesEncode(admins.getPasswd()));
    
    AdminVO adminVO = this.adminProc.read(optional.get().getAdminsno());
    
    JSONObject json = new JSONObject();
    if (optional.isPresent()) {
      json.put("sw", true);
      json.put("adminsno", optional.get().getAdminsno()); // optional -> Admins -> getAdminsno()
      json.put("id", adminVO.getId());
      
    } else {
      json.put("sw", false);
    }
    
    return json.toString();
  }
  
  /**
   * 로그아웃
   * @return 회원 정보
   */
  @GetMapping(value="/logout")
  public String logout(HttpSession session) {
    session.invalidate();  // 모든 세션 변수 삭제
    
    JSONObject json = new JSONObject();
    json.put("logout", true); // 로그아웃 성공 여부
    json.put("sw", false);  // 로그인 여부를 저장하는 React  전역 변수와 대응
    json.put("employeeno", 0); 
    json.put("res", "LOGOUT_OK");
    
    return json.toString();
  }
  
}


