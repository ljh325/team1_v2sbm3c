package dev.mvc.comments;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE COMMENTS(
//COMMENTSNO                        NUMBER(10)     NOT NULL   PRIMARY KEY,
//CONTENTS                  VARCHAR2(1000)     NOT NULL,
//RDATE                     DATE     NOT NULL,
//ID                                  VARCHAR2(30)     NOT NULL,
//MEMBERNO                          NUMBER(10)     NOT NULL,
//CONTENTSNO                        NUMBER(10)     NOT NULL,
//FOREIGN KEY(MEMBERNO) REFERENCES MEMBER(MEMBERNO),
//FOREIGN KEY (CONTENTSNO) REFERENCES CONTENTS(CONTENTSNO)
//);
@Getter @Setter
public class CommentsVO {
  /** 댓글 번호 */
  private int commentsno;
  
  /** 댓글 내용 */
  private String contents;
  
  /** 댓글 작성일자 */
  private String rdate;
  
  /** 댓글 작성 아이디 */
  private String id;
  
  /** 댓글 작성 회원 번호 */
  private int memberno;
  
  /** 댓글 작성 게시글 번호 */
  private int contentsno;
  
  @Override
  public String toString() {
      return "CommentsVO{" +
          "commentsno=" + this.commentsno +
          ", contents='" + this.contents + '\'' +
          ", rdate='" + this.rdate + '\'' +
          ", id='" + this.id + '\'' +
          ", memberno='" + this.memberno + '\'' +
          ", contentsno='" + this.contentsno + '\'' +
          '}';
  }

}
