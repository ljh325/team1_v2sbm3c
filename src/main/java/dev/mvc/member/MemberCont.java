package dev.mvc.member;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

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

import dev.mvc.cate.CateVOMenu;
import dev.mvc.contents.Contents;
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
    obj.put("cnts", cnt);
    
    return obj.toString();
  }
  /***************************************************************************************/
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
  
  
  /***************************************************************************************/
  /**
   * 회원정보 조회
   * @param model
   * @param memberno 회원 번호
   * @return 회원 정보
   */
  @GetMapping(value="/read") // http://localhost:9093/member/raed
  public String read(HttpSession session, Model model) {
    
    int memberno = (int)session.getAttribute("memberno"); // session에서 memberno가져오기
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
  public String list(HttpSession session, Model model) {
    

      System.out.println("HttpSession ------)_)_))>>" + session);
      ArrayList<MemberVO> list = this.memberProc.list(); //관리자가 회원 목록 실행
      
      model.addAttribute("list", list); // 회원목록들 반환
      
      return "member/list";  // templates/member/list.html      
// 조회 
//    @GetMapping(value="/list")
//    public String list(HttpSession session, Model model) {
//      if (this.memberProc.isMemberAdmin(session)) {
  //
//        System.out.println("HttpSession ------)_)_))>>" + session);
//        ArrayList<MemberVO> list = this.memberProc.list();
//        
//        model.addAttribute("list", list);
//        
//        return "member/list";  // templates/member/list.html      
//      } else {
//        //return "redirect:/member/login_form_need";  // redirect
//        return "redirect:/member/list";
//      }  
  //
//    }
  }
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
      // grade 범위 (1 ~ 3)
      // 일반회원  1  == member
      // 정지회원  2  == black
      // 탈퇴회원  3  == exit
      if (memberVO.getGrade() == 1 ) {
        session.setAttribute("grade", "member");  
      } else if (memberVO.getGrade() == 2 ) {
        session.setAttribute("grade", "black");
      } else if (memberVO.getGrade() == 3) {
        session.setAttribute("grade", "exit");
      }
      return "redirect:/";  // 로그인이 성공하면 Home으로 리다이렉트
    } else { //   { 로그인 실패 0 }
      model.addAttribute("code", "login_fail"); // 로그인 실패시 실패코드
      return "member/msg"; // templates/member/msg.html로 이동  
    }
  
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

    String upDir =  MemberProfile.getUploadDir(); // 파일을 업로드할 폴더 준비
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
      return "member/msg"; // /templates/member/msg.html
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
