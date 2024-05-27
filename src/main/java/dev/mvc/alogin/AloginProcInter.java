package dev.mvc.alogin;

import java.util.ArrayList;

public interface AloginProcInter {

  
  /**
   * 로그인 내역 등록
   * @param aloginVO
   * @return 추가한 레코드 갯수
   */
  public int alogin_insert(AloginVO aloginVO);
  
  /**
   * adminsno로 회원 로그인 내역 조회
   * @param adminsno
   * @return
   */
  public ArrayList<AloginVO> alogin_read(int adminsno);
}
