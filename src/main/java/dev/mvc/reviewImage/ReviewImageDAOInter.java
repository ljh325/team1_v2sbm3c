package dev.mvc.reviewImage;

import java.util.ArrayList;

public interface ReviewImageDAOInter {
  
  
  /**
   * 이미지 파일 추가
   * @param reviewImageVO
   * @return
   */
  public int insert_image(ReviewImageVO reviewImageVO);
  
  /**
   * 이미지 파일 전체 조회
   * @return
   */
  public ArrayList<ReviewImageVO> list_image();
  
  /**
   * 리뷰별 이미지 파일 조회
   * @param reviewno
   * @return
   */
  public ReviewImageVO read_image(int reviewno);
  
  /**
   * 이미지 파일 수정
   * @param reviewImageVO
   * @return
   */
  public int update_image(ReviewImageVO reviewImageVO);
  
  /**
   * 이미지 파일 삭제
   * @param reviewno
   * @return
   */
  public int delete_image(int reviewno);

}
