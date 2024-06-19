package dev.mvc.foodrecom;

import java.util.ArrayList;
import java.util.Map;


public interface FoodrecomDAOInter {
  /**
   * 등록
   * insert id="create" parameterType="dev.mvc.health.HealthVO"
   * @param healthVO
   * @return 등록한 레코드 갯수
   */
  public int create(String frecom,int goals,int mhno);
  
  /**
   * 전체 목록
   * select id="list_all" resultType="dev.mvc.foodrecom.FoodrecomVO"     
   * @return 레코드 목록
   */
  public ArrayList<FoodrecomVO> list_all(int memberno);
  
  /**
   * 조회
   * select id="read" resultType="dev.mvc.health.HealthVO" parameterType="int"
   * @param 
   * @return
   */
  public FoodrecomVO read(int healthno);
  

  public ArrayList<FoodrecomVO> list_search_paging(Map<String, Object> map);
  
  public int list_search_count(String word);
  
  public int delete(int goalsno);
  
//  public int delete_g(int goalsno);
//  
//  public int delete_m(int memberno);

  
}








