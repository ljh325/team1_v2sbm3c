package dev.mvc.adreply;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class AdreplyVO {
  
//  ADREPLYNO       NUMBER(10)      NOT NULL  PRIMARY KEY, (관리자리뷰댓글번호)
//  ADCONTENTS      VARCHAR2(1000)  NOT NULL, (댓글내용)
//  ADDATE          DATE            NOT NULL, (등록일)
//  ADUPDATE        DATE            NULL,
//  REVIEWNO        NUMBER(10)      NOT NULL, (리뷰번호)
//  FOREIGN KEY (REVIEWNO) REFERENCES REVIEW (REVIEWNO)
//  --ADMINSNO                          NUMBER(10)     NOT NULL, (관리자 번호)

  
  /** 관리자리뷰댓글번호 */
  private int adreplyno;
  /** 댓글내용 */
  private String adcontents;
  /** 등록일 */
  private String addate;
  /** 수정일 */
  private String adupdate;
  /** 리뷰번호 */
  private int reviewno;
  /** 관리자번호 */
  private int adminsno;

}
