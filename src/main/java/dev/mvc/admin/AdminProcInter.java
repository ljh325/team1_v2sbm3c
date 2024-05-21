package dev.mvc.admin;

import java.util.ArrayList;
import java.util.HashMap;

import dev.mvc.member.MemberVO;
import jakarta.servlet.http.HttpSession;

public interface AdminProcInter {
  
  /**
   * 관리자 아이디 중복 검사
   * @param id
   * @return 중복 아이디 갯수
   */
  public int checkID(String id);
  
  /**
   * 관리자 가입
   * @param adminVO
   * @return 추가한 레코드 갯수
   */
  public int create(AdminVO adminVO);
  
  /**
   * 관리자 전체 목록
   * @return
   */
  public ArrayList<AdminVO> admins_list();
  

  

  
 
}
