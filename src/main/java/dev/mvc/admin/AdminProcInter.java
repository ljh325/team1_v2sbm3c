package dev.mvc.admin;

import jakarta.servlet.http.HttpSession;

public interface AdminProcInter {
  
  /**
   * 로그인
   * @param adminVO The AdminVO object containing admin credentials
   * @return An integer representing the login result (e.g., 0 for success, 1 for failure)
   */
  public int login(AdminVO adminVO);
  
  /**
   * 관리자 정보
   * @param id The ID of the admin to read information for
   * @return The AdminVO object containing admin information
   */
  public AdminVO read_by_id(String id);
  
  /**
   * 관리자 로그인 체크
   * @param session The HttpSession object for the current session
   * @return A boolean indicating whether the current session belongs to an admin
   */
  public boolean isAdmin(HttpSession session);
  
  /**
   * 회원 정보 조회
   * @param admino
   * @return
   */
  public AdminVO read(int admino);

}
