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
  public ArrayList<ReviewVO> review_list_all() {
    ArrayList<ReviewVO> list = this.reviewDAO.review_list_all();
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
  public int review_delete(int reviewno) {
    int cnt = this.reviewDAO.review_delete(reviewno);
    return cnt;
  }

  @Override
  public ArrayList<ReviewVO> list_star_high() {
    ArrayList<ReviewVO> high = this.reviewDAO.list_star_high();
    return high;
  }

  @Override
  public ArrayList<ReviewVO> list_star_low() {
    ArrayList<ReviewVO> low = this.reviewDAO.list_star_low();
    return low;
  }

  @Override
  public ArrayList<ReviewVO> recent_review() {
    ArrayList<ReviewVO> recent = this.reviewDAO.recent_review();
    return recent;
  }

  @Override
  public ArrayList<ReviewVO> old_review() {
    ArrayList<ReviewVO> old = this.reviewDAO.old_review();
    return old;
  }

  @Override
  public ReviewVO review_read(HashMap<String, Object> hashmap) {
    ReviewVO reviewVO = this.reviewDAO.review_read(hashmap);
    return reviewVO;
  }

}


