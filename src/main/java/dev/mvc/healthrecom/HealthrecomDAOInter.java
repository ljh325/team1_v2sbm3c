package dev.mvc.healthrecom;

import java.util.ArrayList;
import java.util.Map;

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
  public HealthrecomVO read(int healthno);
  

  
  /**
   * delete
   * delete id="delete" parameterType="Integer"
   * @param healthno
   * @return
   */
  public int delete(int healthno);

  
}








