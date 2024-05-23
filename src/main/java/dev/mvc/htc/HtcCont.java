package dev.mvc.htc;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.cate.CateProcInter;
import dev.mvc.cate.CateVOMenu;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/htc")
@Controller
public class HtcCont {
  @Autowired
  @Qualifier("dev.mvc.htc.HtcProc")
  private HtcProcInter htcProc;
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc")  // @Service("dev.mvc.admin.AdminProc")
  private AdminProcInter adminProc;
  
  @Autowired
  @Qualifier("dev.mvc.cate.CateProc")  // @Service("dev.mvc.admin.AdminProc")
  private CateProcInter cateProc;
  
  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
  public int record_per_page = 10;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_block = 10;
  
  public HtcCont() {
    System.out.println("-> HtcCont created.");  
  }
  

  
  /**
   * create 폼 데이터 처리
   * http://localhost:9091/htc/list_search
   * @param model
   * @param htcVO
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/create") // http://localhost:9091/htc/create
  public String create(Model model, @Valid HtcVO htcVO, BindingResult bindingResult, 
      @RequestParam(name="word", defaultValue="") String word,
      @RequestParam(name="now_page", defaultValue="1") int now_page) {
    
    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);
    
    if (bindingResult.hasErrors()) {
      // 페이징 목록
      ArrayList<HtcVO> list = this.htcProc.list_search_paging(word, now_page, this.record_per_page);    
      model.addAttribute("list", list);
      
      // 페이징 버튼 목록
      int search_count = this.htcProc.list_search_count(word);
      
      // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
      int no = search_count - ((now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);
      
      String paging = this.htcProc.pagingBox(now_page, 
          word, "htc/list_search", search_count, this.record_per_page, this.page_per_block);
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);
      
      return "htc/list_search";  // /templates/htc/list_search.html
    }
    
    int cnt = this.htcProc.create(htcVO);
    System.out.println("-> cnt: " + cnt);

    model.addAttribute("cnt", cnt);
    if (cnt == 1) { // 등록 성공
//      model.addAttribute("code", "create_success");
//      model.addAttribute("name", htcVO.getName());
//      model.addAttribute("namesub", htcVO.getNamesub());
      
      return "redirect:/htc/list_search"; // /htc/list_search.html
      
    } else { // 실패
      model.addAttribute("code", "create_fail");
      return "htc/msg"; // /templates/htc/msg.html
    }
  }
  


  /**
   * 조회 + 목록
   * @param model
   * @param htcno 조회할 카테고리 번호
   * @return
   */
  @GetMapping(value="/read/{htcno}")
  public String read(Model model, 
                            @PathVariable("htcno") Integer htcno, 
                            @RequestParam(name="word", defaultValue = "") String word,
                            @RequestParam(name="now_page", defaultValue="1") int now_page) {
    
    // word = Tool.checkNull(word);
    
    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);
    
    HtcVO htcVO = this.htcProc.read(htcno);
    model.addAttribute("htcVO", htcVO);
    
    // 페이징 목록
    ArrayList<HtcVO> list = this.htcProc.list_search_paging(word, now_page, this.record_per_page);    
    model.addAttribute("list", list);
    
    // 페이징 버튼 목록
    int search_count = this.htcProc.list_search_count(word);
    String paging = this.htcProc.pagingBox(now_page, 
        word, "/htc/list_search", search_count, this.record_per_page, this.page_per_block);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    
    model.addAttribute("word", word);
    
    // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
    int no = search_count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("no", no);
    
