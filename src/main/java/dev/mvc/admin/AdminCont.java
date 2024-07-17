package dev.mvc.admin;

import java.util.ArrayList;
import java.util.HashMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mvc.adcontents.AdcontentsProcInter;
import dev.mvc.adcontents.AdcontentsVO;
import dev.mvc.alogin.AloginProcInter;
import dev.mvc.alogin.AloginVO;
import dev.mvc.keyword.KeywordProcInter;
import dev.mvc.keyword.KeywordVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.mlogin.MloginProcInter;
import dev.mvc.review.ReviewProcInter;
import dev.mvc.review.ReviewVO;
import dev.mvc.reviewImage.ReviewImageProcInter;
import dev.mvc.reviewImage.ReviewImageVO;
import dev.mvc.tool.Security;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class AdminCont {


  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")  // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.alogin.AloginProc")
  private AloginProcInter aloginProc;
  
  @Autowired
  @Qualifier("dev.mvc.adcontents.AdcontentsProc")
  private AdcontentsProcInter adcontentsProc;
  
  @Autowired
  @Qualifier("dev.mvc.keyword.KeywordProc")
  private KeywordProcInter keywordProc;
  
  @Autowired
  @Qualifier("dev.mvc.review.ReviewProc")
  private ReviewProcInter reviewProc;
  
  @Autowired
  @Qualifier("dev.mvc.reviewImage.ReviewImageProc")
  private ReviewImageProcInter reviewImageProc;
  
  @Autowired
  @Qualifier("dev.mvc.mlogin.MloginProc")
  private MloginProcInter mloginProc;
  
  
  
  
  @Autowired
  private Security security;

  public AdminCont() {
    System.out.println("-> AdminCont created.");
  }





  /**
   * 관리자 목록 조회
   * @param model
   * @param adminVO
   * @return
   */
  @GetMapping(value="/list") // http://localhost:9093/admin/list
  public String list(HttpSession session, Model model) {
    System.out.println("HttpSession ------)_)_))>>" + session);
    ArrayList<AdminVO> list = this.adminProc.list(); // 관리자 회원 목록 실행
    model.addAttribute("list", list); // 회원 목록 반환
    return "admin/list"; // templates/admin/list.html
  }

  /**
   * 조회
   * @param model
   * @param adminsno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/read") // http://localhost:9093/admin/read?adminsno=1
  public String read(HttpSession session, Model model, int adminsno) {
    AdminVO adminVO = this.adminProc.read(adminsno);
    model.addAttribute("adminVO", adminVO);
    return "admin/read"; // templates/admin/read.html
  }

  /**
   * 수정 처리
   * @param model
   * @param adminVO
   * @return
   */
  @PostMapping(value="/update") 
  public String update_proc(Model model, AdminVO adminVO) {
    int cnt = this.adminProc.update(adminVO); // 수정

    if (cnt == 1) {
      model.addAttribute("code", "update_success");
      model.addAttribute("aname", adminVO.getAname());
      model.addAttribute("id", adminVO.getId());
    } else {
      model.addAttribute("code", "update_fail");
    }
    model.addAttribute("cnt", cnt);

    return "admin/msg"; // /templates/admin/msg.html
  }

 


  /**
   * 로그인
   * @param model
   * @param no 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/login")
  public String login_form(Model model, HttpServletRequest request) {
    return "admin/login"; // templates/admin/login.html
  }
  
  /**
   * 로그인 need
   * @param model
   * @param no 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/login_need")
  public String login_need(Model model, HttpServletRequest request) {
    return "admin/login_need"; // templates/admin/login_need.html
  }

  

  /**
   * Cookie 기반 로그인 처리
   * @param session
   * @param request
   * @param response
   * @param model
   * @param id 아이디
   * @param passwd 패스워드
   * @param id_save 아이디 저장 여부
   * @param passwd_save 패스워드 저장 여부
   * @return
   */
  @PostMapping(value="/login")
  public String login_proc(HttpSession session,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           Model model, 
                           String id, 
                           String passwd,
                           AloginVO aloginVO,
                           @RequestParam(value="id_save", defaultValue = "") String id_save,
                           @RequestParam(value="passwd_save", defaultValue = "") String passwd_save) {
    
    //-------------- 주찬 추가 ----------------------//
    // 회원이 로그인 한 상태에서 관리자 로그인할 경우 세션에 두개가 저장됨
    // 세션에 있는 모든 데이터 지움
    //session.invalidate();
    // 그리고 관리자계정을 세션에 등록시킴 - > 즉 세션에 한 계정만 등록시킬 수 있음
    //-------------- 주찬 추가 ----------------------//
    
    
    String ip = request.getRemoteAddr(); // IP
    System.out.println("-> 접속 IP: " + ip);

    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id", id);
    map.put("passwd", this.security.aesEncode(passwd));
    
    int cnt = this.adminProc.login(map);
    System.out.println("-> login_proc cnt: " + cnt);
    
    
    
    
    
    model.addAttribute("cnt", cnt);

    if (cnt == 1) {
     // saveLoginHistory(id, ip);
      
      // id를 이용하여 회원 정보 조회
      AdminVO adminVO = this.adminProc.read_by_id(id);
      session.setAttribute("adminsno", adminVO.getAdminsno());
      session.setAttribute("id", adminVO.getId());
      session.setAttribute("aname", adminVO.getAname());
      session.setAttribute("grade", adminVO.getGrade());
      
      int adminsno = (int)session.getAttribute("adminsno");
      aloginVO.setAdminsno(adminsno); // 세션에서 가져온 adminsno를  로그인 내역 테이블의 aloginVO의 adminsno에 저장
      aloginVO.setIp(ip);
      
      int log = this.aloginProc.alogin_insert(aloginVO); //실행
      System.out.println("로그인 내역" + log);
      
      
      if (adminVO.getGrade() ==0) {
        session.setAttribute("grade", "admin");
      } else if (adminVO.getGrade() ==1) {
        session.setAttribute("grade", "member");
      } else if (adminVO.getGrade() == 2) {
        session.setAttribute("black", "black");
      }else if (adminVO.getGrade() == 3) {
        session.setAttribute("exit", "exit");
      }

      // Cookie 관련 코드
      if (id_save.equals("Y")) {
        Cookie ck_id = new Cookie("ck_id", id);
        ck_id.setPath("/");
        ck_id.setMaxAge(60 * 60 * 24 * 30); // 30 days
        response.addCookie(ck_id);
      } else {
        Cookie ck_id = new Cookie("ck_id", "");
        ck_id.setPath("/");
        ck_id.setMaxAge(0);
        response.addCookie(ck_id);
      }

      Cookie ck_id_save = new Cookie("ck_id_save", id_save);
      ck_id_save.setPath("/");
      ck_id_save.setMaxAge(60 * 60 * 24 * 30); // 30 days
      response.addCookie(ck_id_save);

      if (passwd_save.equals("Y")) {
        Cookie ck_passwd = new Cookie("ck_passwd", passwd);
        ck_passwd.setPath("/");
        ck_passwd.setMaxAge(60 * 60 * 24 * 30); // 30 days
        response.addCookie(ck_passwd);
      } else {
        Cookie ck_passwd = new Cookie("ck_passwd", "");
        ck_passwd.setPath("/");
        ck_passwd.setMaxAge(0);
        response.addCookie(ck_passwd);
      }

      Cookie ck_passwd_save = new Cookie("ck_passwd_save", passwd_save);
      ck_passwd_save.setPath("/");
      ck_passwd_save.setMaxAge(60 * 60 * 24 * 30); // 30 days
      response.addCookie(ck_passwd_save);

      return "redirect:/admin/admin_form";
    } else {
      model.addAttribute("code", "login_fail");
      return "admin/msg";
    }
  }


