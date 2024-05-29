package dev.mvc.adcontents;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE ADCONTENTS(
//    ADCONTENTSNO                      NUMBER(10)     NOT NULL   PRIMARY KEY,
//    CATENO                            NUMBER(10)     NOT NULL,
//    TITLE                             VARCHAR2(100)    NOT NULL,
//    CONTENT                           CLOB         NOT NULL,
//    RECOM                             NUMBER(7)    DEFAULT 0     NOT NULL,
//    VIEWCNT                           NUMBER(7)    DEFAULT 0     NOT NULL,
//    COMMENTCNT                        NUMBER(7)    DEFAULT 0     NOT NULL,
//    PASSWD                            VARCHAR2(15)     NOT NULL,
//    TAG                               VARCHAR2(100)      NULL ,
//    RDATE                             DATE         NOT NULL,
//        ID                                  VARCHAR2(30)     NOT NULL,
//    FILE1                             VARCHAR2(1000)     NULL ,
//    FILE1SAVED                        VARCHAR2(1000)     NULL ,
//    THUMB1                            VARCHAR2(1000)     NULL ,
//    SIZE1                             NUMBER(10)         NULL ,
//    YOUTUBE                           VARCHAR2(1000)     NULL ,
//    ADMINSNO                          NUMBER(10)         NULL ,
//        FOREIGN KEY(CATENO) REFERENCES CATE(CATENO),
//        FOREIGN KEY(ADMINSNO) REFERENCES ADMINS(ADMINSNO)
//);
@Getter @Setter
public class AdcontentsVO {
  /** 관리자 컨텐츠 번호 */
  private int adcontentsno;
  
  /** 카테고리 번호 */
  private int cateno;
  
  /** 관리자 번호 */
  private int adminsno;
  
  /** 글 제목 */
  private String title;
  
  /** 글 내용 */
  private String content;
  
  /** 추천 수 */
  private int recom;
  
  /** 조회 수 */
  private int viewcnt;
  
  /** 댓글 수 */
  private int commentcnt;
  
  /** 비밀번호 */
  private String passwd;
  
  /** 태그 */
  private String tag;
  
  /** 등록일자 */
  private String rdate;
  
  /** 작성자 id */
  private String id;
  
  /** 파일 크기 */
  private long size1;
  
  /** Youtube 스크립트 */
  private String youtube = "";
  

  
  
//------------------------------------ 파일 업로드 관련 ---------------------------------------------
  /**
  이미지 파일
  <input type='file' class="form-control" name='file1MF' id='file1MF' 
             value='' placeholder="파일 선택">
  */
  private MultipartFile file1MF;
  
  private MultipartFile file2MF;
  
  /** 메인 이미지 크기 단위, 파일 크기 */
  private String size1_label = "";
  
  /** 등록 파일 */
  private String file1 = "";
  
  /** 저장된 등록 파일 */
  private String file1saved = "";
  
  /** 미리보기 이미지*/
  private String thumb1 = "";


}
