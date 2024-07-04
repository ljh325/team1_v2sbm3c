package dev.mvc.likesyesno;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class LikesyesnoVO {

/**********************************/
/* Table Name: 좋아요 */
/**********************************/
//  LIKESNO           NUMBER(10)     NOT NULL    PRIMARY KEY,
//  LIKESDATE         DATE           NOT NULL,
//  EXRECORDNO        NUMBER(10)     NOT NULL,
//  MEMBERNO          NUMBER(10)     NOT NULL,
//  FOREIGN KEY (EXRECORDNO) REFERENCES HISTORY (EXRECORDNO), (FK)
//  FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO) (FK)
  
  /* 좋아요 번호 */
  private int likesno;
  /* 좋아요 한 날짜 */
  private String likesdate;
  /* 운동 기록 번호 번호 */
  private int exrecordno;
  /* 회원 번호 */
  private int memberno;

  /* ===================================================================== */
  

  /** 아이디(이메일) */
  private String id = "";
  /** 패스워드 */
  private String passwd = "";
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
  /** 소개글 */
  private String introduce;
  
//------------------------------------ 파일 업로드 관련 ---------------------------------------------
  /**
  이미지 파일
  <input type='file' class="form-control" name='file1MF' id='file1MF' 
             value='' placeholder="파일 선택">
  */
  private MultipartFile files1MF;

  
  /** 메인 이미지 크기 단위, 파일 크기 */
  private String sizes_label = "";
  
  /** 등록 파일 */
  private String profile = "";
  
  /** 저장된 등록 파일 */
  private String profilesaved = "";
  
  /** 미리보기 이미지*/
  private String thumbs = "";
  /** 메인 이미지 크기 */
  private long sizes;
  
  
}
