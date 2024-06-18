package dev.mvc.recom;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//CREATE TABLE RECOM(
//    RECOMNO                           NUMBER(10)     NOT NULL   PRIMARY KEY,
//    RECOM                             NUMBER(10)         NULL,
//    CONTENTSNO                        NUMBER(10)     NOT NULL,
//    MEMBERNO                          NUMBER(10)     NOT NULL,
//    FOREIGN KEY (contentsno) REFERENCES contents (contentsno),
//    FOREIGN KEY (memberno) REFERENCES member (memberno)
//);
@Getter @Setter @ToString
public class RecomVO {
  /** 추천 번호 */
  private int recomno;
  
  /** 추천 여부 */
  private int recom;
  
  /** 추천 게시글 번호 */
  private int contentsno;
  
  /** 추천 회원 번호 */
  private int memberno;
}
