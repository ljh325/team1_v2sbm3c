package dev.mvc.member;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import dev.mvc.mlogin.MloginProcInter;
import dev.mvc.mlogin.MloginVO;
import dev.mvc.tool.Security;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/member")
@Controller
public class MemberCont {
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")  // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  
  @Autowired
  @Qualifier("dev.mvc.mlogin.MloginProc")  // @Service("dev.mvc.mlogin.MloginProc")
  private MloginProcInter mloginProc;
  
  
  @Autowired
  // dev.mvc.tool.Tool.java
  // dev.mvc.team1_v2sbm3c.SecurityConfig.java
  Security security; 
  
  
  public MemberCont() {
    System.out.println("-> MemberCont created.");  
  }
  /***************************************************************************************/
  @GetMapping(value="/checkID") // http://localhost:9093/member/checkID?id=admin
  @ResponseBody
  public String checkID(String id) {
    System.out.println("-> id: " + id);
    int cnt = this.memberProc.checkID(id);
    
    // return "cnt: " + cnt;
    // return "{\"cnt\": " + cnt + "}";    // {"cnt": 1} JSON
    
    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);
    
    return obj.toString();
  }
  /***************************************************************************************/
  /**
   * 닉네임 중복 확인
   * @param nickname
   * @return
   */
  @GetMapping(value="/nickCheck") // http://localhost:9093/member/checkID?id=admin
  @ResponseBody
  public String nickCheck(String nickname) {
    System.out.println("-> nickname: " + nickname);
    int cnt = this.memberProc.nickCheck(nickname);
    System.out.println("-> cnt: " + cnt);
    // return "cnt: " + cnt;
    // return "{\"cnt\": " + cnt + "}";    // {"cnt": 1} JSON
    
    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);
    
    return obj.toString();
  }
  /***************************************************************************************/
  @PostMapping(value="profile_nickIntro")
  @ResponseBody
  public String profile_nickIntro(Model model, HttpSession session,@RequestBody MemberVO memberVO) {
    
    JSONObject obj = new JSONObject();
    int memberno = (int)session.getAttribute("memberno"); 
    System.out.println("profile_nickIntro");
    System.out.println("---------------------------------------------------------");
    System.out.println("소개글" + memberVO.getIntroduce());
    System.out.println("닉네임" + memberVO.getNickname());
    System.out.println("---------------------------------------------------------");
    memberVO.setMemberno(memberno);
    
    
    int cnt = this.memberProc.profile_nickIntro(memberVO);
    obj.put("cnt", cnt);
    
    
    return obj.toString();
  }
  /***************************************************************************************/
  @PostMapping(value="/profile_update_proc")
  public String profile_update_proc(Model model, MemberVO memberVO,
                                    HttpSession session,
                                    RedirectAttributes ra) {
    
    System.out.println("profile_update_proc");

    
    int memberno = (int)session.getAttribute("memberno"); 
    memberVO.setMemberno(memberno);
    
    // ------------------------------------------------------------------------------
    // 파일 전송 코드 시작
    // ------------------------------------------------------------------------------
    String profile = "";          // 원본 파일명 image
    String profilesaved = "";   // 저장된 파일명, image
    String thumbs = "";     // preview image

    String upDir =  Member.getUploadDir(); // 파일을 업로드할 폴더 준비
    System.out.println("-> upDir: " + upDir);
    
    // 전송 파일이 없어도 files1MF 객체가 생성됨.
    // <input type='file' class="form-control" name='files1MF' id='files1MF' 
    //           value='' placeholder="파일 선택">
    MultipartFile mf = memberVO.getFiles1MF();
    
    profile = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
    System.out.println("-> 원본 파일명 산출 file1: " + profile);
    
    long sizes = mf.getSize();  // 파일 크기
    if (sizes > 0) { // 파일 크기 체크, 파일을 올리는 경우
      if (Tool.checkUploadFile(profile) == true) { // 업로드 가능한 파일인지 검사
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
        profilesaved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(profilesaved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
          thumbs = Tool.preview(upDir, profilesaved, 200, 150); 
        }
        
        memberVO.setProfile(profile);   // 순수 원본 파일명
        memberVO.setProfilesaved(profilesaved); // 저장된 파일명(파일명 중복 처리)
        memberVO.setThumbs(thumbs);      // 원본이미지 축소판
        memberVO.setSizes(sizes);  // 파일 크기
        

        
        int cnt = this.memberProc.profile_update_proc(memberVO);
        model.addAttribute("cnt" , cnt);
        
        if (cnt == 0) {
          return "redirect:/member/profile_update?memberno=" + memberno;
        }
        return "redirect:/member/profile?memberno=" + memberno;
        
        
        
      } else { // 전송 못하는 파일 형식
        ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
        ra.addFlashAttribute("cnt", 0); // 업로드 실패
        ra.addFlashAttribute("url", "/member/msg"); // msg.html, redirect parameter 적용
         // Post -> Get - param...
      }
    } else { // 글만 등록하는 경우
      System.out.println("-> 글만 등록");
    }
    
    // ------------------------------------------------------------------------------
    // 파일 전송 코드 종료
    // ------------------------------------------------------------------------------
    
    
    return "redirect:/member/profile?memberno=" + memberno;
  }
  
  /***************************************************************************************/
  
  /**
   * 아이디 찾기 폼
   * @param model
   * @return
   */
  @GetMapping(value="/find_id_form")
  public String find_id_form(Model model) {
       
    return "member/find_id";
  }
  
  /**
   * 아이디 찾기 Proc
   * @param model
   * @return
   */
  @PostMapping(value="/find_id")
  public String find_id(Model model, MemberVO memberVO) {
    

    ArrayList<MemberVO> memberList = this.memberProc.find_id(memberVO);
    System.out.println("memberList->" + memberList);
    
    
    if (memberList != null && !memberList.isEmpty()) {
        model.addAttribute("code", "find_id_success");
        model.addAttribute("memberList", memberList);
        model.addAttribute("log", 1);
    } else {
        model.addAttribute("code", "find_id_fail");
        model.addAttribute("cnt", 0);
        model.addAttribute("log", 1);
    }
    


    return "member/msg";
  }
  
  /**
   * 비밀번호 찾기 폼
   * @param model
   * @return
   */
  @GetMapping(value="/find_passwd_form")
  public String find_passwd_form(Model model) {
    
    return "member/find_passwd";
  }
  
  /**
   * 비밀번호 찾기
   * @param model
   * @param id
   * @param mname
   * @param tel
   * @return
   */
  @PostMapping(value="/find_passwd")
  public String find_passwd(Model model, MemberVO memberVO) {
      // MemberVO 객체에서 비밀번호를 찾습니다.
      MemberVO member = this.memberProc.find_passwd(memberVO);

      if (member != null && member.getPasswd() != null && !member.getPasswd().isEmpty()) {
          try {
              // member 객체에서 암호화된 비밀번호를 가져와서 복호화합니다.
              String decryptedPasswd = security.aesDecode(member.getPasswd());
              model.addAttribute("passwd", decryptedPasswd); // 복호화된 비밀번호 추가
              model.addAttribute("code", "find_passwd_success");
              model.addAttribute("log", 1);
          } catch (Exception e) {
              e.printStackTrace();
              model.addAttribute("code", "find_passwd_fail");
              model.addAttribute("cnt", 0);
              model.addAttribute("log", 1);
          }
      } else {
          model.addAttribute("code", "find_passwd_fail");
          model.addAttribute("cnt", 0);
          model.addAttribute("log", 1);
      }

      return "member/msg";
  }
  /***************************************************************************************/
  /**
   * 회원 가입 폼
   * @param model
   * @param memberVO
   * @return
   */
  @GetMapping(value="/create") // http://localhost:9093/member/create
  public String create_form(Model model, MemberVO memberVO) {
    return "member/create";    // /template/member/create.html
  }
  /**
   * 회원 가입 Proc
   * @param model
   * @param memberVO
   * @return
   */
  @PostMapping(value="/create")
  public String create_proc(Model model, MemberVO memberVO) {
    
    int checkID_cnt = this.memberProc.checkID(memberVO.getId());
    
    if (checkID_cnt == 0) { // 디비에서 id를 조회 후 없으면
      memberVO.setGrade(1);  // memberVO의 grade에 1 (일반회원) 저장
      // 일반회원  1
      // 정지회원  2
      // 탈퇴회원  3
      int cnt = this.memberProc.create(memberVO); //회원가입 실행 성공: 1, 실패: 0
      
      if (cnt == 1) { // 회원가입 성공하면
        model.addAttribute("code", "create_success");
        model.addAttribute("mname", memberVO.getMname());
        model.addAttribute("id", memberVO.getId());
      } else { // 실패하면
        model.addAttribute("code", "create_fail");
      }
      
      model.addAttribute("cnt", cnt);
    } else { // id 가 디비에 존재(id중복)
      model.addAttribute("code", "duplicte_fail");
      model.addAttribute("cnt", 0);
    }
    
    return "member/msg"; // /templates/member/msg.html 일단 메인화면으
  }
  /***************************************************************************************/
  

  
  
  
  
  @GetMapping(value="/profile")
  public String profile(Model model, HttpSession session, int memberno) {
    
    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면 = 세션에 값이 있으면
      //int memberno = (int)session.getAttribute("memberno"); 
      MemberVO memberVO = this.memberProc.read(memberno);
      model.addAttribute("memberVO", memberVO);
    
      return "member/profile"; // /mh/list_all.html
    }else{
      
    return "member/login"; // /mh/list_all.html
    }
  }
  /***************************************************************************************/

  @GetMapping(value="/profile_update")
  public String profile_update(Model model, HttpSession session) {
    
    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면 = 세션에 값이 있으면
      int memberno = (int)session.getAttribute("memberno"); 
      MemberVO memberVO = this.memberProc.read(memberno);
      model.addAttribute("memberVO", memberVO);
    
      return "member/profile_update"; // /mh/list_all.html
    }else{
      
    return "member/login"; // /mh/list_all.html
    }
  }
  /***************************************************************************************/
  /**
   * 회원정보 조회
   * @param model
   * @param memberno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/read") // http://localhost:9093/member/raed
  public String read(int memberno, Model model) {
    
    //int memberno = (int)session.getAttribute("memberno"); // session에서 memberno가져오기
    //String grade = (String)session.getAttribute("grade"); // 등급: 일반회원 1, 정지회원 2, 탈퇴회원 3

    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO);
    
    

//    // 사용자: member && 11 ~ 20
//    // if (grade.equals("member") && (gradeno >= 11 && gradeno <= 20) && memberno == (int)session.getAttribute("memberno")) {
//    if (grade.equals("member") &&  memberno == (int)session.getAttribute("memberno")) {
//      // System.out.println("-> read memberno: " + memberno);
//      
//      MemberVO memberVO = this.memberProc.read(memberno);
//      model.addAttribute("memberVO", memberVO);
//      
//      return "member/read";  // templates/member/read.html
//      
//    } else if (grade.equals("admin")) {
//      // System.out.println("-> read memberno: " + memberno);
//      
//      MemberVO memberVO = this.memberProc.read(memberno);
//      model.addAttribute("memberVO", memberVO);
//      
//      return "member/read";  // templates/member/read.html
//    } else {
//      return "redirect:/member/login_form_need";  // redirect
//    }
    
    
    return "member/read";  // templates/member/read.html    
  }
  /***************************************************************************************/
  
  
  
  
  /***************************************************************************************/
  /**
   * 관리자 전용 회원목록 조회
   * @param model
   * @param memberVO
   * @return
   */
  @GetMapping(value="/list") // http://localhost:9093/member/list
  public String list(HttpSession session, Model model,
      @RequestParam(name = "word", defaultValue = "") String word,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page) {
      
      word = Tool.checkNull(word).trim(); // 검색 단어에 공백 제거
      
      // 등급을 셀렉트 검색할때
      if (word.contains("__")) {
        
        //문자열을 "_"를 기준으로 나눔
        String[] parts = word.split("__");
        word = parts[1];  // 예):  word 값 ==> grade__3  word[0] = "grade", word[1] = "3"
        HashMap<String, Object> map = new HashMap<>();
        map.put("word", word); // 분리한 word값을 map에 저장
        map.put("now_page", now_page); 
        
        ArrayList<MemberVO> list = this.memberProc.grade_list_by_search_paging(map); // 페이징, 검색리스트 회원목록
        model.addAttribute("list", list); // 페이징, 검색리스트 회원목록들 반환
        
        // 검색 목록 갯수
        int search_count = this.memberProc.grade_list_by_search_count(map);
        
        // 페이징 
        String paging = this.memberProc.pagingBox(now_page, word, "/member/list", 
            search_count, Member.RECORD_PER_PAGE, Member.PAGE_PER_BLOCK);
        
        model.addAttribute("paging", paging);
        model.addAttribute("now_page", now_page);
        model.addAttribute("search_count", search_count);
        
        int no = search_count - ((now_page - 1) * Member.RECORD_PER_PAGE);
        model.addAttribute("no", no);
        
      } //  등급 코드
      else { // 이름 및 아이디 검색 코드
        HashMap<String, Object> map = new HashMap<>();
        map.put("word", word); // 입력한 검색어를 map에 저장
        map.put("now_page", now_page); // 
        model.addAttribute("word", word); // word 검색어 다시 반환
        
        // 회원목록 리스트
        ArrayList<MemberVO> list = this.memberProc.list_by_search_paging(map); // 페이징, 검색리스트 회원목록
        model.addAttribute("list", list); // 페이징, 검색리스트 회원목록들 반환
        
        
        // 검색 목록 갯수
        int search_count = this.memberProc.list_by_search_count(map);
        
        // 페이징 
        String paging = this.memberProc.pagingBox(now_page, word, "/member/list", 
            search_count, Member.RECORD_PER_PAGE, Member.PAGE_PER_BLOCK);
        
        model.addAttribute("paging", paging);
        model.addAttribute("now_page", now_page);
        model.addAttribute("search_count", search_count);
        
        int no = search_count - ((now_page - 1) * Member.RECORD_PER_PAGE);
        model.addAttribute("no", no);
        //System.out.println("HttpSession ------)_)_))>>" + session);
        //ArrayList<MemberVO> list = this.memberProc.list(); //관리자가 회원 목록 실행
      } // 검색 코드 종료
      

      return "member/list";  // templates/member/list.html      

      
      
      
      
      
      
  }
  /***************************************************************************************/
  
  
  
  /***************************************************************************************/
  /**
   * 관리자 회원 관리폼
   * @param model
   * @param memberno
   * @return
   */
  @GetMapping(value="admin_read_form") // http://localhost:9093/member/admin_read_form?memberno=??
  public String admin_read_form(Model model, int memberno) {
    
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO);
    
    return "member/admin_read";//  /templates/member/admin_read.html
  }
  
  
  @PostMapping(value="admin_update")
  public String admin_read(Model model, MemberVO memberVO, RedirectAttributes ra) {

    int cnt = this.memberProc.grade_update(memberVO);
    ra.addAttribute("cnt", cnt);

    if (cnt == 1) {
      System.out.println("성공");
    } else {
      System.out.println("실패");
    }
    ra.addAttribute("memberno", memberVO.getMemberno());
    return "redirect:/member/admin_read_form"; //templates/member/admin_read.html
  }
  
