package dev.mvc.reply;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  대댓글 및 회원 조인 VO
 */
@Getter @Setter @ToString
public class ReplyMemberVO {
  
  /** 대댓글 번호 */
  private int replyno;
  
  /** 대댓글 내용 */
  private String contents;
  
  /** 대댓글 작성일자 */
  private String rdate;
  
  /** 대댓글 작성자 아이디 */
  private String id;
  
  /** 대댓글 작성자 프로필 이미지 */
  private String thumbs;
  
  /** 대댓글 작성자 등급 */
  private int grade;
  
  /** 대댓글 작성 회원 번호 */
  private int memberno;
  
  /** 대댓글 작성 게시글 번호 */
  private int contentsno;
  
  /** 대댓글 작성 댓글 번호*/
  private int commentsno;
  


}
