package dev.mvc.admin;



import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.member.MemberVO;
import dev.mvc.tool.Security;
import jakarta.servlet.http.HttpSession;


@Component("dev.mvc.admin.AdminProc")
public class AdminProc implements AdminProcInter {
  @Autowired
  private AdminDAOInter adminDAO;
  
  @Autowired
  Security security;
  
  @Override
  public int checkID(String id) {
    int cnt = this.adminDAO.checkID(id);
    return cnt;
  }

  @Override
  public int create(AdminVO adminVO) {
    String passwd = adminVO.getPasswd();
    
    String passwd_encoded = this.security.aesEncode(passwd);
    adminVO.setPasswd(passwd_encoded);

    int cnt = this.adminDAO.create(adminVO);
    return cnt;
  }

  @Override
  public ArrayList<AdminVO> list() {
    ArrayList<AdminVO> list = this.adminDAO.list();
    return list;
  }

  @Override
  public AdminVO read(int adminsno) {
    AdminVO adminVO = this.adminDAO.read(adminsno);
    return adminVO;
  }
  @Override
  public AdminVO read_by_id(String id) {
    AdminVO adminVO = this.adminDAO.read_by_id(id);
    return adminVO;
  }
  
  
  @Override
  public int login(HashMap<String, Object> map) {
    int cnt = this.adminDAO.login(map);
    return cnt;
  }
  


  



  @Override
  public boolean isAdmin(HttpSession session) {
    boolean sw = false; // 로그인하지 않은 것으로 초기화
    String grade = (String)session.getAttribute("grade");
    
    if (grade != null) {
      if (grade.equals("admin") || grade.equals("member")) {
        sw = true;  // 로그인 한 경우
      }      
    }
    
    return sw;
  }

  @Override
  public boolean isMemberAdmin(HttpSession session) {
    boolean sw = false; // 로그인하지 않은 것으로 초기화
    String grade = (String)session.getAttribute("grade");
    
    if (grade != null) {
      if (grade.equals("admin")) {
        sw = true;  // 로그인 한 경우
      }  
    }
    
    return sw;
  }
  
  @Override
  public int update(AdminVO adminVO) {
    int cnt = this.adminDAO.update(adminVO);
    return cnt;
  }

//  @Override
//  public int update(FoodCateVO adminVO) {
//    String passwd = adminVO.getPasswd();
//    // Security security = new Security();
//    String passwd_encoded = this.security.aesEncode(passwd);
//    adminVO.setPasswd(passwd_encoded);
//    int cnt = this.adminDAO.update(adminVO);
//    return cnt;
//  }

  @Override
  public int delete(int adminsno) {
    int cnt = this.adminDAO.delete(adminsno);
    return cnt;
  }

  @Override
  public int passwd_check(HashMap<String, Object> map) {
    int cnt = this.adminDAO.passwd_check(map);
    return cnt;
  }

  @Override
  public int passwd_update(HashMap<String, Object> map) {
    int cnt = this.adminDAO.passwd_update(map);
    return cnt;
  }

}