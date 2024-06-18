package dev.mvc.healthrecom;

import java.util.ArrayList;
import java.util.Map;

import dev.mvc.foodrecom.FoodrecomVO;

public interface HealthrecomDAOInter {
  /**
   * 등록
   * insert id="create" parameterType="dev.mvc.health.HealthVO"
   * @param healthVO
   * @return 등록한 레코드 갯수
   */
  public int create(String hrecom,int goals,int mhno);
  
  /**
   * 전체 목록
   * select id="list_all" resultType="dev.mvc.health.HealthVO"     
   * @return 레코드 목록
   */
  public ArrayList<HealthrecomVO> list_all(int memberno);
  
  /**
   * 조회
   * select id="read" resultType="dev.mvc.health.HealthVO" parameterType="int"
   * @param healthno
   * @return
   */
  public HealthrecomVO read(int healthrecomno);
  

  
  /**
   * delete
   * delete id="delete" parameterType="Integer"
   * @param healthno
   * @return
   */
  public int delete(int healthrecomno);

  
//  public int delete_g(int goalsno);
//  
//  public int delete_m(int mhno);
  
  public ArrayList<HealthrecomVO> list_search_paging(Map<String, Object> map);
  
  public int list_search_count(String word);
  

}








