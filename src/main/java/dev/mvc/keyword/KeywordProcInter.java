package dev.mvc.keyword;

import java.util.ArrayList;

public interface KeywordProcInter {
  
  
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
  
  /**
   * 키워드 그룹별로 조회
   * @return
   */
  public ArrayList<KeywordVO> keyword_and_count();
  
  /**
   * 키워드 삭제
   * @param reviewno
   * @return
   */
  public int keyword_delete(int reviewno);

}
