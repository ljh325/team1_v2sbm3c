package dev.mvc.adrecom;

import java.util.ArrayList;
import java.util.HashMap;


public interface AdrecomDAOInter {
  /**
   * 최초 추천 시도
   * @param recomVO
   * @return
   */
  public int create(AdrecomVO adrecomVO);
  
  /**
   * 최초 추천 여부 확인
   * @param hashmap
   * @return
   */
  public int adrecom_read(HashMap<String, Object> hashmap);
  
  /**
   * 추천 상태 조회
   * @param hashmap
   * @return
   */
  public AdrecomVO adrecom_check(HashMap<String, Object> hashmap);
  
  /**
   * 회원이 누른 추천 게시글 조회
   * @param memberno
   * @return
   */
  public ArrayList<AdrecomVO> member_read(int memberno);
  
  /**
   * 게시글 추천 누른 회원 목록
   * @param contentsno
   * @return
   */
  public ArrayList<AdrecomVO> adcontents_read(int adcontentsno);
  
  /**
   * 추천 수 증가
   * @param hashmap
   * @return
   */
  public int adrecom(HashMap<String, Object> hashmap);
  
  /**
   * 추천 수 감소
   * @param hashmap
   * @return
   */
  public int adrecom_cancel(HashMap<String, Object> hashmap);

}
