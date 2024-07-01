package dev.mvc.follows;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import dev.mvc.member.MemberVO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class FollowsVO {
  
//  /**********************************/
//  /* Table Name: 팔로우팔로잉 */
//  /**********************************/
//    FOLLOWER_NO      NUMBER(10)     NOT NULL, (팔로우하는사용자)
//    FOLLOWED_NO      NUMBER(10)     NOT NULL, (팔로우당하는사용자)
//    FOLLOW_DATE      DATE           NULL , (팔로우 한 날짜)
  
//    PRIMARY KEY (FOLLOWER_NO, FOLLOWED_NO), (복합키)
//    FOREIGN KEY (FOLLOWER_NO) REFERENCES MEMBER(MEMBERNO), (FK)
//    FOREIGN KEY (FOLLOWED_NO) REFERENCES MEMBER(MEMBERNO)  (FK)
  
  
  
  
  /* 팔로우하는사용자 */
  private int follower_no;
  /* 팔로우당하는사용자 */
  private int followed_no;
  /** 팔로우 한 날짜 */
  private String follow_date;
  
  /* ============================== */
  private MemberVO memberVO;
  /** 회원 번호 */
  private int memberno;
  /** 아이디(이메일) */
  private String id;
  /** 회원 성명 */
  private String mname;
  /** 전화 번호 */
  private String tel;
  /** 가입일 */
  private String mdate;
  /** 등급 */
  private int grade;
  /** 포인트 */
  private int point;
  /** 생일 */
  private String birth;
  /** sex */
  private String sex ;
  /** 닉네임 */
  private String nickname;

  /* =============================== */
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
  
  /* ======================================= */
  
  private ArrayList<MultipartFile> files2MF;
  
  /** 메인 이미지 크기 단위, 파일 크기 */
  private String sizes_label2 = "";
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
  
  
  
  
  

}
