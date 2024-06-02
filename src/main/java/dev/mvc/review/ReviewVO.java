package dev.mvc.review;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ReviewVO {
  
//======================= 주찬 ========================//
  
//  REVIEWNO    NUMBER(10)    NOT NULL, (리뷰번호)
//  STAR        NUMBER(5)     NOT NULL, (별점)
//  CONTENTS    VARCHAR2(1000)NOT NULL, (내용)
//  RDATE       DATE          NOT NULL, (등록일)
//  UDATE       DATE              NULL, (수정일)
//  MEMBERNO    NUMBER(10)    NOT NULL, (회원번호)      (fk)
//  FOODCATENO  NUMBER(10)    NOT NULL, (음식카테고리번호) (fk)
  
  
  
  /** 리뷰번호 */
  private int reviewno;
  /** 별점 */
  private int star;
  /** 내용 */
  private String contents = "";
  /** 등록일 */
  private String rdate = "";
  /** 수정일 */
  private String udate = "";
  /** 회원번호 */
  private int memberno;
  /** 회원 아이디 */
  private String id;
  /** 음식카테고리번호 */
  //private int foodcateno;

  
  
  
  
  
  
  
  
  
  
  

}
