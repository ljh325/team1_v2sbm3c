package dev.mvc.review;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.keyword.KeywordProc;
import dev.mvc.keyword.KeywordVO;

@Component("dev.mvc.review.ReviewProc")
public class ReviewProc implements ReviewProcInter {
  
  
  @Autowired
  private ReviewDAOInter reviewDAO;
  
  @Autowired
  private KeywordProc keywordProc;
  
  @Override
  public int review_insert(ReviewVO reviewVO) {
    int cnt = this.reviewDAO.review_insert(reviewVO);
    return cnt;
  }

  @Override
  public ArrayList<ReviewVO> review_list_all() {
    ArrayList<ReviewVO> list = this.reviewDAO.review_list_all();
    ArrayList<KeywordVO> keywords = keywordProc.keyword_all();
    
    for (ReviewVO review : list) {
      String content = review.getContents();
      for (KeywordVO keyword : keywords) {
        if (content.contains(keyword.getWord())) {
          content = content.replaceAll("(?i)" + keyword.getWord(), "<span style='color:red;'>" + keyword.getWord() + "</span>");
          // (?!) -> (정규표현식)대소문자를 구분하지 않고 매칭을 수행하도록 지정합니다.
        }
      }
      review.setContents(content);
    }
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
  public int reviewno_max() {
    int cnt = this.reviewDAO.reviewno_max();
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

  @Override
  public float avg_cnt() {
    float cnt = this.reviewDAO.avg_cnt();
    return cnt;
  }

  @Override
  public int star_cnt(int star) {
    int cnt = this.reviewDAO.star_cnt(star);
    return cnt;
  }

  @Override
  public ArrayList<ReviewVO> admin_read_review(String keywordname) {
    ArrayList<ReviewVO> list = this.reviewDAO.admin_read_review(keywordname);
    return list;
  }

  @Override
  public ArrayList<ReviewVO> admin_read_review_cold(String keywordname) {
    ArrayList<ReviewVO> list = this.reviewDAO.admin_read_review_cold(keywordname);
    return list;
  }

  @Override
  public ReviewVO admin_review_detail(int reviewno) {
    ReviewVO reviewVO = this.reviewDAO.admin_review_detail(reviewno);
    return reviewVO;
  }



}
