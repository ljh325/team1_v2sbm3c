package dev.mvc.keyword;

import java.util.ArrayList;

public interface KeywordDAOInter {
  
  /**
   * 키워드 등록
   * @param keywordVO
   * @return
   */
  public int keyword_insert(KeywordVO keywordVO);
  
  /**
   * 키워드 전체 목록
   * @return
   */
  public ArrayList<KeywordVO> keyword_all();
  
  /**
   * 키워드 조회
   * @param reviewno
   * @return
   */
  public KeywordVO keyword_read(int reviewno);

}
