package dev.mvc.foodcate;

import java.util.ArrayList;
import java.util.HashMap;

public interface FoodCateProcInter {
  /**
   * 식단 카테고리 등록 
   * @param 
   * @return 등록된 게시글 갯수
   */
  public int create(FoodCateVO foodCateVO);
  
  /**
   * 식단 카테고리 목록
   * @param 
   * @return 식단 카테고리 리스트
   */
  public ArrayList<FoodCateVO> list_all();
  
  public int delete(int foodcateno);
}
