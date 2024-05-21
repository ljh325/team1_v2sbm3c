package dev.mvc.member;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.contents.ContentsVO;
import dev.mvc.tool.Security;
import jakarta.servlet.http.HttpSession;

@Component("dev.mvc.member.MemberProc")
public class MemberProc implements MemberProcInter {
    
  @Autowired
  private MemberDAOInter memberDAO;
  
  @Autowired
  Security security;
  
  public MemberProc(){
    System.out.println("-> MemberProc created.");
  }

  @Override
  public int checkID(String id) {
    int cnt = this.memberDAO.checkID(id);
    return cnt;
  }

  @Override
  public int create(MemberVO memberVO) {
    String passwd = memberVO.getPasswd();
    // Security security = new Security();
    String passwd_encoded = this.security.aesEncode(passwd);
    memberVO.setPasswd(passwd_encoded);
    
    // memberVO.setPasswd(new Security().aesEncode(memberVO.getPasswd())); // 단축형
    
    int cnt = this.memberDAO.create(memberVO);
    return cnt;
  }
 
  @Override
  public ArrayList<MemberVO> list() {
    ArrayList<MemberVO> list = this.memberDAO.list();
    return list;
  }
  
  @Override
  public MemberVO read(int memberno) {
    MemberVO memberVO = this.memberDAO.read(memberno);
    return memberVO;
  }

  @Override
  public MemberVO readById(String id) {
    MemberVO memberVO = this.memberDAO.readById(id);
    return memberVO;
  }

  /**
   * 회원인지 검사
   */
  @Override
  public boolean isMember(HttpSession session){
    boolean sw = false; // 로그인하지 않은 것으로 초기화
    String grade = (String)session.getAttribute("grade");
    
    if (grade != null) {
      if (grade.equals("admin") || grade.equals("member")) {
        sw = true;  // 로그인 한 경우
      }      
    }
    
    return sw;
  }
  
  /**
   * 관리자, 회원인지 검사
   */  
  @Override
  public boolean isMemberAdmin(HttpSession session){
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
  public int update(MemberVO memberVO) {
    String passwd = memberVO.getPasswd();
    // Security security = new Security();
    String passwd_encoded = this.security.aesEncode(passwd);
    memberVO.setPasswd(passwd_encoded);
    int cnt = this.memberDAO.update(memberVO);
    return cnt;
  }
  
  @Override
  public int update_profile(MemberVO memberVO) {
      int cnt = this.memberDAO.update_profile(memberVO);
      return cnt;
  }
  
  @Override
  public int delete(int memberno) {
    int cnt = this.memberDAO.delete(memberno);
    return cnt;
  }
  
  @Override
  public int passwd_check(HashMap<String, Object> map) {
    int cnt = this.memberDAO.passwd_check(map);
    return cnt;
  }

  @Override
  public int passwd_update(HashMap<String, Object> map) {
    int cnt = this.memberDAO.passwd_update(map);
    return cnt;
  }
  
  @Override
  public int login(HashMap<String, Object> map) {
    int cnt = this.memberDAO.login(map);
    return cnt;
  }

}
