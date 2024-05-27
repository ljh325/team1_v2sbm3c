package dev.mvc.reply;

import lombok.Getter;
import lombok.Setter;

//CREATE TABLE REPLY(
//    REPLYNO                           NUMBER(10)     NOT NULL   PRIMARY KEY,
//    CONTENTS                        VARCHAR2(1000)   NOT NULL,
//    RDATE                           DATE         NOT NULL,
//        ID                                  VARCHAR2(30)     NOT NULL,
//    MEMBERNO                          NUMBER(10)     NOT NULL,
//    CONTENTSNO                        NUMBER(10)     NOT NULL,
//    COMMENTSNO                        NUMBER(10)     NOT NULL,
//        FOREIGN KEY(CONTENTSNO) REFERENCES contents(CONTENTSNO),
//        FOREIGN KEY(MEMBERNO)   REFERENCES member(MEMBERNO),
//        FOREIGN KEY(COMMENTSNO) REFERENCES comments(COMMENTSNO)
//);
@Getter @Setter
public class ReplyVO {
  
  /** 대댓글 번호 */
  private int replyno;
  
  /** 대댓글 내용 */
  private String contents;
  
  /** 대댓글 작성일자 */
  private String rdate;
  
  /** 대댓글 작성자 아이디 */
  private String id;
  
  /** 대댓글 작성 회원 번호 */
  private int memberno;
  
  /** 대댓글 작성 게시글 번호 */
  private int contentsno;
  
  /** 대댓글 작성 댓글 번호*/
  private int commentsno;
  
  @Override
  public String toString() {
      return "ReplyVO{" +
          "replyno=" + this.replyno +
          ", contents='" + this.contents + '\'' +
          ", rdate='" + this.rdate + '\'' +
          ", id='" + this.id + '\'' +
          ", memberno='" + this.memberno + '\'' +
          ", contentsno='" + this.contentsno + '\'' +
          ", commentsno='" + this.commentsno + '\'' +
          '}';
  }

}
