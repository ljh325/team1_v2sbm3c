package dev.mvc.reply;

import java.util.ArrayList;

public interface ReplyDAOInter {
  
  public int create(ReplyVO replyVO);
  
  public ReplyVO read(int replyno);
  
  public ArrayList<ReplyVO> reply_list(int commentsno);
  
  public ArrayList<ReplyVO> reply_member_list(int memberno);
  
  public int reply_count(int commentsno);
  
  public int reply_contents_count(int contentsno);
  
  public int reply_member_count(int memberno);
  
  public int update(ReplyVO replyVO);
  
  public int delete(int replyno);
  
}
