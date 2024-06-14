package dev.mvc.review;

import java.util.ArrayList;
import java.util.HashMap;

public interface ReviewProcInter {

  
  /**
   * 리뷰 등록
   * @param ReviewVO
   * @return
   */
  public int review_insert(ReviewVO reviewVO);
  
  /**
   * 리뷰 전체 목록
   * @return
   */
  public ArrayList<ReviewVO> review_list_all();
  
  /**
   * 리뷰 페이징 목록
   * @param memberVO
   * @return 추가한 레코드 갯수
   */
  public ArrayList<ReviewVO> list_by_review_paging(HashMap<String, Object> hashmap);

  /**
   * 전체 리뷰 수
   * @return
   */
  public int review_cnt();
  
  /**
   * 리뷰 최대 번호
   * @return
   */
  public int reviewno_max();
  
  /**
   * 회원 리뷰 조회
   * @param hashmap
   * @return
   */
  public ReviewVO review_read(HashMap<String, Object> hashmap);

  /**
   * 리뷰 수정
   * @param reviewVO
   * @return
   */
  public int review_update(ReviewVO reviewVO);
  
  /**
   * 리뷰 삭제
   * @param reviewno
   * @return
   */
  public int review_delete(int reviewno);


  
  /**
   * 별점에 따른 리뷰 조회(별점 높은 순)
   * ReviewVO
   * @return
   */
  public ArrayList<ReviewVO> list_star_high();
 
  /**
   * 별점에 따른 리뷰 조회(별점 낮은 순)
   * ReviewVO
   * @return
   */
  public ArrayList<ReviewVO> list_star_low();
  
  /**
   * 최근 작성순
   * @return
   */
  public ArrayList<ReviewVO> recent_review();
  
  /**
   * 오래된 작성순
   * @return
   */
  public ArrayList<ReviewVO> old_review();
  
  /**
   * 리뷰 총 평균 평점
   * @return
   */
  public int avg_cnt();

  /**
   * 별점당 갯수
   * @param star
   * @return
   */
  public int star_cnt(int star);
}
