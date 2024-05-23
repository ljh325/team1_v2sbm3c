package dev.mvc.admin;

import java.util.ArrayList;
import java.util.HashMap;

import dev.mvc.member.MemberVO;
import jakarta.servlet.http.HttpSession;

public interface AdminDAOInter {
  
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
   * @param memberVO
   * @return
   */
  public int update(AdminVO adminVO);
  

 
  /**
   * 관리자 삭제 처리
   * @param memberno
   * @return
   */
  public int delete(int adminsno);
  
  /**
   * 현재 패스워드 검사
   * @param map
   * @return 0: 일치하지 않음, 1: 일치함
   */
  public int passwd_check(HashMap<String, Object> map);
  
  /**
   * 패스워드 변경
   * @param map
   * @return 변경된 패스워드 갯수
   */
  public int passwd_update(HashMap<String, Object> map);
}
  
 
  