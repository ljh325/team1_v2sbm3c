package dev.mvc.review;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mvc.member.MemberVO;
import jakarta.servlet.http.HttpSession;


@RequestMapping("/review")
@Controller
public class ReviewCont {

  @Autowired
  @Qualifier("dev.mvc.review.ReviewProc")  
  private ReviewProcInter reviewProc;
  
  
  
  
  /***************************************************************************************/  
  /**
   * 리뷰 등록 폼
   * @param model
   * @param contentsVO
   * @param cateno
   * @return
   */
  @GetMapping(value = "/review_insert_form") // http://localhost:9093/review/review_insert
  public String review_insert_form(Model model, ReviewVO reviewVO) {
    
    return "review/review_insert"; // /templates/review/review_insert.html
  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
   * 리뷰 등록 Proc
   * @param model
   * @param reviewVO
   * @return
   */
  @PostMapping(value="/review_insert")
  public String review_insert(HttpSession session, Model model, ReviewVO reviewVO) {
    int memberno = (int) session.getAttribute("memberno"); 
    reviewVO.setMemberno(memberno);
    System.out.println(reviewVO);
    int cnt = this.reviewProc.review_insert(reviewVO);
    model.addAttribute("cnt", cnt);
    

    return "redirect:/"; // /templates/index.html 일단 메인화면
  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
   * 전체 리뷰 목록
   * @param model
   * @return
   */
  @GetMapping(value="/review_list_all") // http://localhost:9093/review/review_list_all
  public String review_list_all(Model model) {
      int foodcateno = 3;
      System.out.println("foodcateno-->" + foodcateno);
    
    ArrayList<ReviewVO> reviewVO = this.reviewProc.review_list_all(foodcateno);
    model.addAttribute("reviewVO", reviewVO);
    System.out.println("reviewVO-->" + reviewVO);

    return "review/review_list_all";  // templates/review/review_list_all.html    
  }
  /***************************************************************************************/
}


