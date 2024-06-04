package dev.mvc.review;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import jakarta.servlet.http.HttpSession;


@RequestMapping("/review")
@Controller
public class ReviewCont {

  @Autowired
  @Qualifier("dev.mvc.review.ReviewProc")  
  private ReviewProcInter reviewProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")  // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  
  /***************************************************************************************/  
  /**
   * 리뷰 등록 폼
   * @param model
   * @param contentsVO
   * @param cateno
   * @return
   */
  @GetMapping(value = "/review_insert_form") // http://localhost:9093/review/review_insert?memberno=${memberno}
  public String review_insert_form(HttpSession session, Model model, int memberno) {

    if (this.memberProc.isMember(session)) { // 세션에서 가져옴 / 로그인 상태: True, 비로그인상태: False
      // 로그인 상태면 리뷰등록폼으로 이동
      
      MemberVO memberVO = this.memberProc.read(memberno); // memberno로 회원정보 조회
      model.addAttribute("memberVO", memberVO);
      return "review/review_insert"; // /templates/review/review_insert.html
    } else { 
      return "member/login_need_form"; // 로그인상태가 아니라면 로그인 폼으로 이동
    }
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
    System.out.println("starstarstarstar---->" + reviewVO.getStar());
    int cnt = this.reviewProc.review_insert(reviewVO);
    model.addAttribute("cnt", cnt);
    

    return "redirect:/review/review_list_form"; // /templates/index.html 일단 메인화면
  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
   * 리뷰 (최근목록, 오래된 목록, 별점 높은 목록, 낮은 목록)
   * @param sort
   * @param model
   * @return
   */
  @GetMapping(value="/review_list_all", produces = MediaType.APPLICATION_JSON_VALUE) // 비동기 통신 코드
  @ResponseBody
  public String review_list_all(@RequestParam(required = false, defaultValue = "recent") String sort, Model model) {
      // 리뷰 목록 정렬을 위한 JSONArray 객체 생성
      JSONArray array = new JSONArray();

      // 정렬 기준에 따라 리뷰 목록을 처리
      switch (sort) {
          // 별점 높은 순으로 정렬
          case "star_high":
              // 별점 높은 순으로 정렬된 리뷰 목록 가져오기
              ArrayList<ReviewVO> highStarList = reviewProc.list_star_high();
              // JSON 객체로 변환하여 JSONArray에 추가
              for (ReviewVO review : highStarList) {
                  JSONObject reviewJson = new JSONObject();
                  reviewJson.put("reviewno", review.getReviewno());
                  reviewJson.put("id", review.getId());
                  reviewJson.put("star", review.getStar());
                  reviewJson.put("rdate", review.getRdate());
                  reviewJson.put("contents", review.getContents());
                  array.put(reviewJson);
              }
              break;

          // 별점 낮은 순으로 정렬
          case "star_low":
              // 별점 낮은 순으로 정렬된 리뷰 목록 가져오기
              ArrayList<ReviewVO> lowStarList = reviewProc.list_star_low();
              // JSON 객체로 변환하여 JSONArray에 추가
              for (ReviewVO review : lowStarList) {
                  JSONObject reviewJson = new JSONObject();
                  reviewJson.put("reviewno", review.getReviewno());
                  reviewJson.put("id", review.getId());
                  reviewJson.put("star", review.getStar());
                  reviewJson.put("rdate", review.getRdate());
                  reviewJson.put("contents", review.getContents());
                  array.put(reviewJson);
              }
              break;

          // 최근 작성 순으로 정렬
          case "recent":
              // 최근 작성된 리뷰 목록 가져오기
              ArrayList<ReviewVO> recentList = reviewProc.recent_review();
              // JSON 객체로 변환하여 JSONArray에 추가
              for (ReviewVO review : recentList) {
                  JSONObject reviewJson = new JSONObject();
                  reviewJson.put("reviewno", review.getReviewno());
                  reviewJson.put("id", review.getId());
                  reviewJson.put("star", review.getStar());
                  reviewJson.put("rdate", review.getRdate());
                  reviewJson.put("contents", review.getContents());
                  array.put(reviewJson);
              }
              break;

          // 오래된 순으로 정렬 (기본값)
          case "old":
          default:
              // 모든 리뷰 목록 가져오기
              ArrayList<ReviewVO> allList = reviewProc.old_review();
              // JSON 객체로 변환하여 JSONArray에 추가
              for (ReviewVO review : allList) {
                  JSONObject reviewJson = new JSONObject();
                  reviewJson.put("reviewno", review.getReviewno());
                  reviewJson.put("id", review.getId());
                  reviewJson.put("star", review.getStar());
                  reviewJson.put("rdate", review.getRdate());
                  reviewJson.put("contents", review.getContents());
                  array.put(reviewJson);
              }
              break;
      }
      // JSONArray를 문자열로 반환하여 클라이언트에 전달
      return array.toString();
  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
   * 리뷰 전체 목록
   * @param model
   * @return
   */
  @GetMapping(value="review_list_form") //http://localhost:9093/review/review_list_form
  public String review_list_form(Model model) {
    
    // 리뷰 총 수를 모델에 추가
    int review_cnt = this.reviewProc.review_cnt();
    model.addAttribute("review_cnt", review_cnt);
    
    return "review/review_list_all"; //  /templates/review/review_list_all.html
  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
   * 리뷰 수정 폼
   * @param session
   * @param model
   * @param reviewVO
   * @param reviewno
   * @return
   */
  @GetMapping(value="review_update_form") //http://localhost:9093/review/review_update_form
  public String review_update_form(HttpSession session, Model model, ReviewVO reviewVO, int reviewno) {

    if (this.memberProc.isMember(session)) { // 세션에서 가져옴 / 로그인 상태: True, 비로그인상태: False
      // 로그인 상태면 리뷰등록폼으로 이동
      
      HashMap<String, Object> map = new HashMap<>();
      int memberno = (int)session.getAttribute("memberno");
      
      MemberVO memberVO = this.memberProc.read(memberno); // memberno로 회원정보 조회
      model.addAttribute("memberVO", memberVO);
      
      map.put("memberno", memberno);
      map.put("reviewno", reviewno);
      
      reviewVO = this.reviewProc.review_read(map);
      model.addAttribute("reviewVO", reviewVO);
      
      return "review/review_update"; //  /templates/review/review_update.html
    } else { // 로그인상태가 아니라면 로그인 폼으로 이동
      return "member/login_need_form";  //  /templates/member/login_need_form.html
    }
    
  }
  /**
   * 리뷰 수정 proc
   * @param model
   * @param reviewVO
   * @param reviewno
   * @return
   */
  @PostMapping(value="/review_update_proc")
  public String review_update_proc(Model model, ReviewVO reviewVO) {
    
    int cnt = this.reviewProc.review_update(reviewVO);
    model.addAttribute("cnt", cnt);

    return "redirect:/review/review_list_form"; // http://localhost:9093/review/review_list_form
  }
 /***************************************************************************************/
  
  
  /***************************************************************************************/
  
  /**
   * 리뷰 삭제
   * @param model
   * @return
   */
  @PostMapping(value="review_delete") 
  public String review_delete(Model model, int reviewno) {
    
    int cnt = this.reviewProc.review_delete(reviewno);
    model.addAttribute("cnt", cnt);
    
    return "review/review_list_form"; //  /templates/review/review_list_form.html
  }
  
}


