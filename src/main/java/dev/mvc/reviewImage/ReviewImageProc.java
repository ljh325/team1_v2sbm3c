package dev.mvc.reviewImage;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("dev.mvc.reviewImage.ReviewImageProc")
public class ReviewImageProc implements ReviewImageProcInter {

  
  @Autowired
  private ReviewImageDAOInter reviewImageDAO;
  
  @Override
  public int insert_image(ReviewImageVO reviewImageVO) {
    int cnt = this.reviewImageDAO.insert_image(reviewImageVO);
    return cnt;
  }

  @Override
  public ArrayList<ReviewImageVO> list_image() {
    ArrayList<ReviewImageVO> list = this.reviewImageDAO.list_image();
    return list;
  }

  @Override
  public ReviewImageVO read_image(int reviewno) {
    ReviewImageVO reviewImageVO = this.reviewImageDAO.read_image(reviewno);
    return reviewImageVO;
  }

  @Override
  public int update_image(ReviewImageVO reviewImageVO) {
    int cnt = this.reviewImageDAO.update_image(reviewImageVO);
    return cnt;
  }

  @Override
  public int delete_image(int reviewno) {
    int cnt = this.reviewImageDAO.delete_image(reviewno);
    return cnt;
  }



}
