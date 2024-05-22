package dev.mvc.comments;

import java.util.ArrayList;

public interface CommentsDAOInter {
  /**
   * 댓글 생성
   * @param commentsVO
   * @return 댓글 생성 갯수
   */
  public int create(CommentsVO commentsVO);
  
  /**
   * 댓글 조회
   * @return 댓글 목록
   */
  public ArrayList<CommentsVO> comment_list(int contentsno);
  
  /**
   * 댓글 정보 조회
   * @param commentsno
   * @return
   */
  public CommentsVO read(int commentsno);
  
  /**
   * 게시글에 등록된 댓글 갯수
   * @param contentsno
   * @return 등록된 댓글 갯수
   */
  public int comments_count(int contentsno);
  
  /**
   * 댓글 수정
   * @param commentsVO
   * @return 수정 여부
   */
  public int update(CommentsVO commentsVO);
  
  /**
   * 댓글 삭제
   * @param commentsno
   * @return 삭제 여부
   */
  public int delete(int commentsno);
  
  
  
}
