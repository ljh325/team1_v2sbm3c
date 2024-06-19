package dev.mvc.adrecom;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//CREATE TABLE ADRECOM(
//    ADRECOMNO                         NUMBER(10)     NOT NULL   PRIMARY KEY,
//    ADRECOM                           NUMBER(10)     NULL ,
//    ADCONTENTSNO                      NUMBER(10)     NULL ,
//    MEMBERNO                          NUMBER(10)     NULL ,
//        FOREIGN KEY (adcontentsno) REFERENCES adcontents (adcontentsno),
//        FOREIGN KEY (memberno) REFERENCES member (memberno)
//);
@Getter @Setter @ToString
public class AdrecomVO {
  /** 관리자 게시글 추천 번호 */
  private int adrecomno;
  
  /** 관리자 게시글 추천 여부 */
  private int adrecom;
  
  /** 관리자 게시글 번호 */
  private int adcontentsno
  ;
  /** 관리자 게시글 추천 회원 번호 */
  private int memberno;

}
