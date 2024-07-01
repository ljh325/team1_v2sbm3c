package dev.mvc.follows;

import java.util.ArrayList;
import java.util.HashMap;

public interface FollowsDAOInter {

  
  /**
   * 팔로우 관계를 추가
   * @param followsVO
   * @return
   */
  public int follower_insert(FollowsVO followsVO);
  
  /**
   * 내가 팔로우 한 사람이 있는지 확인
   * @param followsVO
   * @return
   */
  public int exist_cnt(FollowsVO followsVO);
  
  /**
   * Alice가 팔로우하는 사람들
   * @param follower_no
   * @return
   */
  public FollowsVO following_read(int follower_no);
  
  /**
   * Alice가 팔로우당한 사람들
   * @param followed_no
   * @return
   */
  public FollowsVO follower_read(int followed_no);
  
  /**
   * 내가 팔로잉 한 사람들 조회
   * @param follower_no
   * @return
   */
  public ArrayList<FollowsVO> following_member_read(int follower_no);
  
  /**
   * 다른 사람이 나를 팔로우 한 사람들 조회
   * @param followed_no
   * @return
   */
  public ArrayList<FollowsVO> follower_member_read(int followed_no);
  
  /**
   * 팔로우한게시글 조회
   * @param follower_no
   * @return
   */
  public ArrayList<FollowsVO> sns_follow_member_read(int follower_no);
  
  /**
   * 현재 내가 팔로잉 한 수
   * @param follower_no
   * @return
   */
  public int following_cnt(int follower_no);
  
  /**
   * 현재 내 팔로우 수
   * @param followed_no
   * @return
   */
  public int follower_cnt(int followed_no);
  
  /**
   * tom이 절교하려고 친삭할때 친구 삭제
   * @param map
   * @return
   */
  public int delete_friends(HashMap<String, Object> map);
  
}