    return "htc/read";  // /templates/htc/read.html
    
  }
  
  /**
   * 수정폼
   * @param model
   * @param htcno 조회할 카테고리 번호
   * @return
   */
  @GetMapping(value="/update/{htcno}")
  public String update(HttpSession session,
                                Model model, 
                                @PathVariable("htcno") Integer htcno, 
                                @RequestParam(name="word", defaultValue = "") String word,
                                @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    
    if (this.adminProc.isMemberAdmin(session)) {
      ArrayList<HtcVOMenu> menu = this.htcProc.menu();
      model.addAttribute("menu", menu);
      
      HtcVO htcVO = this.htcProc.read(htcno);
      model.addAttribute("htcVO", htcVO);
      
      // 페이징 목록
      ArrayList<HtcVO> list = this.htcProc.list_search_paging(word, now_page, this.record_per_page);    
      model.addAttribute("list", list);
      
      // 페이징 버튼 목록
      int search_count = this.htcProc.list_search_count(word);
      String paging = this.htcProc.pagingBox(now_page, 
          word, "/htc/list_search", search_count, this.record_per_page, this.page_per_block);
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);
      
      model.addAttribute("word", word);
      
      // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
      int no = search_count - ((now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);
      
      return "htc/update";  // /templates/htc/update.html

    } else {
      return "redirect:/admin/login_form_need";  // redirect
    }
    
    
  }
  
  /**
   * 수정 처리
   * @param model
   * @param htcVO
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/update") // http://localhost:9091/htc/update
  public String update_process(HttpSession session,
                                            Model model, 
                                            @Valid HtcVO htcVO, BindingResult bindingResult, 
                                            @RequestParam(name="word", defaultValue = "") String word,
                                            @RequestParam(name="now_page", defaultValue="1") int now_page) {
    if (this.adminProc.isMemberAdmin(session)) {
      ArrayList<HtcVOMenu> menu = this.htcProc.menu();
      model.addAttribute("menu", menu);
      
      if (bindingResult.hasErrors()) {
        // 페이징 목록
        ArrayList<HtcVO> list = this.htcProc.list_search_paging(word, now_page, this.record_per_page);    
        model.addAttribute("list", list);
        
        // 페이징 버튼 목록
        int search_count = this.htcProc.list_search_count(word);
        String paging = this.htcProc.pagingBox(now_page, 
            word, "htc/list_search", search_count, this.record_per_page, this.page_per_block);
        model.addAttribute("paging", paging);
        model.addAttribute("now_page", now_page);
        
        return "htc/update";  // /templates/htc/update.html
      }
      
      int cnt = this.htcProc.update(htcVO);
//      System.out.println("-> cnt: " + cnt);

      model.addAttribute("cnt", cnt);
      if (cnt == 1) {
        return "redirect:/htc/update/" + htcVO.getHtcno() + "?word=" + Tool.encode(word) + "&now_page=" + now_page;
        
      } else {
        model.addAttribute("code", "update_fail");
        return "htc/msg"; // /templates/htc/msg.html
      }
    } else {
      return "redirect:/admin/login_form_need";  // redirect
    }
  }
  
  /**
   * Delete form
   * http://localhost:9091/htc/delete/1
   * @param model
   * @param htcno htcgory number to delete.
   * @return
   */
  @GetMapping(value="/delete/{htcno}")
  public String delete(HttpSession session, Model model, 
                               @PathVariable("htcno") Integer htcno, 
                               @RequestParam(name="word", defaultValue = "") String word,
                               @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    
    if (this.adminProc.isMemberAdmin(session)) {
      ArrayList<HtcVOMenu> menu = this.htcProc.menu();
      model.addAttribute("menu", menu);
      
      HtcVO htcVO = this.htcProc.read(htcno);
      model.addAttribute("htcVO", htcVO);
      
      // 페이징 목록
      ArrayList<HtcVO> list = this.htcProc.list_search_paging(word, now_page, this.record_per_page);    
      model.addAttribute("list", list);
      
      // 페이징 버튼 목록
      int search_count = this.htcProc.list_search_count(word);
      String paging = this.htcProc.pagingBox(now_page, 
          word, "/htc/list_search", search_count, this.record_per_page, this.page_per_block);
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);
      
      model.addAttribute("word", word);
      
      // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
      int no = search_count - ((now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);
      
      return "htc/delete";  // /templates/htc/delete.html
    } else {
      return "redirect:/admin/login_form_need";  // redirect
    }
    
  }
  
  /**
   * Delete process
   * @param model
   * @param htcno 삭제할 레코드 번호
   * @param bindingResult
   * @return
   */
  @PostMapping(value="/delete")
  public String delete_process(Model model, Integer htcno, 
                                           @RequestParam(name="word", defaultValue = "") String word,
                                           @RequestParam(name="now_page", defaultValue = "1") int now_page) {
    int cnt = this.htcProc.delete(htcno); // 삭제
    // System.out.println("-> cnt: " + cnt);
    
    model.addAttribute("cnt", cnt);
    
    // ----------------------------------------------------------------------------------------------------------
    // 마지막 페이지에서 모든 레코드가 삭제되면 페이지수를 1 감소 시켜야함.
    int search_cnt = this.htcProc.list_search_count(word);
    if (search_cnt % this.record_per_page == 0) {
      now_page = now_page - 1;
      if (now_page < 1) {
        now_page = 1; // 최소 시작 페이지
      }
    }
    // ----------------------------------------------------------------------------------------------------------
    
    if (cnt == 1) {
      return "redirect:/htc/list_search?word=" + Tool.encode(word) + "&now_page=" + now_page;
    } else {
      model.addAttribute("code", "delete_fail");
      return "htc/msg"; // /templates/htc/msg.html
    }
  }

  /**
   * 출력 순서 높임: seqno 10 -> 1
   * http://localhost:9091/htc/update_seqno_forward/1
   * @param model
   * @param htcno 조회할 카테고리 번호
   * @return
   */
  @GetMapping(value="/update_seqno_forward/{htcno}")
  public String update_seqno_forward(Model model, 
                                                       @PathVariable("htcno") Integer htcno, 
                                                       @RequestParam(name="word", defaultValue = "") String word,
                                                       @RequestParam(name="now_page", defaultValue = "1") int now_page) {

    this.htcProc.update_seqno_forward(htcno);
    
    return "redirect:/htc/list_search?word=" + Tool.encode(word) + "&now_page=" + now_page;  // /templates/htc/list_search.html
    
  }

  /**
   * 출력 순서 낮춤: seqno 1 -> 10
   * http://localhost:9091/htc/update_seqno_backward/1
   * @param model
   * @param htcno 조회할 카테고리 번호
   * @return
   */
  @GetMapping(value="/update_seqno_backward/{htcno}")
  public String update_seqno_backward(Model model, 
                                                         @PathVariable("htcno") Integer htcno, 
                                                         @RequestParam(name="word", defaultValue = "") String word,
                                                         @RequestParam(name="now_page", defaultValue = "1") int now_page) {
   
    this.htcProc.update_seqno_backward(htcno);
    
    return "redirect:/htc/list_search?word=" + Tool.encode(word) + "&now_page=" + now_page;  // /templates/htc/list_search.html
    
  }
  
  /**
   * 카테고리 공개 설정
   * http://localhost:9091/htc/update_visible_y/1
   * @param model
   * @param htcno 조회할 카테고리 번호
   * @return
   */
  @GetMapping(value="/update_visible_y/{htcno}")
  public String update_visible_y(Model model, 
                                             @PathVariable("htcno") Integer htcno, 
                                             @RequestParam(name="word", defaultValue = "") String word) {

    this.htcProc.update_visible_y(htcno);
    
    return "redirect:/htc/list_search?word=" + Tool.encode(word);  // /templates/htc/list_search.html
  }
  
  /**
   * 카테고리 비공개 설정
   * http://localhost:9091/htc/update_visible_n/1
   * @param model
   * @param htcno 조회할 카테고리 번호
   * @return
   */
  @GetMapping(value="/update_visible_n/{htcno}")
  public String update_visible_n(Model model, 
                                             @PathVariable("htcno") Integer htcno, 
                                             @RequestParam(name="word", defaultValue = "") String word) {
    this.htcProc.update_visible_n(htcno);
    
    return "redirect:/htc/list_search?word=" + Tool.encode(word);  // /templates/htc/list_search.html
  }


  /**
   * 등록폼 + 검색 목록
   * http://localhost:9091/htc/list_search?word=개발  ← GET Form
   * http://localhost:9091/htc/list_search/개발  ← @PathVariable
   * @param model
   * @param htcVO
   * @param word 검색어
   * @param now_page 현재 페이지 번호
   * @return
   */
  @GetMapping(value="/list_search")
  public String list_search(HttpSession session, Model model, HtcVO htcVO, 
                                      @RequestParam(name="word", defaultValue="") String word, 
                                     @RequestParam(name="now_page", defaultValue="1") int now_page) {
    if (this.adminProc.isMemberAdmin(session)) {
      // htcVO.setNamesub("-"); // 폼 초기값 설정
      word = Tool.checkNull(word).trim();
      // System.out.println("--> word: " + word);
      ArrayList<CateVOMenu> menu1 = this.cateProc.menu();
      model.addAttribute("menu1", menu1);
      
      
      ArrayList<HtcVOMenu> menu = this.htcProc.menu();
      model.addAttribute("menu", menu);
      
      // 페이징 목록
      ArrayList<HtcVO> list = this.htcProc.list_search_paging(word, now_page, this.record_per_page);    
      model.addAttribute("list", list);
      
      // 페이징 버튼 목록
      int search_count = this.htcProc.list_search_count(word);
      
      String paging = this.htcProc.pagingBox(now_page, 
          word, "/htc/list_search", search_count, this.record_per_page, this.page_per_block);
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);
      
      model.addAttribute("word", word);
      
      // 일련 변호 생성: 레코드 갯수 - ((현재 페이지수 -1) * 페이지당 레코드 수)
      int no = search_count - ((now_page - 1) * this.record_per_page);
      model.addAttribute("no", no);
      
      return "htc/list_search"; // /htc/list_search.html
    } else {
      return "redirect:/admin/login_need";  // redirect
    }
    
  }  
}



