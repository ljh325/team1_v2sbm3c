package dev.mvc.review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.adreply.AdreplyProcInter;
import dev.mvc.adreply.AdreplyVO;
import dev.mvc.keyword.KeywordDAOInter;
import dev.mvc.keyword.KeywordProc;
import dev.mvc.keyword.KeywordProcInter;
import dev.mvc.keyword.KeywordVO;
import dev.mvc.member.Member;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import dev.mvc.reviewImage.ReviewImage;
import dev.mvc.reviewImage.ReviewImageProc;
import dev.mvc.reviewImage.ReviewImageProcInter;
import dev.mvc.reviewImage.ReviewImageVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
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
  
  @Autowired
  @Qualifier("dev.mvc.keyword.KeywordProc") 
  private KeywordProcInter keywordProc;
  
  @Autowired
  @Qualifier("dev.mvc.reviewImage.ReviewImageProc") 
  private ReviewImageProcInter reviewImageProc;
  
  @Autowired
  @Qualifier("dev.mvc.adreply.AdreplyProc")
  private AdreplyProcInter adreplyProc;
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
  public String review_insert(HttpSession session, 
                                    Model model, 
                                    ReviewVO reviewVO, 
                                    ReviewImageVO reviewImageVO, 
                                    RedirectAttributes ra) {
    System.out.println("reviewImageVO" + reviewImageVO);
    
//    int memberno = (int) session.getAttribute("memberno"); 
//    reviewVO.setMemberno(memberno);
    System.out.println("starstarstarstar---->" + reviewVO.getStar());
    System.out.println("reviewVO.getReviewno() -> " + reviewVO.getReviewno());
    int cnt = this.reviewProc.review_insert(reviewVO);
    model.addAttribute("cnt", cnt);
    System.out.println(cnt);
    
    //model.addAttribute(reviewVO.getReviewno());
    System.out.println("reviewVO.getReviewno() -> " + reviewVO.getReviewno());
    
    
    // ------------------------------------------------------------------------------
    // 파일 전송 코드 시작
    // ------------------------------------------------------------------------------
    String profile = "";          // 원본 파일명 image
    String profilesaved = "";   // 저장된 파일명, image
    String thumbs = "";     // preview image

    String upDir =  ReviewImage.getUploadDir(); // 파일을 업로드할 폴더 준비
    System.out.println("-> upDir: " + upDir);
    
    // 전송 파일이 없어도 files1MF 객체가 생성됨.
    // <input type='file' class="form-control" name='files1MF' id='files1MF' 
    //           value='' placeholder="파일 선택">
    ArrayList<MultipartFile> mf = reviewImageVO.getFiles1MF();
    
    int count = mf.size(); // 전송 파일 갯수
    
    if(count > 0) {
      for (MultipartFile multipartFile:mf) { // 파일 추출, 1개이상 파일 처리
        profile = multipartFile.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
        System.out.println("-> 원본 파일명 산출 file1: " + profile);
        long sizes = multipartFile.getSize(); // 파일 크기
        if (sizes > 0) { // 파일 크기 체크, 파일을 올리는 경우
          if (Tool.checkUploadFile(profile) == true) { // 업로드 가능한 파일인지 검사
            // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
            profilesaved = Upload.saveFileSpring(multipartFile, upDir); 
            
            if (Tool.isImage(profilesaved)) { // 이미지인지 검사
              // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
              thumbs = Tool.preview(upDir, profilesaved, 200, 150); 
            }

            reviewImageVO.setProfile(profile);   // 순수 원본 파일명
            reviewImageVO.setProfilesaved(profilesaved); // 저장된 파일명(파일명 중복 처리)
            reviewImageVO.setThumbs(thumbs);      // 원본이미지 축소판
            reviewImageVO.setSizes(sizes);  // 파일 크기
            
            
            int reviewno = this.reviewProc.reviewno_max();
            System.out.println("reviewno" + reviewno);
            reviewImageVO.setReviewno(reviewno);
            int image_cnt = this.reviewImageProc.insert_image(reviewImageVO);
            model.addAttribute("image_cnt", image_cnt);
            if (image_cnt == 1) { // 성공: 1 했을 경우
              System.out.println("이미지 등록 성공");
            } else { // 실패: 0 
              System.out.println("이미지 등록 실패");
            }
          }else { // 전송 못하는 파일 형식
            ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
            ra.addFlashAttribute("cnt", 0); // 업로드 실패
            ra.addFlashAttribute("url", "/member/msg"); // msg.html, redirect parameter 적용
             // Post -> Get - param...
          }
        } else { // 글만 등록하는 경우
          System.out.println("-> 글만 등록");
        }
      }
    }
    // ------------------------------------------------------------------------------
    // 파일 전송 코드 종료
    // ------------------------------------------------------------------------------
  


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
  public String review_list_all(@RequestParam(required = false, defaultValue = "recent") 
                                String sort, Model model, ReviewImageVO reviewImageVO, 
                                HttpSession session) {

      
      
      // 리뷰 목록 정렬을 위한 JSONArray 객체 생성
      JSONArray array = new JSONArray();
      
      ArrayList<KeywordVO> keywords = keywordProc.keyword_all(); // 키워드
      
      ArrayList<ReviewVO> reviewList;
      switch (sort) {
          case "star_high":
              reviewList = reviewProc.list_star_high();
              break;
          case "star_low":
              reviewList = reviewProc.list_star_low();
              break;
          case "recent":
              reviewList = reviewProc.recent_review();
              break;
          case "old":
          default:
              reviewList = reviewProc.old_review();
              break;
      }
      for (ReviewVO review : reviewList) {
        ArrayList<ReviewImageVO> images = this.reviewImageProc.read_image(review.getReviewno());
        MemberVO members = this.memberProc.read(review.getMemberno());
        AdreplyVO adreply = this.adreplyProc.admin_read(review.getReviewno());
        ArrayList<KeywordVO> keywordlist = this.keywordProc.keyword_all_read(review.getReviewno());
        JSONObject reviewJson = new JSONObject();
        reviewJson.put("reviewno", review.getReviewno());
        reviewJson.put("id", review.getId());
        reviewJson.put("star", review.getStar());
        reviewJson.put("rdate", review.getRdate());
        reviewJson.put("udate", review.getUdate());
        if(review.getTemperater() == 2) {
          reviewJson.put("contents", highlightKeywords(review.getContents(), keywordlist));
        } else if(review.getTemperater() == 1) {
          reviewJson.put("contents", negativeKeywords(review.getContents(), keywordlist));
        } else {
          reviewJson.put("contents", review.getContents());
        }
        
        reviewJson.put("nickname", members.getNickname());
        reviewJson.put("profile", members.getProfile());
        reviewJson.put("profilesaved", members.getProfilesaved());
        reviewJson.put("thumbs", members.getThumbs());
        reviewJson.put("sizes", members.getSizes());
        
        if(adreply != null) {
          reviewJson.put("adcontents", adreply.getAdcontents());
          reviewJson.put("addate", adreply.getAddate());
          reviewJson.put("adupdate", adreply.getAdupdate());
        } 

        
//        JSONArray keywordlists = new JSONArray();
//        for (KeywordVO keyword : keywordlist) {
//          JSONObject  keywordJson = new JSONObject();
//          keywordJson.put("contents", highlightKeywords(review.getContents(), keywordlist));
//        }
//        reviewJson.put("keywordlists", keywordlists);
        
        JSONArray imageArray = new JSONArray();
        for (ReviewImageVO image : images) {
            JSONObject imageJson = new JSONObject();
            imageJson.put("imageno", image.getImageno());
            imageJson.put("profile", image.getProfile());
            imageJson.put("profilesaved", image.getProfilesaved());
            imageJson.put("thumbs", image.getThumbs());
            imageJson.put("sizes", image.getSizes());
            imageArray.put(imageJson);
        }
        reviewJson.put("images", imageArray);

        array.put(reviewJson);
    }
//      // 정렬 기준에 따라 리뷰 목록을 처리
//      switch (sort) {
//          // 별점 높은 순으로 정렬
//          case "star_high":
//              // 별점 높은 순으로 정렬된 리뷰 목록 가져오기
//              ArrayList<ReviewVO> highStarList = reviewProc.list_star_high();
//              // JSON 객체로 변환하여 JSONArray에 추가
//              for (ReviewVO review : highStarList) {
//                  ReviewImageVO image = this.reviewImageProc.read_image(review.getReviewno());
//                  JSONObject reviewJson = new JSONObject();
//                  reviewJson.put("reviewno", review.getReviewno());
//                  reviewJson.put("id", review.getId());
//                  reviewJson.put("star", review.getStar());
//                  reviewJson.put("rdate", review.getRdate());
//                  reviewJson.put("contents", highlightKeywords(review.getContents(), keywords));
//                  reviewJson.put("imageno", image.getImageno());
//                  reviewJson.put("profile", image.getProfile());
//                  reviewJson.put("profilesaved", image.getProfilesaved());
//                  reviewJson.put("thumbs", image.getThumbs());
//                  reviewJson.put("sizes", image.getSizes());
//                  array.put(reviewJson);
//              }
//              break;
//
//          // 별점 낮은 순으로 정렬
//          case "star_low":
//              // 별점 낮은 순으로 정렬된 리뷰 목록 가져오기
//              ArrayList<ReviewVO> lowStarList = reviewProc.list_star_low();
//              // JSON 객체로 변환하여 JSONArray에 추가
//              for (ReviewVO review : lowStarList) {
//                  ReviewImageVO image = this.reviewImageProc.read_image(review.getReviewno());
//                  JSONObject reviewJson = new JSONObject();
//                  reviewJson.put("reviewno", review.getReviewno());
//                  reviewJson.put("id", review.getId());
//                  reviewJson.put("star", review.getStar());
//                  reviewJson.put("rdate", review.getRdate());
//                  reviewJson.put("contents", highlightKeywords(review.getContents(), keywords));
//                  reviewJson.put("imageno", image.getImageno());
//                  reviewJson.put("profile", image.getProfile());
//                  reviewJson.put("profilesaved", image.getProfilesaved());
//                  reviewJson.put("thumbs", image.getThumbs());
//                  reviewJson.put("sizes", image.getSizes());
//                  array.put(reviewJson);
//              }
//              break;
//
//          // 최근 작성 순으로 정렬
//          case "recent":
//              // 최근 작성된 리뷰 목록 가져오기
//              ArrayList<ReviewVO> recentList = reviewProc.recent_review();
//              // JSON 객체로 변환하여 JSONArray에 추가
//              for (ReviewVO review : recentList) {
//                  ReviewImageVO image = this.reviewImageProc.read_image(review.getReviewno());
//                  JSONObject reviewJson = new JSONObject();
//                  reviewJson.put("reviewno", review.getReviewno());
//                  reviewJson.put("id", review.getId());
//                  reviewJson.put("star", review.getStar());
//                  reviewJson.put("rdate", review.getRdate());
//                  reviewJson.put("contents", highlightKeywords(review.getContents(), keywords));
//                  reviewJson.put("imageno", image.getImageno());
//                  reviewJson.put("profile", image.getProfile());
//                  reviewJson.put("profilesaved", image.getProfilesaved());
//                  reviewJson.put("thumbs", image.getThumbs());
//                  reviewJson.put("sizes", image.getSizes());
//                  array.put(reviewJson);
//              }
//              break;
//
//          // 오래된 순으로 정렬 (기본값)
//          case "old":
//          default:
//              // 모든 리뷰 목록 가져오기
//              ArrayList<ReviewVO> allList = reviewProc.old_review();
//              // JSON 객체로 변환하여 JSONArray에 추가
//              for (ReviewVO review : allList) {
//                  ReviewImageVO image = this.reviewImageProc.read_image(review.getReviewno());
//                  JSONObject reviewJson = new JSONObject();
//                  reviewJson.put("reviewno", review.getReviewno());
//                  reviewJson.put("id", review.getId());
//                  reviewJson.put("star", review.getStar());
//                  reviewJson.put("rdate", review.getRdate());
//                  reviewJson.put("contents", highlightKeywords(review.getContents(), keywords));
//                  reviewJson.put("imageno", image.getImageno());
//                  reviewJson.put("profile", image.getProfile());
//                  reviewJson.put("profilesaved", image.getProfilesaved());
//                  reviewJson.put("thumbs", image.getThumbs());
//                  reviewJson.put("sizes", image.getSizes());
//                  array.put(reviewJson);
//              }
//              break;
//      }
      // JSONArray를 문자열로 반환하여 클라이언트에 전달
      return array.toString();
  }
  /***************************************************************************************/
  /***************************************************************************************/
  /***************************************************************************************/
  // 긍정 의 리뷰 키워드 색깔 입히기
  private String highlightKeywords(String content, ArrayList<KeywordVO> keywords) {
    
    for (KeywordVO keyword : keywords) {
        System.out.println("keywords--?>" + keyword.getWord());
        content = content.replaceAll("(?i)" + keyword.getWord(), "<span style='background-color:#81F79F;'>" + keyword.getWord() + "</span>");
        System.out.println("content--?>" + content);
    }
    return content;
}
  // 부정 의 리뷰 키워드 색깔 입히기
  private String negativeKeywords(String content, ArrayList<KeywordVO> keywords) {
    
    for (KeywordVO keyword : keywords) {
        System.out.println("keywords--?>" + keyword.getWord());
        content = content.replaceAll("(?i)" + keyword.getWord(), "<span style='background-color:#FF90FF;'>" + keyword.getWord() + "</span>");
        System.out.println("content--?>" + content);
    }
    return content;
}
  /***************************************************************************************/
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
    float avg_cnt =this.reviewProc.avg_cnt();
   
    
   
    model.addAttribute("review_cnt", review_cnt);
    model.addAttribute("avg_cnt", avg_cnt);
    
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
  public String review_update_proc(Model model, 
                                   ReviewVO reviewVO,
                                   ReviewImageVO reviewImageVO, 
                                   RedirectAttributes ra) {
    this.reviewImageProc.delete_image(reviewVO.getReviewno());
    //highlightKeywords("contents", reviewVO.getContents());
    int cnt = this.reviewProc.review_update(reviewVO);
    model.addAttribute("cnt", cnt);

    System.out.println("reviewno" + reviewVO.getReviewno());
    // ------------------------------------------------------------------------------
    // 파일 전송 코드 시작
    // ------------------------------------------------------------------------------
    String profile = "";          // 원본 파일명 image
    String profilesaved = "";   // 저장된 파일명, image
    String thumbs = "";     // preview image

    String upDir =  ReviewImage.getUploadDir(); // 파일을 업로드할 폴더 준비
    System.out.println("-> upDir: " + upDir);
    
    // 전송 파일이 없어도 files1MF 객체가 생성됨.
    // <input type='file' class="form-control" name='files1MF' id='files1MF' 
    //           value='' placeholder="파일 선택">
    ArrayList<MultipartFile> mf = reviewImageVO.getFiles1MF();
    
    int count = mf.size(); // 전송 파일 갯수
    
    if(count > 0) {
      for (MultipartFile multipartFile:mf) { // 파일 추출, 1개이상 파일 처리
        profile = multipartFile.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
        System.out.println("-> 원본 파일명 산출 file1: " + profile);
        long sizes = multipartFile.getSize(); // 파일 크기
        if (sizes > 0) { // 파일 크기 체크, 파일을 올리는 경우
          if (Tool.checkUploadFile(profile) == true) { // 업로드 가능한 파일인지 검사
            // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
            profilesaved = Upload.saveFileSpring(multipartFile, upDir); 
            
            if (Tool.isImage(profilesaved)) { // 이미지인지 검사
              // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
              thumbs = Tool.preview(upDir, profilesaved, 200, 150); 
            }

            reviewImageVO.setProfile(profile);   // 순수 원본 파일명
            reviewImageVO.setProfilesaved(profilesaved); // 저장된 파일명(파일명 중복 처리)
            reviewImageVO.setThumbs(thumbs);      // 원본이미지 축소판
            reviewImageVO.setSizes(sizes);  // 파일 크기
            
            
            int reviewno = reviewVO.getReviewno();
            System.out.println("reviewno" + reviewno);
            reviewImageVO.setReviewno(reviewno);
            int image_cnt = this.reviewImageProc.insert_image(reviewImageVO);
            model.addAttribute("image_cnt", image_cnt);
            if (image_cnt == 1) { // 성공: 1 했을 경우
              System.out.println("이미지 등록 성공");
            } else { // 실패: 0 
              System.out.println("이미지 등록 실패");
            }
          }else { // 전송 못하는 파일 형식
            ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
            ra.addFlashAttribute("cnt", 0); // 업로드 실패
            ra.addFlashAttribute("url", "/member/msg"); // msg.html, redirect parameter 적용
             // Post -> Get - param...
          }
        } else { // 글만 등록하는 경우
          System.out.println("-> 글만 등록");
        }
      }
    }
    // ------------------------------------------------------------------------------
    // 파일 전송 코드 종료
    // ------------------------------------------------------------------------------
  
    return "redirect:/review/review_list_form"; // http://localhost:9093/review/review_list_form
  }
 /***************************************************************************************/
//  private String highlightKeywords(String content, ArrayList<KeywordVO> keywords) {
//    for (KeywordVO keyword : keywords) {
//        content = content.replaceAll("(?i)" + keyword.getWord(), "<span style='background-color:#81F79F;'>" + keyword.getWord() + "</span>");
//    }
//    return content;
//}
  
  /***************************************************************************************/
  
  /**
   * 리뷰 삭제
   * @param model
   * @return
   */
//  @PostMapping(value="review_delete", consumes = "application/json") 
//  public String review_delete(Model model, int reviewno) {
//    
//    int cnt = this.reviewProc.review_delete(reviewno);
//    model.addAttribute("cnt", cnt);
//    
//    return "review/review_list_form"; //  /templates/review/review_list_form.html
//  }
  
  
  @PostMapping(value="review_delete", consumes = "application/json")
  public ResponseEntity<Map<String, Object>> review_delete(@RequestBody Map<String, Object> payload) {
    String reviewnoStr = (String) payload.get("reviewno");
    
    if (reviewnoStr == null) {
        return ResponseEntity.badRequest().body(Map.of("error", "Review number is missing"));
    }

    try {
        int reviewno = Integer.parseInt(reviewnoStr);
        this.reviewImageProc.delete_image(reviewno); // 자식삭제
        this.keywordProc.keyword_delete(reviewno); // 자식 삭제
        int cnt = this.reviewProc.review_delete(reviewno); // 부모 삭제
        
        Map<String, Object> response = new HashMap<>();
        response.put("cnt", cnt);
        return ResponseEntity.ok(response);
    } catch (NumberFormatException e) {
        return ResponseEntity.badRequest().body(Map.of("error", "Invalid review number format"));
    }
  }
}


