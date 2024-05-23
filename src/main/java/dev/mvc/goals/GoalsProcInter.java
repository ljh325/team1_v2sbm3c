package dev.mvc.goals;

import java.util.ArrayList;
import java.util.Map;

public interface GoalsProcInter {
  /**
   * 등록
   * insert id="create" parameterType="dev.goalsc.mh.GoalsVO"
   * @param mhVO
   * @return 등록한 레코드 갯수
   */
  public int create(GoalsVO mhVO);
  
  /**
   * 전체 목록
   * select id="list_all" resultType="dev.goalsc.mh.GoalsVO"     
   * @return 레코드 목록
   */
  public ArrayList<GoalsVO> list_all(int memberno);
  
  /**
   * 조회
   * select id="read" resultType="dev.goalsc.mh.GoalsVO" parameterType="int"
   * @param mhno
   * @return
   */
  public GoalsVO read(int mhno);
  
  /**
   * 수정
   * update id="update" parameterType="dev.goalsc.mh.GoalsVO"    
   * @param mhVO
   * @return 수정된 레코드 갯수
   */
  public int update(GoalsVO mhVO);
  
  /**
   * delete
   * delete id="delete" parameterType="Integer"
   * @param mhno
   * @return
   */
  public int delete(int mhno);


  
  /**
   * 관리자용 검색 목록
   * select id="list_search" resultType="dev.goalsc.mh.GoalsVO" parameterType="String"
   * @param map
   * @return 조회한 레코드 목록
   */
  public ArrayList<GoalsVO> list_search(String word);      

  /**
   * 검색 + 페이징 목록
   * @param word 검색어
   * @param now_page 현재 페이지
   * @param record_per_page 페이지당 레코드수
   * @return
   */
  public ArrayList<GoalsVO> list_search_paging(String word, int now_page, int record_per_page);

  /**
   * 검색된 레코드 수
   * @param word
   * @return
   */
  public int list_search_count(String word);

  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param now_page  현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @param search_count 검색 레코드수   
   * @param record_per_page 페이지당 레코드 수
   * @param page_per_block 블럭당 페이지 수
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int now_page, String word, String list_file, int search_count, 
                                      int record_per_page, int page_per_block);   
  
}







