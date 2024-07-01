package dev.mvc.reviewImage;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ReviewImageVO {

//======================= 주찬 ========================//
  
//  IMAGENO                         NUMBER(10)        NOT NULL      PRIMARY KEY,  (이미지번호)
//  PROFILE                           VARCHAR2(100)    NULL , (메인 이미지)
//  PROFILESAVED                   VARCHAR2(100)    NULL , (실제 저장된 이미지)
//  THUMBS                          VARCHAR2(100)    NULL , (메인 이미지 Preview)
//  SIZES                              NUMBER(10)         NULL , (메인 이미지 크기)
//  REVIEWNO                       NUMBER(10)         NOT NULL, (리뷰 번호)

//------------------------------------ 파일 업로드 관련 ---------------------------------------------
  /**
  이미지 파일
  <input type='file' class="form-control" name='file1MF' id='file1MF' 
             value='' placeholder="파일 선택">
  */
  private ArrayList<MultipartFile> files1MF;

  
  /** 메인 이미지 크기 단위, 파일 크기 */
  private String sizes_label = "";
  /** 이미지파일번호 */
  private int imageno;
  /** 등록 파일 */
  private String profile = "";
  /** 저장된 등록 파일 */
  private String profilesaved = "";
  /** 미리보기 이미지 */
  private String thumbs = "";
  /** 메인 이미지 크기 */
  private long sizes;
  /** 리뷰 번호 */
  private int reviewno;
  

}
