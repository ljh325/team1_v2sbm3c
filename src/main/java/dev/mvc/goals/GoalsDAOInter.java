package dev.mvc.goals;

import java.util.ArrayList;
import java.util.Map;

public interface GoalsDAOInter {
  /**
   * 등록
   * insert id="create" parameterType="dev.mvc.goals.GoalsVO"
   * @param goalsVO
   * @return 등록한 레코드 갯수
   */
  public int create(GoalsVO goalsVO);
  
  /**
   * 전체 목록
   * select id="list_all" resultType="dev.mvc.goals.GoalsVO"     
   * @return 레코드 목록
   */
  public ArrayList<GoalsVO> list_all(int memberno);
  
  /**
   * 조회
   * select id="read" resultType="dev.mvc.goals.GoalsVO" parameterType="int"
   * @param goalsno
   * @return
   */
  public GoalsVO read(int goalsno);
  
  /**
   * 수정
   * update id="update" parameterType="dev.mvc.goals.GoalsVO"    
   * @param goalsVO
   * @return 수정된 레코드 갯수
   */
  public int update(GoalsVO goalsVO);
  
  /**
   * delete
   * delete id="delete" parameterType="Integer"
   * @param goalsno
   * @return
   */
  public int delete(int goalsno);

  /**
   * 관리자용 검색 목록
   * select id="list_search" resultType="dev.mvc.goals.GoalsVO" parameterType="String"
   * @param map
   * @return 조회한 레코드 목록
   */
  public ArrayList<GoalsVO> list_search(String word);    
      
  /**
   * 검색목록 페이징
   * select id="list_search_paging" resultType="dev.mvc.goals.GoalsVO" parameterType="Map"
   * @param map
   * @return 조회한 레코드 목록
   */
  public ArrayList<GoalsVO> list_search_paging(Map<String, Object> map);
  
  /**
   * 검색된 레코드 수
   * @param word
   * @return
   */
  public int list_search_count(String word);
  
}








