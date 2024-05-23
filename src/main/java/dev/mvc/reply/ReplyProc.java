package dev.mvc.reply;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("dev.mvc.reply.ReplyProc")
public class ReplyProc implements ReplyProcInter {
  
  @Autowired
  private ReplyDAOInter replyDAO;
  
  @Override
  public int create(ReplyVO replyVO) {
    int cnt = this.replyDAO.create(replyVO);
    return cnt;
  }

  @Override
  public ReplyVO read(int replyno) {
    ReplyVO replyVO = this.replyDAO.read(replyno);
    return replyVO;
  }

  @Override
  public ArrayList<ReplyVO> reply_list(int commentsno) {
    ArrayList<ReplyVO> list = this.replyDAO.reply_list(commentsno);
    return list;
  }

  @Override
  public ArrayList<ReplyVO> reply_member_list(int memberno) {
    ArrayList<ReplyVO> list = this.replyDAO.reply_member_list(memberno);
    return list;
  }

  @Override
  public int reply_count(int commentsno) {
    int cnt = this.replyDAO.reply_count(commentsno);
    return cnt;
  }

  @Override
  public int reply_contents_count(int contentsno) {
    int cnt = this.replyDAO.reply_contents_count(contentsno);
    return cnt;
  }

  @Override
  public int reply_member_count(int memberno) {
    int cnt = this.replyDAO.reply_member_count(memberno);
    return cnt;
  }

  @Override
  public int update(ReplyVO replyVO) {
    int cnt = this.replyDAO.update(replyVO);
    return cnt;
  }

  @Override
  public int delete(int replyno) {
    int cnt = this.replyDAO.delete(replyno);
    return cnt;
  }

  @Override
  public int delete_all(int commentsno) {
    int cnt = this.replyDAO.delete_all(commentsno);
    return cnt;
  }
  
  

}
