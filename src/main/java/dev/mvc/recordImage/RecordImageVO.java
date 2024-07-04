package dev.mvc.recordImage;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class RecordImageVO {
  
//  RECIMAGENO                        NUMBER(10)     NOT NULL    PRIMARY KEY,  (이미지파일번호)
//  RECPROFILE                        VARCHAR2(100)    NULL ,  (메인이미지)
//  RECPROFILESAVED                   VARCHAR2(100)    NULL ,  (실제저장된이미지)
//  RECTHUMBS                         VARCHAR2(100)    NULL , (메인이미지Preview)
//  RECSIZES                          NUMBER(10)     NULL , (메인이미지크기)
//  RECCONTENTS                       VARCHAR2(1000)     NULL , (글내용)
//  RECVISIBLE                        NUMBER(10)     NULL , (공개비공개)
//  RECDATE                           DATE     NOT NULL, (등록일자)
//  RECUPDATE                         DATE     NULL , (수정일자)
//  EXRECORDNO                        NUMBER(10)     NULL ,  (FK) (운동기록번호)
//  MEMBERNO                            NUMBER(10)   NOT NULL,   (FK) (회원번호)
  /**
  이미지 파일
  <input type='file' class="form-control" name='file1MF' id='file1MF' 
             value='' placeholder="파일 선택">
  */
  private ArrayList<MultipartFile> files1MF;
  
  /** 메인 이미지 크기 단위, 파일 크기 */
  private String sizes_label = "";
  /* 이미지파일번호 */
  private int recimageno;
  /* 메인이미지 */
  private String recprofile;
  /* 실제저장된이미지 */
  private String recprofilesaved;
  /* 메인이미지 */
  private String recthumbs;
  /* 메인이미지크기 */
  private long recsizes;
  /* 글내용 */
  private String reccontents;
  /* 공개비공개 */
  private int recvisible;
  /* 등록일자 */
  private String recdate;
  /* 수정일자 */
  private String recupdate;
  /* 운동기록번호 */
  private int exrecordno;
  /* 회원번호 */
  private int memberno;
  
  
  
  
  
  
  //-----------------------------------------------
  /** 아이디(이메일) */
  private String id = "";
  /** 회원 성명 */
  private String mname = "";
  /** 전화 번호 */
  private String tel = "";
  /** 가입일 */
  private String mdate = "";
  /** 등급 */
  private int grade;
  /** 포인트 */
  private int point;
  /** 생일 */
  private String birth;
  /** sex */
  private String sex = "";
  /** 닉네임 */
  private String nickname="";
  
  //-----------------------------------------------
 private MultipartFile files2MF;

  
  /** 메인 이미지 크기 단위, 파일 크기 */
  private String sizes_label2= "";
  
  /** 등록 파일 */
  private String profile = "";
  
  /** 저장된 등록 파일 */
  private String profilesaved = "";
  
  /** 미리보기 이미지*/
  private String thumbs = "";
  /** 메인 이미지 크기 */
  private long sizes;
  
//-----------------------------------------------
  
  /** 좋아요 수 */
  private int likeCnt;

}
