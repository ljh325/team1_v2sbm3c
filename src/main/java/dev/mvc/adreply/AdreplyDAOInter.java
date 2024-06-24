package dev.mvc.adreply;

import java.util.ArrayList;

public interface AdreplyDAOInter {
  
  /**
   * 관리자 댓글 생성
   * @param adreplyVO
   * @return
   */
  public int create(AdreplyVO adreplyVO);
  
  /**
   * 관리자 댓글 조회
   * @param reviewno
   * @return
   */
  public AdreplyVO admin_read(int reviewno);
  
  /**
   * 총 관리자 댓글 갯수
   * @return
   */
  public int all_count();
  
  /**
   * 관리자 댓글 수정
   * @param adreplyVO
   * @return
   */
  public int update(AdreplyVO adreplyVO);
  
  /**
   * 관리자 댓글 삭제
   * @param adreplyVO
   * @return
   */
  public int delete(AdreplyVO adreplyVO);

}
