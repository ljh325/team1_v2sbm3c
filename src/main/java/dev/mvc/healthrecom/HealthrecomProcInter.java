package dev.mvc.healthrecom;

import java.util.ArrayList;
import java.util.Map;

import dev.mvc.goals.GoalsVO;
import dev.mvc.mh.MhVO;

public interface HealthrecomProcInter {
  /**
   * 등록
   * insert id="create" parameterType="dev.goalsc.mh.GoalsVO"
   * @param mhVO
   * @return 등록한 레코드 갯수
   */
  public int create(MhVO mhVO,GoalsVO goalsVO);
  
  /**
   * 전체 목록
   * select id="list_all" resultType="dev.goalsc.mh.GoalsVO"     
   * @return 레코드 목록
   */
  public ArrayList<HealthrecomVO> list_all(int memberno);
  
  /**
   * 조회
   *
   * @param mhno
   * @return
   */
  public HealthrecomVO read(int healthrecomno);
  

  /**
   * delete
   * delete id="delete" parameterType="Integer"
   * @param mhno
   * @return
   */
  public int delete(int healthrecomno);
  



  

  
}







