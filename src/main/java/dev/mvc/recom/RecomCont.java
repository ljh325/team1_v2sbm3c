package dev.mvc.recom;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.cate.CateProcInter;
import dev.mvc.comments.CommentsMemberVO;
import dev.mvc.comments.CommentsVO;
import dev.mvc.contents.ContentsProcInter;
import dev.mvc.member.MemberProcInter;
import jakarta.servlet.http.HttpSession;

@RequestMapping(value = "/recom")
@Controller
public class RecomCont {
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  @Qualifier("dev.mvc.cate.CateProc") // @Component("dev.mvc.cate.CateProc")
  private CateProcInter cateProc;

  @Autowired
  @Qualifier("dev.mvc.contents.ContentsProc") // @Component("dev.mvc.contents.ContentsProc")
  private ContentsProcInter contentsProc;
  
  @Autowired
  @Qualifier("dev.mvc.recom.RecomProc")
  private RecomProcInter recomProc;
  
  public RecomCont() {
    System.out.println("-> RecomCont created.");
  }
  
  
  
  /**
   * 최초 추천
   * @param session
   * @param recomVO
   * @return
   */
  @PostMapping(value = "/create")
  @ResponseBody
  public String create(HttpSession session, @RequestBody RecomVO recomVO) {
    int memberno = (int) session.getAttribute("memberno"); // adminno FK
    recomVO.setMemberno(memberno);

    int cnt = this.recomProc.create(recomVO);
    if (cnt == 1) {
      this.contentsProc.recom(recomVO.getContentsno());
    }
    JSONObject json = new JSONObject();
    json.put("res", cnt);
    return json.toString();
  }
  
  /**
   * 최초 추천 여부 조회
   * 
   * @param contentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/read", produces = "application/json")
  public String read(HttpSession session, int contentsno) {
    int memberno = (int)session.getAttribute("memberno");
    
    HashMap<String, Object> hashmap = new HashMap<String, Object>();
    hashmap.put("memberno", memberno);
    hashmap.put("contentsno", contentsno);  
    
    int cnt = this.recomProc.recom_read(hashmap);
    

    JSONObject obj = new JSONObject();
    obj.put("res", cnt);

    return obj.toString();
  }
  
  /**
   * 추천 상태 조회
   * 
   * @param replyno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/check", produces = "application/json")
  public String check(HttpSession session, int contentsno) {
    int memberno = (int)session.getAttribute("memberno");
    
    HashMap<String, Object> hashmap = new HashMap<String, Object>();
    hashmap.put("memberno", memberno);
    hashmap.put("contentsno", contentsno);  
    
    RecomVO recomVO =  this.recomProc.recom_check(hashmap);
    
    JSONObject row = new JSONObject();
    
    row.put("recomno", recomVO.getRecomno());
    row.put("contentsno", recomVO.getContentsno());
    row.put("memberno", recomVO.getMemberno());
    row.put("recom", recomVO.getRecom());
    

    JSONObject obj = new JSONObject();
    obj.put("res", row);

    return obj.toString();
  }
  
  /**
   * 추천 수 증가
   * @param session
   * @param recomVO
   * @return
   */
  @PostMapping(value = "/increase")
  @ResponseBody
  public String increase(HttpSession session, @RequestBody RecomVO recomVO) {

    int memberno = (int) session.getAttribute("memberno"); // 보안성 향상

    int cnt = 0;
    if (memberno == recomVO.getMemberno()) { // 회원 자신이 쓴 글만 수정 가능
      HashMap<String,Object> hashMap = new HashMap<String, Object>();
      hashMap.put("memberno", memberno);
      hashMap.put("contentsno", recomVO.getContentsno());
      cnt = this.recomProc.recom(hashMap);
      if(cnt == 1) {
        this.contentsProc.recom(recomVO.getContentsno());
      }
    }
    JSONObject json = new JSONObject();
    json.put("res", cnt); // 1: 성공, 0: 실패

    return json.toString();
  }
  
  /**
   * 추천 수 감소
   * @param session
   * @param recomVO
   * @return
   */
  @PostMapping(value = "/decrease")
  @ResponseBody
  public String decrease(HttpSession session, @RequestBody RecomVO recomVO) {

    int memberno = (int) session.getAttribute("memberno"); // 보안성 향상

    int cnt = 0;
    if (memberno == recomVO.getMemberno()) { // 회원 자신이 쓴 글만 수정 가능
      HashMap<String,Object> hashMap = new HashMap<String, Object>();
      hashMap.put("memberno", memberno);
      hashMap.put("contentsno", recomVO.getContentsno());
      cnt = this.recomProc.recom_cancel(hashMap);
      
      if(cnt == 1) {
        this.contentsProc.recom_cancel(recomVO.getContentsno());
      }
    }
    JSONObject json = new JSONObject();
    json.put("res", cnt); // 1: 성공, 0: 실패

    return json.toString();
  }
  
  
  
}
