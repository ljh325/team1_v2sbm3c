package dev.mvc.adrecom;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.adcontents.AdcontentsProcInter;
import dev.mvc.cate.CateProcInter;
import dev.mvc.member.MemberProcInter;
import dev.mvc.recom.RecomVO;
import jakarta.servlet.http.HttpSession;

@RequestMapping(value = "/adrecom")
@Controller
public class AdrecomCont {
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  @Qualifier("dev.mvc.cate.CateProc") // @Component("dev.mvc.cate.CateProc")
  private CateProcInter cateProc;

  @Autowired
  @Qualifier("dev.mvc.adcontents.AdcontentsProc") // @Component("dev.mvc.contents.ContentsProc")
  private AdcontentsProcInter adcontentsProc;
  
  @Autowired
  @Qualifier("dev.mvc.adrecom.AdrecomProc")
  private AdrecomProcInter adrecomProc;
  
  public AdrecomCont() {
    System.out.println("-> AdrecomCont created.");
  }
  
  /**
   * 최초 추천
   * @param session
   * @param adrecomVO
   * @return
   */
  @PostMapping(value = "/create")
  @ResponseBody
  public String create(HttpSession session, @RequestBody AdrecomVO adrecomVO) {
    int memberno = (int) session.getAttribute("memberno"); // adminno FK
    adrecomVO.setMemberno(memberno);

    int cnt = this.adrecomProc.create(adrecomVO);
    if (cnt == 1) {
      this.adcontentsProc.recom(adrecomVO.getAdcontentsno());
    }
    JSONObject json = new JSONObject();
    json.put("res", cnt);
    return json.toString();
  }
  
  /**
   * 최초 추천 여부 조회
   * 
   * @param adcontentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/read", produces = "application/json")
  public String read(HttpSession session, int adcontentsno) {
    int memberno = (int)session.getAttribute("memberno");
    
    HashMap<String, Object> hashmap = new HashMap<String, Object>();
    hashmap.put("memberno", memberno);
    hashmap.put("adcontentsno", adcontentsno);  
    
    int cnt = this.adrecomProc.adrecom_read(hashmap);
    

    JSONObject obj = new JSONObject();
    obj.put("res", cnt);

    return obj.toString();
  }
  
/**
 * 추천 상태 조회
 * @param session
 * @param adcontentsno
 * @return
 */
  @ResponseBody
  @GetMapping(value = "/check", produces = "application/json")
  public String check(HttpSession session, int adcontentsno) {
    int memberno = (int)session.getAttribute("memberno");
    
    HashMap<String, Object> hashmap = new HashMap<String, Object>();
    hashmap.put("memberno", memberno);
    hashmap.put("adcontentsno", adcontentsno);  
    
    AdrecomVO adrecomVO =  this.adrecomProc.adrecom_check(hashmap);
    
    JSONObject row = new JSONObject();
    
    row.put("adrecomno", adrecomVO.getAdrecomno());
    row.put("adcontentsno", adrecomVO.getAdcontentsno());
    row.put("memberno", adrecomVO.getMemberno());
    row.put("adrecom", adrecomVO.getAdrecom());
    

    JSONObject obj = new JSONObject();
    obj.put("res", row);

    return obj.toString();
  }
  
  /**
   * 추천 수 증가
   * @param session
   * @param adrecomVO
   * @return
   */
  @PostMapping(value = "/increase")
  @ResponseBody
  public String increase(HttpSession session, @RequestBody AdrecomVO adrecomVO) {

    int memberno = (int) session.getAttribute("memberno"); // 보안성 향상

    int cnt = 0;
    if (memberno == adrecomVO.getMemberno()) { // 회원 자신이 쓴 글만 수정 가능
      HashMap<String,Object> hashMap = new HashMap<String, Object>();
      hashMap.put("memberno", memberno);
      hashMap.put("adcontentsno", adrecomVO.getAdcontentsno());
      cnt = this.adrecomProc.adrecom(hashMap);
      if(cnt == 1) {
        this.adcontentsProc.recom(adrecomVO.getAdcontentsno());
      }
    }
    JSONObject json = new JSONObject();
    json.put("res", cnt); // 1: 성공, 0: 실패

    return json.toString();
  }
  
  /**
   * 추천 수 감소
   * @param session
   * @param adrecomVO
   * @return
   */
  @PostMapping(value = "/decrease")
  @ResponseBody
  public String decrease(HttpSession session, @RequestBody AdrecomVO adrecomVO) {

    int memberno = (int) session.getAttribute("memberno"); // 보안성 향상

    int cnt = 0;
    if (memberno == adrecomVO.getMemberno()) { // 회원 자신이 쓴 글만 수정 가능
      HashMap<String,Object> hashMap = new HashMap<String, Object>();
      hashMap.put("memberno", memberno);
      hashMap.put("adcontentsno", adrecomVO.getAdcontentsno());
      cnt = this.adrecomProc.adrecom_cancel(hashMap);
      
      if(cnt == 1) {
        this.adcontentsProc.recom_cancel(adrecomVO.getAdcontentsno());
      }
    }
    JSONObject json = new JSONObject();
    json.put("res", cnt); // 1: 성공, 0: 실패

    return json.toString();
  }
  

}