//  @PostMapping(value="admin_update")
//  @ResponseBody
//  public String admin_read(Model model, MemberVO memberVO) {
//    
//    
//    int cnt = this.memberProc.grade_update(memberVO);
//    model.addAttribute("cnt", cnt);
//    System.out.println("id-->" + memberVO.getId());
//    System.out.println("grade--->" + memberVO.getGrade());
//    JSONObject obj = new JSONObject();
//    obj.put("cnt", cnt);
//
//    
//    return obj.toString();
//  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
  

  /**
   * 로그인
   * @param model
   * @param request 
   * @return 회원 정보
   */
  @GetMapping(value="/login") // http://localhost:9093/member/login
  public String login_form(Model model, HttpServletRequest request) {
  

    // Cookie 관련 코드---------------------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;
  
    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크
  
    if (cookies != null) { // 쿠키가 존재한다면
      for (int i=0; i < cookies.length; i++){
        cookie = cookies[i]; // 쿠키 객체 추출
      
        if (cookie.getName().equals("ck_id")){
          ck_id = cookie.getValue();  // email
        }else if(cookie.getName().equals("ck_id_save")){
          ck_id_save = cookie.getValue();  // Y, N
        }else if (cookie.getName().equals("ck_passwd")){
          ck_passwd = cookie.getValue();         // 1234
        }else if(cookie.getName().equals("ck_passwd_save")){
          ck_passwd_save = cookie.getValue();  // Y, N
        }
      }
    }
    model.addAttribute("ck_id", ck_id);
    
    //    <input type='checkbox' name='id_save' value='Y' 
    //            th:checked="${ck_id_save == 'Y'}"> 저장
    model.addAttribute("ck_id_save", ck_id_save);
  
    model.addAttribute("ck_passwd", ck_passwd);
    model.addAttribute("ck_passwd_save", ck_passwd_save);
    // Cookie 관련 코드 끝----------------------------------------------------------------------------
    
    
    
    return "member/login";  // templates/member/login.html
  }
  /**
   * 로그인 처리
   * @param session
   * @param request
   * @param response
   * @param model
   * @param id
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
                                     MloginVO mloginVO,
                                     String id, 
                                     String passwd,
                                     @RequestParam(value="id_save", defaultValue = "") String id_save,
                                     @RequestParam(value="passwd_save", defaultValue = "") String passwd_save
                                     ) {
    
    String ip = request.getRemoteAddr(); // ip 조회
    System.out.println("-> 접속 IP: " + ip);
    

    
    
    
    
    
    
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id", id); // map에 id 저장
    map.put("passwd", this.security.aesEncode(passwd)); // map에 암호화된 패스워드 저장
    int cnt = this.memberProc.login(map); // mybatis 로그인 처리
    model.addAttribute("cnt", cnt); // 결과값 반환 1: 로그인성공, 0: 실패
    
    if (cnt == 1) { // { 로그인 성공 1 }
      // id를 이용하여 회원 정보 조회
      MemberVO memberVO = this.memberProc.readById(id); // id로 회원정보를 찾음
      session.setAttribute("memberno", memberVO.getMemberno()); // 세션에 memberno 저장
      session.setAttribute("id", memberVO.getId()); //세션에 id 저장
      session.setAttribute("mname", memberVO.getMname()); //세션에 mname 저장
      session.setAttribute("grade", memberVO.getGrade()); //세션에 grade 저장
      
      
      int memberno = (int)session.getAttribute("memberno"); // 로그인 성공시 세션에서 memberno를 가져옴
      mloginVO.setMemberno(memberno); // 세션에서 가져온 memberno를  로그인 내역 테이블의 mloginVO의 memberno에 저장
      mloginVO.setIp(ip);
      
      int cnts = this.mloginProc.mlogin_insert(mloginVO);
      model.addAttribute("cnts", cnts);
      // grade 범위 (0 ~ 3)
      // 관리자     0  == admin
      // 일반회원  1  == member
      // 정지회원  2  == black
      // 탈퇴회원  3  == exit
      if (memberVO.getGrade() == 1 ) {
          session.setAttribute("grade", "member");  
        } else if (memberVO.getGrade() == 2 ) {
          session.setAttribute("grade", "black");
        } else if (memberVO.getGrade() == 3) {
          session.setAttribute("grade", "exit");
        }  else if (memberVO.getGrade() == 0) {
            session.setAttribute("grade", "admin");
        }

      
      
      
      // Cookie 관련 코드---------------------------------------------------------
      // -------------------------------------------------------------------
      // id 관련 쿠기 저장
      // -------------------------------------------------------------------
      if (id_save.equals("Y")) { // id를 저장할 경우, Checkbox를 체크한 경우
        Cookie ck_id = new Cookie("ck_id", id);
        ck_id.setPath("/");  // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
        ck_id.setMaxAge(60 * 60 * 24 * 30); // 30 day, 초단위
        response.addCookie(ck_id); // id 저장
      } else { // N, id를 저장하지 않는 경우, Checkbox를 체크 해제한 경우
        Cookie ck_id = new Cookie("ck_id", "");
        ck_id.setPath("/");
        ck_id.setMaxAge(0);
        response.addCookie(ck_id); // id 저장
      }
      
      // id를 저장할지 선택하는  CheckBox 체크 여부
      Cookie ck_id_save = new Cookie("ck_id_save", id_save);
      ck_id_save.setPath("/");
      ck_id_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
      response.addCookie(ck_id_save);
      System.out.println("ck_id_save" + ck_id_save);
      // -------------------------------------------------------------------
  
      // -------------------------------------------------------------------
      // Password 관련 쿠기 저장
      // -------------------------------------------------------------------
      if (passwd_save.equals("Y")) { // 패스워드 저장할 경우
        Cookie ck_passwd = new Cookie("ck_passwd", passwd);
        ck_passwd.setPath("/");
        ck_passwd.setMaxAge(60 * 60 * 24 * 30); // 30 day
        response.addCookie(ck_passwd);
      } else { // N, 패스워드를 저장하지 않을 경우
        Cookie ck_passwd = new Cookie("ck_passwd", "");
        ck_passwd.setPath("/");
        ck_passwd.setMaxAge(0);
        response.addCookie(ck_passwd);
      }
      // passwd를 저장할지 선택하는  CheckBox 체크 여부
      Cookie ck_passwd_save = new Cookie("ck_passwd_save", passwd_save);
      ck_passwd_save.setPath("/");
      ck_passwd_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
      response.addCookie(ck_passwd_save);
      // -------------------------------------------------------------------
      // ----------------------------------------------------------------------------     
  
      
      System.out.println("id_save -->" + id_save );
      System.out.println("ck_id_save -->" + ck_id_save );
      System.out.println("passwd_save -->" + passwd_save );
      System.out.println("ck_passwd_save -->" + ck_passwd_save );
      return "redirect:/";  // 로그인이 성공하면 Home으로 리다이렉트
    } else { //   { 로그인 실패 0 }
      model.addAttribute("code", "login_fail"); // 로그인 실패시 실패코드
      return "member/msg"; // templates/member/msg.html로 이동  
    }
  
  }
  @PostMapping(value="/logins")
  @ResponseBody
  public String login_json(Model model, @RequestBody Map<String, String> payload) {
    String id = payload.get("id");
    String passwd = payload.get("passwd");

    if (id == null || id.trim().isEmpty() || passwd == null || passwd.trim().isEmpty()) {
        JSONObject obj = new JSONObject();
        obj.put("cnt", 0);
        obj.put("error", "ID and Password cannot be empty");
        return obj.toString();
    }

    HashMap<String, Object> map = new HashMap<>();
    map.put("id", id); // map에 id 저장
    map.put("passwd", this.security.aesEncode(passwd)); // map에 암호화된 패스워드 저장

    int cnt = this.memberProc.login(map); // 로그인 확인

    JSONObject obj = new JSONObject();
    obj.put("cnt", cnt);
    
    return obj.toString();
  }
  
  /***************************************************************************************/
  /**
   * 로그아웃
   * @param session
   * @param model
   * @return
   */
  @GetMapping(value="/logout") // http://localhost:9093/member/logout
  public String logout(HttpSession session, Model model) {
    session.invalidate();  // 모든 세션 변수 삭제
    return "redirect:/"; // 홈으로 이동
  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
   * 회원정보 수정폼
   * @param session
   * @param model
   * @return
   */
  @GetMapping(value="/update_form") // http://localhost:9093/member/update_form
  public String update_form(HttpSession session, Model model) {
    int memberno = (int)session.getAttribute("memberno"); // session에서 memberno가져오기
    
    MemberVO memberVO = this.memberProc.read(memberno); // memberno로 회원정보 조회
    
    model.addAttribute("memberVO", memberVO); //  회원정보가 들어있는 memberVO를 폼으로 보냄
    return "member/update_form"; // templates/member/passwd_update_form.html   
  }
  /**
   * 회원정보 수정처리
   * @param model
   * @param memberVO
   * @param request
   * @param ra
   * @return
   */
  @PostMapping(value="/update_proc")
  public String update_proc(Model model 
                            ,MemberVO memberVO
                            ,HttpServletRequest request
                            ,RedirectAttributes ra) {
    // ------------------------------------------------------------------------------
    // 파일 전송 코드 시작
    // ------------------------------------------------------------------------------
    String profile = "";          // 원본 파일명 image
    String profilesaved = "";   // 저장된 파일명, image
    String thumbs = "";     // preview image

    String upDir =  Member.getUploadDir(); // 파일을 업로드할 폴더 준비
    System.out.println("-> upDir: " + upDir);
    
    // 전송 파일이 없어도 files1MF 객체가 생성됨.
    // <input type='file' class="form-control" name='files1MF' id='files1MF' 
    //           value='' placeholder="파일 선택">
    MultipartFile mf = memberVO.getFiles1MF();
    
    profile = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
    System.out.println("-> 원본 파일명 산출 file1: " + profile);
    
    long sizes = mf.getSize();  // 파일 크기
    if (sizes > 0) { // 파일 크기 체크, 파일을 올리는 경우
      if (Tool.checkUploadFile(profile) == true) { // 업로드 가능한 파일인지 검사
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
        profilesaved = Upload.saveFileSpring(mf, upDir); 
        
        if (Tool.isImage(profilesaved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
          thumbs = Tool.preview(upDir, profilesaved, 200, 150); 
        }

        memberVO.setProfile(profile);   // 순수 원본 파일명
        memberVO.setProfilesaved(profilesaved); // 저장된 파일명(파일명 중복 처리)
        memberVO.setThumbs(thumbs);      // 원본이미지 축소판
        memberVO.setSizes(sizes);  // 파일 크기

      } else { // 전송 못하는 파일 형식
        ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
        ra.addFlashAttribute("cnt", 0); // 업로드 실패
        ra.addFlashAttribute("url", "/member/msg"); // msg.html, redirect parameter 적용
         // Post -> Get - param...
      }
    } else { // 글만 등록하는 경우
      System.out.println("-> 글만 등록");
    }
    
    // ------------------------------------------------------------------------------
    // 파일 전송 코드 종료
    // ------------------------------------------------------------------------------
    
    int cnt = this.memberProc.update(memberVO); // 회원 정보 수정 실행  (성공: 1, 실패: 0)|| memberVO에 수정한 값 들어있음
    
    if (cnt == 1) { // 성공: 1 했을 경우
      model.addAttribute("code", "update_success");
      model.addAttribute("mname", memberVO.getMname()); // memberVO에 있는 mname을 꺼내와[getMname()] mname에 저장 후 폼으로 보냄
      model.addAttribute("id", memberVO.getId()); // memberVO에 있는 id을 꺼내와[getId()] id에 저장 후 폼으로 보냄
    } else { // 실패: 0 
      model.addAttribute("code", "update_fail"); // 오류 코드를 code로 폼으로 보냄
    }
    
    model.addAttribute("cnt", cnt); //  성공 실패코드 보냄
    
    return "member/msg"; // /templates/member/msg.html
  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
  /**
   * 삭제
   * @param model
   * @param memberno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/delete_form")  // http://localhost:9093/member/delete_form
  public String delete(Model model, int memberno) {
    
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO); 
    
    return "member/delete";  // templates/member/delete.html
  }
  /**
   * 회원 Delete process
   * @param model
   * @param memberno 삭제할 레코드 번호
   * @return
   */
  @PostMapping(value="/delete_proc")
  public String delete_process(Model model, Integer memberno, HttpSession session) {
    int cnt = this.memberProc.delete(memberno);
    session.invalidate();  // 모든 세션 변수 삭제 (로그아웃)
    
    if (cnt == 1) {
      return "redirect:/"; // 삭제 성공시(1) template/index.html
    } else {
      model.addAttribute("code", "delete_fail");
      return "member/login"; // /templates/member/msg.html
    }
  }
  /***************************************************************************************/
  
  
  /***************************************************************************************/
//  /**
//   * 패스워드 수정 폼
//   * @param model
//   * @param memberno
//   * @return
//   */
//  @GetMapping(value="/passwd_update_form")
//  public String passwd_update_form(HttpSession session, Model model) {
//    int memberno = (int)session.getAttribute("memberno"); // session에서 가져오기
//    
//    MemberVO memberVO = this.memberProc.read(memberno);
//    
//    model.addAttribute("memberVO", memberVO);
//    
//    return "member/passwd_update_form"; // templates/member/passwd_update_form.html   
//  }
//  /**
//   * 
//   * 현재 패스워드 확인 - 기존 패스워드 비교 대상
//   * @param session
//   * @param current_passwd
//   * @return 1: 일치, 0: 불일치
//   * 
//   *   <select id="passwd_check" parameterType="HashMap" resultType="int">
//    SELECT COUNT(memberno) as cnt
//    FROM member
//    WHERE memberno=#{memberno} AND passwd=#{passwd}
//    </select>
//   
//  <!-- (o) 패스워드 변경 -->
//  <update id="passwd_update" parameterType="HashMap">
//    UPDATE member
//    SET passwd=#{passwd}
//    WHERE memberno=#{memberno}
//  </update>
//   */
//  @PostMapping(value="/passwd_check")
//  @ResponseBody
//  public String passwd_check(HttpSession session, @RequestBody String json_src) {
//    System.out.println("-> json_src: " + json_src); // json_src: {"current_passwd":"1234"}
//    JSONObject src = new JSONObject(json_src); // String -> JSON
//    String current_passwd =  (String)src.get("current_passwd"); // 값 가져오기
//    // System.out.println("-> current_passwd: " + current_passwd);
//    
//    try {
//      Thread.sleep(2000);
//    } catch(Exception e) {
//      
//    }
//    
//    int memberno = (int)session.getAttribute("memberno"); // session에서 가져오기
//    HashMap<String, Object> map = new HashMap<String, Object>();
//    map.put("memberno", memberno);
//    // map.put("passwd", new Security().aesEncode(current_passwd));
//    map.put("passwd", this.security.aesEncode(current_passwd));
//    
//    int cnt = this.memberProc.passwd_check(map);
//    
//    JSONObject json = new JSONObject();
//    json.put("cnt", cnt);
//    
//    return json.toString();   
//  }
//  /**
//   * 패스워드 변경
//   * http://localhost:9091/member/passwd_update_proc?current_passwd=00000000000&passwd=7777
//   * @param session
//   * @param model
//   * @param current_passwd 현재 패스워드
//   * @param passwd 새로운 패스워드
//   * @return
//   */
//  @PostMapping(value="/passwd_update_proc")
//  public String passwd_update_proc(HttpSession session, 
//                                                    Model model, 
//                                                    String current_passwd, 
//                                                    String passwd) {
//    
//    if (this.memberProc.isMember(session)) {
//      int memberno = (int)session.getAttribute("memberno"); // session에서 가져오기
//      HashMap<String, Object> map = new HashMap<String, Object>();
//      map.put("memberno", memberno);
//      // map.put("passwd", new Security().aesEncode(current_passwd)); // 현재 패스워드 이름 주의
//      map.put("passwd", this.security.aesEncode(current_passwd)); // 현재 패스워드 이름 주의
//      
//      // System.out.println("-> passwd_update_proc current_passwd: " + current_passwd);
//      // Form 없이 해커가 해킹 했을 경우를 대비하여 다시 패스워드 검사
//      int cnt = this.memberProc.passwd_check(map); 
//      
//      if (cnt == 0) { // 현재 패스워드 불일치
//        model.addAttribute("code", "passwd_not_equal");
//        model.addAttribute("cnt", 0);
//        
//      } else { // 현재 패스워드 일치
//        HashMap<String, Object> map_new_passwd = new HashMap<String, Object>();
//        map_new_passwd.put("memberno", memberno);
//        // map_new_passwd.put("passwd", new Security().aesEncode(passwd)); // 새로운 패스워드
//        map_new_passwd.put("passwd", this.security.aesEncode(passwd)); // 새로운 패스워드
//        // System.out.println("-> passwd_update_proc passwd 변경: " + passwd);
//        
//        int passwd_change_cnt = this.memberProc.passwd_update(map_new_passwd);
//        
//        if (passwd_change_cnt == 1) {
//          model.addAttribute("code", "passwd_change_success");
//          model.addAttribute("cnt", 1);
//        } else {
//          model.addAttribute("code", "passwd_change_fail");
//          model.addAttribute("cnt", 0);
//        }
//      }
//
//      return "member/msg";   // /templates/member/msg.html
//    } else {
//      return "redirect:/member/login_form_need";
//    }
//
//  }
  /***************************************************************************************/
}
