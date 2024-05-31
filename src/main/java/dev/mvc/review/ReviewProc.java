package dev.mvc.review;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
  public ArrayList<ReviewVO> review_list_all(int foodcateno) {
    ArrayList<ReviewVO> list = this.reviewDAO.review_list_all(foodcateno);
    return list;
  }

  @Override
  public ArrayList<ReviewVO> list_by_review_paging(HashMap<String, Object> hashmap) {
    ArrayList<ReviewVO> list = this.reviewDAO.list_by_review_paging(hashmap); 
    return list;
  }

  @Override
  public int review_cnt() {
    int cnt = this.reviewDAO.review_cnt();
    return cnt;
  }

  @Override
  public int review_update(ReviewVO reviewVO) {
    int cnt = this.reviewDAO.review_update(reviewVO);
    return cnt;
  }

  @Override
  public int delete(int reviewno) {
    int cnt = this.reviewDAO.delete(reviewno);
    return cnt;
  }

  @Override
  public ReviewVO list_star_high(int star) {
    ReviewVO high = this.reviewDAO.list_star_high(star);
    return high;
  }

  @Override
  public ReviewVO list_star_low(int star) {
    ReviewVO low = this.reviewDAO.list_star_low(star);
    return low;
  }

  @Override
  public ReviewVO recent_review() {
    ReviewVO recent = this.reviewDAO.recent_review();
    return recent;
  }

  @Override
  public ReviewVO old_review() {
    ReviewVO old = this.reviewDAO.old_review();
    return old;
  }

}


