package dev.mvc.likesyesno;

import java.util.ArrayList;
import java.util.HashMap;

public interface LikesyesnoDAOInter {
  
  /**
   * 
   * @param likesyesnoVO
   * @return
   */
  public int like_insert(LikesyesnoVO likesyesnoVO);

  /**
   * 좋아요 한 회원 조회
   * @param exrecordno
   * @return
   */
  public ArrayList<LikesyesnoVO> like_read(int exrecordno);
  
  /**
   * 좋아요 수
   * @param exrecordno
   * @return
   */
  public int like_cnt(int exrecordno);
  
  /**
   * 좋아요 삭제
   * @param map
   * @return
   */
  public int unlike(HashMap<String, Object> map);
}
