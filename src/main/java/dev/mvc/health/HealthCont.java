package dev.mvc.health;

import java.util.ArrayList;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.admin.AdminProcInter;

import dev.mvc.htc.HtcProcInter;
import dev.mvc.htc.HtcVO;
import dev.mvc.htc.HtcVOMenu;

import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;

@RequestMapping(value = "/health")
@Controller
public class HealthCont {
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") // @Service("dev.mvc.admin.MemberProc")
  private AdminProcInter adminProc;

  @Autowired
  @Qualifier("dev.mvc.htc.HtcProc") // @Component("dev.mvc.htc.HtcProc")
  private HtcProcInter htcProc;

  @Autowired
  @Qualifier("dev.mvc.health.HealthProc") // @Component("dev.mvc.health.HealthProc")
  private HealthProcInter healthProc;

  public HealthCont() {
    System.out.println("-> HealthCont created.");
  }

  /**
   * POST 요청시 새로고침 방지, POST 요청 처리 완료 → redirect → url → GET → forward -> html 데이터
   * 전송
   * 
   * @return
   */
  @GetMapping(value = "/msg")
  public String msg(Model model, String url) {
    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);

    return url; // forward, /templates/...
  }

  // 등록 폼, health 테이블은 FK로 htcno를 사용함.
  // http://localhost:9091/health/create X
  // http://localhost:9091/health/create?htcno=1 // htcno 변수값을 보내는 목적
  // http://localhost:9091/health/create?htcno=2
  // http://localhost:9091/health/create?htcno=5
  @GetMapping(value = "/create")
  public String create(Model model, HealthVO healthVO, int htcno) {
    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);

    HtcVO htcVO = this.htcProc.read(htcno); // create.jsp에 카테고리 정보를 출력하기위한 목적
    model.addAttribute("htcVO", htcVO);

    return "health/create"; // /templates/health/create.html
  }

  /**
   * 등록 처리 http://localhost:9091/health/create
   * 
   * @return
   */
  @PostMapping(value = "/create")
  public String create(HttpServletRequest request, HttpSession session, Model model, HealthVO healthVO,
      RedirectAttributes ra) {

    if (adminProc.isMemberAdmin(session)) { // 관리자로 로그인한경우
      // ------------------------------------------------------------------------------
      // 파일 전송 코드 시작
      // ------------------------------------------------------------------------------
      String file1 = ""; // 원본 파일명 image
      String file1saved = ""; // 저장된 파일명, image
      String thumb1 = ""; // preview image

      String upDir = Health.getUploadDir(); // 파일을 업로드할 폴더 준비
      System.out.println("-> upDir: " + upDir);

      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF'
      // value='' placeholder="파일 선택">
      MultipartFile mf = healthVO.getFile1MF();

      file1 = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
      System.out.println("-> 원본 파일명 산출 file1: " + file1);

      long size1 = mf.getSize(); // 파일 크기
      if (size1 > 0) { // 파일 크기 체크, 파일을 올리는 경우
        if (Tool.checkUploadFile(file1) == true) { // 업로드 가능한 파일인지 검사
          // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg, spring_2.jpg...
          file1saved = Upload.saveFileSpring(mf, upDir);

          if (Tool.isImage(file1saved)) { // 이미지인지 검사
            // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
            thumb1 = Tool.preview(upDir, file1saved, 200, 150);
          }

          healthVO.setFile1(file1); // 순수 원본 파일명
          healthVO.setFile1saved(file1saved); // 저장된 파일명(파일명 중복 처리)
          healthVO.setThumb1(thumb1); // 원본이미지 축소판
          healthVO.setSize1(size1); // 파일 크기

        } else { // 전송 못하는 파일 형식
          ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
          ra.addFlashAttribute("cnt", 0); // 업로드 실패
          ra.addFlashAttribute("url", "/health/msg"); // msg.html, redirect parameter 적용
          return "redirect:/health/msg"; // Post -> Get - param...
        }
      } else { // 글만 등록하는 경우
        System.out.println("-> 글만 등록");
      }

      // ------------------------------------------------------------------------------
      // 파일 전송 코드 종료
      // ------------------------------------------------------------------------------

      // Call By Reference: 메모리 공유, Hashcode 전달
      int adminsno = (int) session.getAttribute("adminsno"); // adminno FK
      healthVO.setAdminsno(adminsno);
      int cnt = this.healthProc.create(healthVO);

     


      if (cnt == 1) {
        

        ra.addAttribute("htcno", healthVO.getHtcno()); // controller -> controller: O
        return "redirect:/health/list_by_htcno";

        // return "redirect:/health/list_by_htcno?htcno=" + healthVO.gethtcno();
        // // /templates/health/list_by_htcno.html
      } else {
        ra.addFlashAttribute("code", "create_fail"); // DBMS 등록 실패
        ra.addFlashAttribute("cnt", 0); // 업로드 실패
        ra.addFlashAttribute("url", "/health/msg"); // msg.html, redirect parameter 적용
        return "redirect:/health/msg"; // Post -> Get - param...
      }
    } else { // 로그인 실패 한 경우
      return "redirect:/admin/login_need"; // /member/login_cookie_need.html
    }
  }

  /**
   * 전체 목록, 관리자만 사용 가능 http://localhost:9091/health/list_all.do
   * 
   * @return
   */
  @GetMapping(value = "/list_all")
  public String list_all(HttpSession session, Model model,
    @RequestParam(name="word", defaultValue="") String word,
    @RequestParam(name="now_page", defaultValue="1") int now_page) {
    // System.out.println("-> list_all");
    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);

    if (this.adminProc.isMemberAdmin(session)) {
      ArrayList<HealthVO> list = this.healthProc.list_all();
      


      model.addAttribute("list", list);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      
      return "health/list_all";// templates/health/list_all.html

    } else {
      return "redirect:/admin/login_need";

    }

  }



  /**
   * 카테고리별 목록 + 검색 + 페이징 http://localhost:9091/health/list_by_htcno?htcno=5
   * http://localhost:9091/health/list_by_htcno?htcno=6
   * 
   * @return
   */
  @GetMapping(value = "/list_by_htcno")
  public String list_by_htcno_search_paging(HttpSession session, Model model, int htcno,
      @RequestParam(name = "word", defaultValue = "") String word,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page) {

    // System.out.println("-> htcno: " + htcno);

    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);

    HtcVO htcVO = this.htcProc.read(htcno);
    model.addAttribute("htcVO", htcVO);

    word = Tool.checkNull(word).trim();

    HashMap<String, Object> map = new HashMap<>();
    map.put("htcno", htcno);
    map.put("word", word);
    map.put("now_page", now_page);

    ArrayList<HealthVO> list = this.healthProc.list_by_htcno_search_paging(map);
    model.addAttribute("list", list);

    // System.out.println("-> size: " + list.size());
    model.addAttribute("word", word);

    int search_count = this.healthProc.list_by_htcno_search_count(map);
    String paging = this.healthProc.pagingBox(htcno, now_page, word, "/health/list_by_htcno", search_count,
        Health.RECORD_PER_PAGE, Health.PAGE_PER_BLOCK);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page); //controler->html

    model.addAttribute("search_count", search_count);

    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * Health.RECORD_PER_PAGE);
    model.addAttribute("no", no);

    return "health/list_by_htcno_search_paging"; // /templates/health/list_by_htcno_search_paging.html
  }

  /**
   * 카테고리별 목록 + 검색 + 페이징 + Grid
   * http://localhost:9091/health/list_by_htcno?htcno=5
   * http://localhost:9091/health/list_by_htcno?htcno=6
   * 
   * @return
   */
  @GetMapping(value = "/list_by_htcno_grid")
  public String list_by_htcno_search_paging_grid(HttpSession session, Model model, int htcno,
      @RequestParam(name = "word", defaultValue = "") String word,
      @RequestParam(name = "now_page", defaultValue = "1") int now_page) {

    // System.out.println("-> htcno: " + htcno);

    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);

    HtcVO htcVO = this.htcProc.read(htcno);
    model.addAttribute("htcVO", htcVO);

    word = Tool.checkNull(word).trim();

    HashMap<String, Object> map = new HashMap<>();
    map.put("htcno", htcno);
    map.put("word", word);
    map.put("now_page", now_page);

    ArrayList<HealthVO> list = this.healthProc.list_by_htcno_search_paging(map);
    model.addAttribute("list", list);

    // System.out.println("-> size: " + list.size());
    model.addAttribute("word", word);

    int search_count = this.healthProc.list_by_htcno_search_count(map);
    String paging = this.healthProc.pagingBox(htcno, now_page, word, "/health/list_by_htcno", search_count,
        Health.RECORD_PER_PAGE, Health.PAGE_PER_BLOCK);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);

    model.addAttribute("search_count", search_count);

    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * Health.RECORD_PER_PAGE);
    model.addAttribute("no", no);

    // /templates/health/list_by_htcno_search_paging_grid.html
    return "health/list_by_htcno_search_paging_grid";
  }

  /**
   * 조회 http://localhost:9091/health/read?healthno=17
   * 
   * @return
   */
  @GetMapping(value = "/read")
  public String read(Model model, int healthno, String word, int now_page) { // int htcno =
                                                                               // (int)request.getParameter("htcno");
    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);
    
    ArrayList<HtcVOMenu> menu1 = this.htcProc.menu();
    model.addAttribute("menu", menu1);
    
    HealthVO healthVO = this.healthProc.read(healthno);
    long size1 = healthVO.getSize1();
    String size1_label = Tool.unit(size1);
    healthVO.setSize1_label(size1_label);

    model.addAttribute("healthVO", healthVO);   
    HtcVO htcVO = this.htcProc.read(healthVO.getHtcno());
    model.addAttribute("htcVO", htcVO);


    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    return "health/read";
  }

  /**
   * 맵 등록/수정/삭제 폼 http://localhost:9091/health/map?healthno=1
   * 
   * @return
   */
  @GetMapping(value = "/map")
  public String map(Model model, int healthno) {
    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);

    HealthVO healthVO = this.healthProc.read(healthno); // map 정보 읽어 오기
    model.addAttribute("healthVO", healthVO); // request.setAttribute("healthVO", healthVO);

    HtcVO htcVO = this.htcProc.read(healthVO.getHtcno()); // 그룹 정보 읽기
    model.addAttribute("htcVO", htcVO);

    return "health/map";
  }

  /**
   * MAP 등록/수정/삭제 처리 http://localhost:9091/health/map
   * 
   * @param healthVO
   * @return
   */
  @PostMapping(value = "/map")
  public String map_update(Model model, int healthno, String map) {
    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("healthno", healthno);
    hashMap.put("map", map);

    this.healthProc.map(hashMap);

    return "redirect:/health/read?healthno=" + healthno;
  }

  /**
   * Youtube 등록/수정/삭제 폼 http://localhost:9091/health/youtube?healthno=1
   * 
   * @return
   */
  @GetMapping(value = "/youtube")
  public String youtube(Model model, int healthno, String word, int now_page) {
    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);

    HealthVO healthVO = this.healthProc.read(healthno); // map 정보 읽어 오기
    model.addAttribute("healthVO", healthVO); // request.setAttribute("healthVO", healthVO);

    HtcVO htcVO = this.htcProc.read(healthVO.getHtcno()); // 그룹 정보 읽기
    model.addAttribute("htcVO", htcVO);

    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    
    return "health/youtube";  // forward
  }

  /**
   * Youtube 등록/수정/삭제 처리 http://localhost:9091/health/youtube
   * 
   * @param healthVO
   * @return
   */
  @PostMapping(value = "/youtube")
  public String youtube_update(Model model, 
                                             RedirectAttributes ra,
                                             int healthno, 
                                             String youtube, 
                                             String word, 
                                             int now_page) {

    if (youtube.trim().length() > 0) { // 삭제 중인지 확인, 삭제가 아니면 youtube 크기 변경
      youtube = Tool.youtubeResize(youtube, 640); // youtube 영상의 크기를 width 기준 640 px로 변경
    }

    HashMap<String, Object> hashMap = new HashMap<String, Object>();
    hashMap.put("healthno", healthno);
    hashMap.put("youtube", youtube);

    this.healthProc.youtube(hashMap);
    
    ra.addAttribute("healthno", healthno);
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    // return "redirect:/health/read?healthno=" + healthno;
    return "redirect:/health/read";
  }

  /**
   * 수정 폼 http:// localhost:9091/health/update_text?healthno=1
   *
   */
  @GetMapping(value = "/update_text")
  public String update_text(HttpSession session, Model model, int healthno, RedirectAttributes ra, String word,
      int now_page) {
    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);

    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);

    if (this.adminProc.isMemberAdmin(session)) { // 관리자로 로그인한경우
      HealthVO healthVO = this.healthProc.read(healthno);
      model.addAttribute("healthVO", healthVO);

      HtcVO htcVO = this.htcProc.read(healthVO.getHtcno());
      model.addAttribute("htcVO", htcVO);

      return "/health/update_text"; // /templates/health/update_text.html
      // String content = "장소:\n인원:\n준비물:\n비용:\n기타:\n";
      // model.addAttribute("content", content);

    } else {
      ra.addAttribute("url", "/admin/login_need"); // /templates/admin/login_need.html
      return "redirect:/health/msg"; // @GetMapping(value = "/read")
    }

  }

  /**
   * 수정 처리 http://localhost:9091/health/update_text?healthno=1
   * 
   * @return
   */
  @PostMapping(value = "/update_text")
  public String update_text(HttpSession session, Model model, HealthVO healthVO, 
           RedirectAttributes ra,
           String search_word, // healthVO.word와 구분 필요
           int now_page) {
    ra.addAttribute("word", search_word);
    ra.addAttribute("now_page", now_page);

    if (this.adminProc.isMemberAdmin(session)) { // 관리자 로그인 확인
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("healthno", healthVO.getHealthno());
      map.put("passwd", healthVO.getPasswd());

      if (this.healthProc.password_check(map) == 1) { // 패스워드 일치
        this.healthProc.update_text(healthVO); // 글수정

        // mav 객체 이용
        ra.addAttribute("healthno", healthVO.getHealthno());
        ra.addAttribute("htcno", healthVO.getHtcno());
        return "redirect:/health/read"; // @GetMapping(value = "/read")

      } else { // 패스워드 불일치
        ra.addFlashAttribute("code", "passwd_fail"); // redirect -> forward -> html
        ra.addFlashAttribute("cnt", 0);
        ra.addAttribute("url", "/health/msg"); // msg.html, redirect parameter 적용

        return "redirect:/health/msg"; // @GetMapping(value = "/msg")
      }
    } else { // 정상적인 로그인이 아닌 경우 로그인 유도
      ra.addAttribute("url", "/admin/login_need"); // /templates/member/login_cookie_need.html
      return "redirect:/health/msg"; // @GetMapping(value = "/msg")
    }

  }

  /**
   * 파일 수정 폼 http://localhost:9091/health/update_file?healthno=1
   * 
   * @return
   */
  @GetMapping(value = "/update_file")
  public String update_file(HttpSession session, Model model, 
                                      int healthno,
                                      String word, 
                                      int now_page) {
    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);
    
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    
    HealthVO healthVO = this.healthProc.read(healthno);
    model.addAttribute("healthVO", healthVO);

    HtcVO htcVO = this.htcProc.read(healthVO.getHtcno());
    model.addAttribute("htcVO", htcVO);

    return "/health/update_file";

  }

  /**
   * 파일 수정 처리 http://localhost:9091/health/update_file
   * 
   * @return
   */
  @PostMapping(value = "/update_file")
  public String update_file(HttpSession session, Model model, RedirectAttributes ra,
                                      HealthVO healthVO,
                                      String word, 
                                      int now_page) {

    if (this.adminProc.isMemberAdmin(session)) {
      // 삭제할 파일 정보를 읽어옴, 기존에 등록된 레코드 저장용
      HealthVO healthVO_old = healthProc.read(healthVO.getHealthno());

      // -------------------------------------------------------------------
      // 파일 삭제 시작
      // -------------------------------------------------------------------
      String file1saved = healthVO_old.getFile1saved(); // 실제 저장된 파일명
      String thumb1 = healthVO_old.getThumb1(); // 실제 저장된 preview 이미지 파일명
      long size1 = 0;

      String upDir = Health.getUploadDir(); // C:/kd/deploy/resort_v4sbm3c/health/storage/

      Tool.deleteFile(upDir, file1saved); // 실제 저장된 파일삭제
      Tool.deleteFile(upDir, thumb1); // preview 이미지 삭제
      // -------------------------------------------------------------------
      // 파일 삭제 종료
      // -------------------------------------------------------------------

      // -------------------------------------------------------------------
      // 파일 전송 시작
      // -------------------------------------------------------------------
      String file1 = ""; // 원본 파일명 image

      // 전송 파일이 없어도 file1MF 객체가 생성됨.
      // <input type='file' class="form-control" name='file1MF' id='file1MF'
      // value='' placeholder="파일 선택">
      MultipartFile mf = healthVO.getFile1MF();

      file1 = mf.getOriginalFilename(); // 원본 파일명
      size1 = mf.getSize(); // 파일 크기

      if (size1 > 0) { // 폼에서 새롭게 올리는 파일이 있는지 파일 크기로 체크 ★
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        file1saved = Upload.saveFileSpring(mf, upDir);

        if (Tool.isImage(file1saved)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
          thumb1 = Tool.preview(upDir, file1saved, 250, 200);
        }

      } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
        file1 = "";
        file1saved = "";
        thumb1 = "";
        size1 = 0;
      }

      healthVO.setFile1(file1);
      healthVO.setFile1saved(file1saved);
      healthVO.setThumb1(thumb1);
      healthVO.setSize1(size1);
      // -------------------------------------------------------------------
      // 파일 전송 코드 종료
      // -------------------------------------------------------------------

      this.healthProc.update_file(healthVO); // Oracle 처리
      ra.addAttribute ("healthno", healthVO.getHealthno());
      ra.addAttribute("htcno", healthVO.getHtcno());
      ra.addAttribute("word", word);
      ra.addAttribute("now_page", now_page);
      
      return "redirect:/health/read";
    } else {
      ra.addAttribute("url", "/admin/login_need"); 
      return "redirect:/health/msg"; // GET
    }
  }

  /**
   * 파일 삭제 폼
   * http://localhost:9091/health/delete?healthno=1
   * 
   * @return
   */
  @GetMapping(value = "/delete")
  public String delete(HttpSession session, Model model, RedirectAttributes ra,
                               int htcno, 
                               int healthno, 
                               String word, 
                               int now_page) {
    if (this.adminProc.isMemberAdmin(session)) { // 관리자로 로그인한경우
      model.addAttribute("htcno", htcno);
      model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      
      ArrayList<HtcVOMenu> menu = this.htcProc.menu();
      model.addAttribute("menu", menu);
      
      HealthVO healthVO = this.healthProc.read(healthno);
      model.addAttribute("healthVO", healthVO);
      
      HtcVO htcVO = this.htcProc.read(healthVO.getHtcno());
      model.addAttribute("htcVO", htcVO);
      
      return "/health/delete"; // forward
      
    } else {
      ra.addAttribute("url", "/admin/login_need");
      return "redirect:/health/msg"; 
    }

  }
  
  /**
   * 삭제 처리 http://localhost:9091/health/delete
   * 
   * @return
   */
  @PostMapping(value = "/delete")
  public String delete(RedirectAttributes ra,
                              int healthno, int htcno, String word, int now_page) {
    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    // 삭제할 파일 정보를 읽어옴.
    HealthVO healthVO_read = healthProc.read(healthno);
        
    String file1saved = healthVO_read.getFile1saved();
    String thumb1 = healthVO_read.getThumb1();
    
    String uploadDir = Health.getUploadDir();
    Tool.deleteFile(uploadDir, file1saved);  // 실제 저장된 파일삭제
    Tool.deleteFile(uploadDir, thumb1);     // preview 이미지 삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료
    // -------------------------------------------------------------------
        
    this.healthProc.delete(healthno); // DBMS 삭제
        
    // -------------------------------------------------------------------------------------
    // 마지막 페이지의 마지막 레코드 삭제시의 페이지 번호 -1 처리
    // -------------------------------------------------------------------------------------    
    // 마지막 페이지의 마지막 10번째 레코드를 삭제후
    // 하나의 페이지가 3개의 레코드로 구성되는 경우 현재 9개의 레코드가 남아 있으면
    // 페이지수를 4 -> 3으로 감소 시켜야함, 마지막 페이지의 마지막 레코드 삭제시 나머지는 0 발생
    
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("htcno", htcno);
    map.put("word", word);
    
    if (this.healthProc.list_by_htcno_search_count(map) % Health.RECORD_PER_PAGE == 0) {
      now_page = now_page - 1; // 삭제시 DBMS는 바로 적용되나 크롬은 새로고침등의 필요로 단계가 작동 해야함.
      if (now_page < 1) {
        now_page = 1; // 시작 페이지
      }
    }
    // -------------------------------------------------------------------------------------

    ra.addAttribute("htcno", htcno);
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);
    
    return "redirect:/health/list_by_htcno";    
    
  }   
   
  
}


