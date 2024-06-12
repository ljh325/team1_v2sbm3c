package dev.mvc.foodrecom;

import java.util.ArrayList;
import java.util.Map;

import dev.mvc.cate.CateVO;
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
  


  public int delete_g(int goalsno);
  
  public int delete_m(int mhno);

  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
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

  public int list_search_count(String word);
  
  
  
  public ArrayList<FoodrecomVO> list_search_paging(String word, int now_page, int record_per_page);

  
}