/* =============================== 주찬 코드 =================================== */
/* ========================================================================== */
/* ========================================================================== */
  /**
   * 관리자 메인 페이지
   * @param model
   * @param session
   * @return
   */
  @GetMapping(value="/admin_form")
  public String admin_form(Model model,HttpSession session) {
    
    if (this.adminProc.isAdmin(session)) { // 로그인이 되어있으면
      int adminsno = (int)session.getAttribute("adminsno"); // 세션에서 memberno를 꺼내옴
      
      // 신규 등록자 수
      int new_user = this.memberProc.new_user_count();
      // 일반회원 전체 수 
      int user_cnt = this.memberProc.user_count_normal();
      
      AdcontentsVO adcontentsVO = this.adcontentsProc.list_ones();
      
      model.addAttribute("new_user", new_user);
      model.addAttribute("user_cnt", user_cnt);
      model.addAttribute("adcontentsVO", adcontentsVO);
      
      return "admin_index";
      
    } else { // 로그인이 안되어있으면
      return "admin/login_need";
    } 
    
  }
  
  /**
   * 메뉴 페이지
   * @param model
   * @return
   */
  @GetMapping(value = "/menu")
  public String admin_menu(Model model, HttpSession session) {
    if (this.adminProc.isAdmin(session)) { // 로그인이 되어있으면
      int adminsno = (int) session.getAttribute("adminsno"); // 세션에서 memberno를 꺼내옴

      
      
      return "admin/menu";
    } else { // 로그인이 안되어있으면
      return "admin/login_need";
    }
  }
  
  /**
   * 리뷰 관리 모니터링
   * @param model
   * @param session
   * @return
   */
  @GetMapping(value = "/review_management")
  public String review_management(Model model, HttpSession session) {
    if (this.adminProc.isAdmin(session)) { // 로그인이 되어있으면
      int adminsno = (int) session.getAttribute("adminsno"); // 세션에서 memberno를 꺼내옴

      ArrayList<KeywordVO> word_list = this.keywordProc.keyword_and_count();
      model.addAttribute("word_list", word_list);
      
      return "admin/review_management";
    } else { // 로그인이 안되어있으면
      return "admin/login_need";
    }
  }
  
  
  @GetMapping(value="/review_admin_list/warm")
  public String review_admin_list_warm(Model model, HttpSession session, String keywordname) {
    System.out.println("keywordname-->" + keywordname);
    if (this.adminProc.isAdmin(session)) { // 로그인이 되어있으면
      int adminsno = (int) session.getAttribute("adminsno"); // 세션에서 memberno를 꺼내옴
      
      ArrayList<ReviewVO> list = this.reviewProc.admin_read_review(keywordname);
      model.addAttribute("list", list);
      model.addAttribute("keywordname", keywordname);
      
      return "admin/review_admin_list_warm";
    } else { // 로그인이 안되어있으면
      return "admin/login_need";
    }
  }
  
  @GetMapping(value="/review_admin_list/cold")
  public String review_admin_list_cold(Model model, HttpSession session, String keywordname) {
    System.out.println("keywordname-->" + keywordname);
    if (this.adminProc.isAdmin(session)) { // 로그인이 되어있으면
      int adminsno = (int) session.getAttribute("adminsno"); // 세션에서 memberno를 꺼내옴
      
      ArrayList<ReviewVO> lists = this.reviewProc.admin_read_review_cold(keywordname);
      model.addAttribute("lists", lists);
      model.addAttribute("keywordname", keywordname);
      
      return "admin/review_admin_list_cold";
    } else { // 로그인이 안되어있으면
      return "admin/login_need";
    }
  }
  
  @GetMapping(value="/review_detail_reads")
  public String review_detail_reads(Model model, HttpSession session, int reviewno) {
      System.out.println("reviewno-->d" + reviewno);
    if (this.adminProc.isAdmin(session)) { // 로그인이 되어있으면
      int adminsno = (int) session.getAttribute("adminsno"); // 세션에서 memberno를 꺼내옴
    
      ReviewVO reviewVO = this.reviewProc.admin_review_detail(reviewno);
      ArrayList<KeywordVO> keywordlist = this.keywordProc.keyword_all_read(reviewno);
      String contents = reviewVO.getContents(); 
      model.addAttribute("reviewVO", reviewVO);
      if (reviewVO.getTemperater() == 2) {
        
        model.addAttribute("contents", highlightKeywords(contents, keywordlist));
        
      } else if(reviewVO.getTemperater() == 1) {
        
        model.addAttribute("contents", highlightKeywords(contents, keywordlist));
        
      }
      
      
      ArrayList<ReviewImageVO> review_image = this.reviewImageProc.read_image(reviewno);
      model.addAttribute("review_image", review_image);
      
    return "admin/review_detail_reads";
    } else { // 로그인이 안되어있으면
      return "admin/login_need";
    }
  }
  
  
  
  
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
/* =============================== 주찬 코드 여기까지 =================================== */
/* ========================================================================== */
/* ========================================================================== */
  
  /**
   * 로그아웃
   * @param model
   * @param no 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/logout")
  public String logout(HttpSession session, Model model) {
    session.invalidate(); // 모든 세션 변수 삭제
    return "redirect:/";
  }
}

