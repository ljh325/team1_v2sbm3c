package dev.mvc.review;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ReviewVO {
  
//======================= 주찬 ========================//
  
//  REVIEWNO    NUMBER(10)    NOT NULL, (리뷰번호)
//  STAR        NUMBER(10)    NOT NULL, (별점)
//  CONTENTS    VARCHAR2(10)  NOT NULL, (내용)
//  RDATE       DATE          NOT NULL, (등록일)
//  UDATE       DATE          NOT NULL, (수정일)
//  CNT         NUMBER(10)    NOT NULL, (리뷰수)
//  MEMBERNO    NUMBER(10)    NOT NULL, (회원번호) (fk)
//  EATNO       NUMBER(10)    NOT NULL, (식품번호) (fk)
  
  
  
  /** 리뷰번호 */
  private int reviewno = 0;
  /** 별점 */
  private int star = 0;
  /** 내용 */
  private String contents = "";
  /** 등록일 */
  private String rdate = "";
  /** 수정일 */
  private String udate = "";
  /** 리뷰수 */
  private int cnt = 0;
//  /** 회원번호 */
//  private int memberno = 0;
//  /** 식품번호 */
//  private int eatno = 0;

  
  
  
  
  
  
  
  
  
  
  

}
