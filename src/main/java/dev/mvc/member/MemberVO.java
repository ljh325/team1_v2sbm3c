package dev.mvc.member;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class MemberVO {

  
// ======================= 주찬 ========================//
  
//  MEMBERNO           NUMBER(10)       NOT NULL    PRIMARY KEY,
//  ID                 VARCHAR2(30)     NOT NULL,
//  PASSWD             VARCHAR2(200)    NOT NULL,
//  MNAME              VARCHAR2(30)     NOT NULL,
//  TEL                VARCHAR2(14)     NOT NULL,
//  ZIPCODE            VARCHAR2(5)      NULL ,
//  ADDRESS1           VARCHAR2(80)     NULL ,
//  ADDRESS2           VARCHAR2(50)     NULL ,
//  MDATE              DATE             NOT NULL,
//  GRADE              NUMBER(2)        NOT NULL,
//  PROFILE            VARCHAR2(1000)   NULL ,
//  POINT              NUMBER(10)       NOT NULL, 
  
  /** 회원 번호 */
  private int memberno;
  /** 아이디(이메일) */
  private String id = "";
  /** 패스워드 */
  private String passwd = "";
  /** 회원 성명 */
  private String mname = "";
  /** 전화 번호 */
  private String tel = "";
  /** 우편 번호 */
  private String zipcode = "";
  /** 주소 1 */
  private String address1 = "";
  /** 주소 2 */
  private String address2 = "";
  /** 가입일 */
  private String mdate = "";
  /** 등급 */
  private int grade = 1;
  /** 프로필 이미지 */
  private String profile = "";  
  /** 포인트 */
  private int point = 0;
  
  
  //------------------------------------------

  /** 등록된 패스워드 */
  private String old_passwd = "";
  /** id 저장 여부 */
  private String id_save = "";
  /** passwd 저장 여부 */
  private String passwd_save = "";
  /** 이동할 주소 저장 */
  private String url_address = "";
  
}
