package dev.mvc.follows;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component("dev.mvc.follows.FollowsProc")
public class FollowsProc implements FollowsProcInter {
  
  public FollowsProc(){
    System.out.println("-> FollowsProc created.");
  }

  @Autowired
  private FollowsDAOInter followsDAO;
  
  @Override
  public int follower_insert(FollowsVO followsVO) {
    int cnt = this.followsDAO.follower_insert(followsVO);
    return cnt;
  }
  
  @Override
  public int exist_cnt(FollowsVO followsVO) {
    int cnt = this.followsDAO.exist_cnt(followsVO);
    return cnt;
  }

  
  @Override
  public FollowsVO following_read(int follower_no) {
    FollowsVO followsVO = this.followsDAO.following_read(follower_no);
    return followsVO;
  }

  @Override
  public FollowsVO follower_read(int followed_no) {
    FollowsVO followsVO = this.followsDAO.follower_read(followed_no);
    return followsVO;
  }

  @Override
  public int following_cnt(int follower_no) {
    int cnt = this.followsDAO.following_cnt(follower_no); 
    return cnt;
  }

  @Override
  public int follower_cnt(int followed_no) {
    int cnt = followsDAO.follower_cnt(followed_no);
    return cnt;
  }

  @Override
  public int delete_friends(HashMap<String, Object> map) {
    int cnt = followsDAO.delete_friends(map);
    return cnt;
  }

  @Override
  public ArrayList<FollowsVO> following_member_read(int follower_no) {
    ArrayList<FollowsVO> list = this.followsDAO.following_member_read(follower_no);
    return list;
  }

  @Override
  public ArrayList<FollowsVO> follower_member_read(int followed_no) {
    ArrayList<FollowsVO> list = this.followsDAO.follower_member_read(followed_no);
    return list;
  }

  @Override
  public ArrayList<FollowsVO> sns_follow_member_read(int follower_no) {
    ArrayList<FollowsVO> list = this.followsDAO.sns_follow_member_read(follower_no);
    return list;
  }

}
