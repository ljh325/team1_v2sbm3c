package dev.mvc.admin;

import java.util.ArrayList;
import java.util.HashMap;

import dev.mvc.member.MemberVO;
import jakarta.servlet.http.HttpSession;

public interface AdminProcInter {
  

  
  /**
   * 관리자 전체 목록
   * @return
   */
  public ArrayList<AdminVO> list();
  
  /**
   * adminsno로 관리자 정보 조회
   * @param adminsno
   * @return
   */
  public AdminVO read(int adminsno);
  
  /**
   * id로 회원 정보 조회
   * @param id
   * @return
   */
  public AdminVO read_by_id(String id);
  
  /**
   * 로그인된 회원 계정인지 검사합니다.
   * @param session
   * @return true: 사용자
   */
  public boolean isAdmin(HttpSession session);

  /**
   * 로그인된 회원 관리자 계정인지 검사합니다.
   * @param session
   * @return true: 사용자
   */
  public boolean isMemberAdmin(HttpSession session);
  
  /**
   * 로그인 처리
   */
  public int login(HashMap<String, Object> map);
  
  
  /**
   * 수정 처리
   * @param adminVO
   * @return
   */
  public int update(AdminVO adminVO);
  


}
