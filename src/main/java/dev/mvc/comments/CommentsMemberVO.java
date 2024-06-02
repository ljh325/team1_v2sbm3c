package dev.mvc.comments;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CommentsMemberVO {
  /** 댓글 번호 */
  private int commentsno;
  
  /** 댓글 내용 */
  private String contents;
  
  /** 댓글 작성일자 */
  private String rdate;
  
  /** 댓글 작성 아이디 */
  private String id;
  
  /** 댓글에 작성된 대댓글 갯수 */
  private String replycnt;
  
  /** 댓글 작성 회원 번호 */
  private int memberno;
  
  /** 댓글 작성 게시글 번호 */
  private int contentsno;
  
  /** 댓글 작성 회원 프로필 이미지*/
  private String thumbs;
  
  /** 댓글 작성 회원 등급 */
  private int grade;
}
