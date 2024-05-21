package dev.mvc.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;

@Component("dev.mvc.admin.AdminProc")
public class AdminProc implements AdminProcInter {
  @Autowired
  private AdminDAOInter adminDAO;
  
  @Override
  public int login(AdminVO adminVO) {
    int cnt = this.adminDAO.login(adminVO);
    return cnt;
  }

  @Override
  public AdminVO read_by_id(String id) {
    AdminVO adminVO = this.adminDAO.read_by_id(id);
    return adminVO;
  }
  
  @Override
  public boolean isAdmin(HttpSession session) {
    boolean admin = false;
    
    if (session != null) {
      String admin_id = (String)session.getAttribute("admin_id");
      
      if (admin_id != null) {
        admin = true;
      }
    }
    
    return admin;
  }
  @Override
  public AdminVO read(int admino) {
    AdminVO adminVO = this.adminDAO.read(admino);
    return adminVO;
  }
  
}