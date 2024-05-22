package dev.mvc.mlogin;


import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class MloginVO {

//======================= 주찬 ========================//
//  CREATE TABLE MLOGIN(
//      MLOGINNO     NUMBER(10)     NOT NULL    PRIMARY KEY,
//      IP           VARCHAR2(16)   NOT NULL,
//      LOGINDATE    DATE           NOT NULL,
//      MEMBERNO     NUMBER(10)     NULL,
//  );
  
  /** 회원 로그인 번호 */
  private int mloginno;
  /** 회원 ip */
  private String ip;
  /** 로그인 날짜 */
  private String logindate;
  /** 회원 번호 */
  private int memberno;

  
  
  
  

}
