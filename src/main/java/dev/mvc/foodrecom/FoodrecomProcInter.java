package dev.mvc.foodrecom;

import java.util.ArrayList;
import java.util.Map;

import dev.mvc.goals.GoalsVO;
import dev.mvc.mh.MhVO;

public interface FoodrecomProcInter {
  /**
   * 등록
   * insert id="create" parameterType="dev.goalsc.foodrecom.FoodrecomVO"
   * @param mhVO
   * @return 등록한 레코드 갯수
   */
  public int create(MhVO mhVO,GoalsVO goalsVO);
  
  /**
   * 전체 목록
   * select id="list_all" resultType="dev.goalsc.foodrecom.Foodrecom"     
   * @return 레코드 목록
   */
  public ArrayList<FoodrecomVO> list_all(int memberno);
  
  /**
   * 조회
   *
   * @param 
   * @return
   */
  public FoodrecomVO read(int foodecomno);
  

  /**
   * delete
   * delete id="delete" parameterType="Integer"
   * @param 
   * @return
   */
  public int delete(int foodecomno);
  



  

  
}







