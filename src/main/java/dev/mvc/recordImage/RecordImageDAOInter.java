package dev.mvc.recordImage;

import java.util.ArrayList;
import java.util.HashMap;

public interface RecordImageDAOInter {
  
  /**
   * 운동이미지 등록
   * @param recordImageVO
   * @return
   */
  public int rec_images_insert(RecordImageVO recordImageVO);
  
  /**
   * 회원이 보는 이미지 리스트 조회
   * @param memberno
   * @return
   */
  public ArrayList<RecordImageVO> rec_images_list(int memberno);
  
  /**
   * 기록 별 이미지 조회
   * @return
   */
  public ArrayList<RecordImageVO> rec_images_read(HashMap<String, Object> map);

  /**
   * 기록 별 이미지 하나조회
   * @return
   */
  public ArrayList<RecordImageVO> one_images_read(int memberno);
  
  /**
   * 전체 이미지 
   * @return
   */
  public ArrayList<RecordImageVO> sns_image_read(); 
  
  /**
   * 회원별 총 이미지 수
   * @param memberno
   * @return
   */
  public int rec_images_cnt(int memberno);
  
  /**
   * 이미지 전체 총 수
   * @return
   */
  public int all_image_cnt();
  
  /**
   * 운동 기록 이미지 수정
   * @param recordImageVO
   * @return
   */
  public int rec_images_update(RecordImageVO recordImageVO);
  
  /**
   * 기록 이미지 공개
   * @param exrecordno
   * @return
   */
  public int rec_recvisible_update(int exrecordno);
  
  /**
   * 기록 이미지 비공개
   * @param exrecordno
   * @return
   */
  public int rec_norecvisible_update(int exrecordno);
  
  /**
   * 운동 기록이미지 삭제
   * @param exrecordno
   * @return
   */
  public int rec_images_delete(int exrecordno);
  
}
