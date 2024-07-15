package dev.mvc.likesyesno;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/likesyesno")
@Controller
public class LikesyesnoCont {
  
  @Autowired
  @Qualifier("dev.mvc.likesyesno.LikesyesnoProc")
  private LikesyesnoProcInter likesyesnoProc;
  
  public LikesyesnoCont() {
    System.out.println("-> LikesyesnoCont created.");  
  }
  
  
  /***************************************************************************************/

  
  @PostMapping(value="/like_insert")
  @ResponseBody
  public String like_insert(Model model, HttpSession session,@RequestBody LikesyesnoVO likesyesnoVO) {
    
    int memberno = (int) session.getAttribute("memberno");
    int exrecordno = likesyesnoVO.getExrecordno();
    System.out.println("exrecordno-->>" + exrecordno);
    
    JSONObject obj = new JSONObject();
    likesyesnoVO.setMemberno(memberno);
    
    int cnt = this.likesyesnoProc.like_insert(likesyesnoVO);
    obj.put("cnt", cnt);
    
    return obj.toString();
  }
  
  /**
   * 좋아요 삭제
   * @param model
   * @param session
   * @param likesyesnoVO
   * @return
   */
  @PostMapping(value="/unlike")
  @ResponseBody
  public String unlike(Model model, HttpSession session,@RequestBody LikesyesnoVO likesyesnoVO) {
    
    int memberno = (int) session.getAttribute("memberno");
    int exrecordno = likesyesnoVO.getExrecordno();
    System.out.println("exrecordno-->>" + exrecordno);
    HashMap<String, Object> map = new HashMap<String, Object>();
    JSONObject obj = new JSONObject();
    System.out.println("memberno--->" + memberno);
    map.put("memberno", memberno);
    map.put("exrecordno", exrecordno);
    
    
    int cnt = this.likesyesnoProc.unlike(map);
    obj.put("cnt", cnt);
    System.out.println("cnt-->>" + cnt);
    return obj.toString();
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
}
