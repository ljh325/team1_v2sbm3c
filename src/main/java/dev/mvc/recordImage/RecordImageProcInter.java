package dev.mvc.recordImage;

import java.util.ArrayList;
import java.util.HashMap;

public interface RecordImageProcInter {

  
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
   * 회원별 총 이미지 수
   * @param memberno
   * @return
   */
  public int rec_images_cnt(int memberno);
  
  /**
   * 운동 기록 이미지 수정
   * @param recordImageVO
   * @return
   */
  public int rec_images_update(RecordImageVO recordImageVO);
  
  /**
   * 운동 기록이미지 삭제
   * @param exrecordno
   * @return
   */
  public int rec_images_delete(int exrecordno);
}
