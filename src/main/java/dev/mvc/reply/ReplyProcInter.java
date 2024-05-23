package dev.mvc.reply;

import java.util.ArrayList;

public interface ReplyProcInter {
  
  /**
   * 대댓글 생성
   * @param replyVO
   * @return 대댓글 생성 여부
   */
  public int create(ReplyVO replyVO);
  
  /** 
   *  대댓글 정보 조회
   * @param replyno
   * @return 대댓글 정보
   */
  public ReplyVO read(int replyno);
  
  /**
   * 댓글 대댓글 목록 조회
   * @param commentsno
   * @return 대댓글 목록
   */
  public ArrayList<ReplyVO> reply_list(int commentsno);
  
  /**
   * 회원이 작성한 대댓글 목록 조회
   * @param memberno
   * @return 대댓글 목록
   */
  public ArrayList<ReplyVO> reply_member_list(int memberno);
  
  /**
   * 댓글에 등록된 대댓글 갯수
   * @param commentsno
   * @return 대댓글 갯수
   */
  public int reply_count(int commentsno);
  
  /**
   * 게시글에 등록된 대댓글 갯수
   * @param contentsno
   * @return 대댓글 갯수
   */
  public int reply_contents_count(int contentsno);
  
  /**
   * 회원이 등록한 대댓글 갯수
   * @param memberno
   * @return 대댓글 갯수
   */
  public int reply_member_count(int memberno);
  
  /**
   * 대댓글 수정
   * @param replyVO
   * @return 수정 여부
   */
  public int update(ReplyVO replyVO);
  
  /**
   * 대댓글 삭제
   * @param replyno
   * @return 삭제 여부
   */
  public int delete(int replyno);
  
  /**
   * 댓글 내에 대댓글 삭제
   * @param replyno
   * @return 삭제 여부
   */
  public int delete_all(int commentsno);
}
