package dev.mvc.review;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.member.MemberDAOInter;

@Component("dev.mvc.review.ReviewProc")
public class ReviewProc implements ReviewProcInter {
  
  
  @Autowired
  private ReviewDAOInter reviewDAO;
  
  @Override
  public int review_insert(ReviewVO reviewVO) {
    int cnt = this.reviewDAO.review_insert(reviewVO);
    return cnt;
  }

  @Override
  public ArrayList<ReviewVO> review_list_all(String reviewno) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<ReviewVO> list_by_review_paging(HashMap<String, Object> hashmap) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int review_cnt() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int review_update(ReviewVO reviewVO) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int delete(int reviewno) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public ReviewVO list_star_high(int star) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ReviewVO list_star_low(int star) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ReviewVO recent_review() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ReviewVO old_review() {
    // TODO Auto-generated method stub
    return null;
  }

}
