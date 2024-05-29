package dev.mvc.health;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


//COMMENT ON TABLE HEALTH is '운동 종류';
//COMMENT ON COLUMN HEALTH.HEALTHNO is '운동 종류 번호';
//COMMENT ON COLUMN HEALTH.HEALTHNAME is '운동명';
//COMMENT ON COLUMN HEALTH.EXPLAN is '운동설명';
//COMMENT ON COLUMN HEALTH.HEALTHPHOTO is '운동사진';
//COMMENT ON COLUMN HEALTH.HEALTHYOUTUBE is '운동유튜브주소';
//COMMENT ON COLUMN HEALTH.RDATE is '등록일';
//COMMENT ON COLUMN HEALTH.HTCNO is '헬스카테고리번호';
//COMMENT ON COLUMN HEALTH.ADMINSNO is '관리자 번호';


@Getter @Setter @ToString
public class HealthVO {
  /** 운동 종류 번호 */
  private int healthno;
  /** 관리자 번호 */
  private int adminsno;
  /** 운동 카테고리 번호 */
  private int htcno;
  /** 제목 */
  private String title = "";
  /** 내용 */
  private String explan = "";
  /** 추천수 */
  private int recom;
  /** 조회수 */
  private int cnt = 0;
  /** 댓글수 */
  private int replycnt = 0;
  /** 패스워드 */
  private String passwd = "";
  /** 검색어 */
  private String word = "";
  /** 등록 날짜 */
  private String rdate = "";
  /** 지도 */
  private String map = "";
  /** Youtube */
  private String youtube = "";

  /** mp4 */
  private String mp4 = "";
  
  // 파일 업로드 관련
  // -----------------------------------------------------------------------------------
  /**
  이미지 파일
  <input type='file' class="form-control" name='file1MF' id='file1MF' 
             value='' placeholder="파일 선택">
  */
  private MultipartFile file1MF = null;
  /** 메인 이미지 크기 단위, 파일 크기 */
  private String size1_label = "";
  /** 메인 이미지 */
  private String file1 = "";
  /** 실제 저장된 메인 이미지 */
  private String file1saved = "";
  /** 메인 이미지 preview */
  private String thumb1 = "";
  /** 메인 이미지 크기 */
  private long size1 = 0;


  
    
  
}
