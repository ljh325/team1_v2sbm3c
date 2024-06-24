package dev.mvc.adreply;

import java.util.List;

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

import dev.mvc.reply.ReplyMemberVO;

@RequestMapping("/adreply")
@Controller
public class AdreplyCont {
  
  @Autowired
  @Qualifier("dev.mvc.adreply.AdreplyProc")
  private AdreplyProcInter adreplyProc;
  
  public AdreplyCont() {
    System.out.println("-> AdreplyCont created.");
  }
  
  
  
  
  /**
   * 관리자 리뷰 댓글 생성 & 조회
   * @param model
   * @param adreplyVO
   * @return
   */
  @PostMapping(value="/create")
  @ResponseBody
  public String create(Model model, @RequestBody AdreplyVO adreplyVO) {

    JSONObject obj = new JSONObject();
    //if () { // 관리자가 아닐 시 리뷰글 못쓰게 막기
    //}
    
    // 웹에서 fetch함수로 가져온 reviewno를 저장
    int reviewno = adreplyVO.getReviewno(); 
    
    // 관리자 리뷰 댓글 등록 처리
    int cnt = this.adreplyProc.create(adreplyVO);
    
    //Json의 키와 값 구조로 obj에 저장
    obj.put("cnt", cnt); // 등록 성공시 1, 실패시 0
    
    // 등록한 댓글 조회
    AdreplyVO adminRead = this.adreplyProc.admin_read(reviewno);

    obj.put("adminRead", adminRead);
    
    
    return obj.toString(); // 문자열로 변환 후 리턴
  }
  
  
  /**
   * 관리자 리뷰 조회
   * @param reviewno
   * @return
   */
  @GetMapping(value = "/admin_read")
  @ResponseBody
  public String admin_read(int reviewno) {

    AdreplyVO list = adreplyProc.admin_read(reviewno);
    
    JSONObject obj = new JSONObject();
    
    
    if (list != null) { // 관리자 댓글에값이 있으면
      String adcontents = list.getAdcontents();  // 관리자 댓글
      String addate = list.getAddate();
      String adupdate = list.getAdupdate();
      
      obj.put("adcontents", adcontents); // hmtl로 보내기
      obj.put("addate", addate); // hmtl로 보내기
      obj.put("adupdate", adupdate); // hmtl로 보내기
      

    } else {
      obj.put("res", "0"); // hmtl로 보내기
    }
    //System.out.println("list:  관리자 댓글 컨트롤러2: -> " + list);

    obj.put("res", list);

    // System.out.println("-> obj.toString(): " + obj.toString());

    return obj.toString();
  }
  
  
  /**
   * 관리자 댓글 삭제 처리
   * @param model
   * @param adreplyVO
   * @return
   */
  @PostMapping(value="admin_reply_delete")
  @ResponseBody
  public String admin_reply_delete(Model model, @RequestBody AdreplyVO adreplyVO) {
    
    JSONObject obj = new JSONObject();
    
    int cnt = this.adreplyProc.delete(adreplyVO);
    obj.put("cnt", cnt);
    
    
    return obj.toString();
  }
  
  /**
   * 관리자 댓글 수정 처리
   * @param model
   * @param adreplyVO
   * @return
   */
  @PostMapping(value="admin_reply_update")
  @ResponseBody
  public String admin_reply_update(Model model, @RequestBody AdreplyVO adreplyVO) {
    
    JSONObject obj = new JSONObject();
    System.out.println("adcontents--->" + adreplyVO.getAdcontents());
    System.out.println("session.adminsno" + adreplyVO.getAdminsno());
    System.out.println("reviewno ___???? : " + adreplyVO.getReviewno());
    int cnt = this.adreplyProc.update(adreplyVO);
    System.out.println("cnt--> update::::" + cnt);
    obj.put("cnt", cnt);
    
    
    return obj.toString();
  }
  

}
