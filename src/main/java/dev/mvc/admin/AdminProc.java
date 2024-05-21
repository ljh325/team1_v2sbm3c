package dev.mvc.admin;



import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.member.MemberVO;
import dev.mvc.tool.Security;


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
  public ArrayList<AdminVO> admins_list() {
    ArrayList<AdminVO> admins_list = this.adminDAO.admins_list();
    return admins_list;
  }

}