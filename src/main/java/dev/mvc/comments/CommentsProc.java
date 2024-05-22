package dev.mvc.comments;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.comments.CommentsProc")
public class CommentsProc implements CommentsProcInter {
  
  @Autowired
  private CommentsDAOInter commentsDAO;
  
  @Override
  public int create(CommentsVO commentsVO) {
    int cnt = this.commentsDAO.create(commentsVO);
    return cnt;
  }

  @Override
  public ArrayList<CommentsVO> comment_list(int contentsno) {
    ArrayList<CommentsVO> list = this.commentsDAO.comment_list(contentsno);
    return list;
  }

  
  @Override
  public CommentsVO read(int commentsno) {
    CommentsVO commentsVO = this.commentsDAO.read(commentsno);
    return commentsVO;
  }

  @Override
  public int comments_count(int contentsno) {
    int cnt = this.commentsDAO.comments_count(contentsno);
    return cnt;
  }

  @Override
  public int update(CommentsVO commentsVO) {
    int cnt = this.commentsDAO.update(commentsVO);
    return cnt;
  }

  @Override
  public int delete(int commentsno) {
    int cnt = this.commentsDAO.delete(commentsno);
    return cnt;
  }

}
