package dev.mvc.mlogin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import jakarta.servlet.http.HttpSession;



@RequestMapping("/mlogin")
@Controller
public class MloginCont {

  @Autowired
  @Qualifier("dev.mvc.mlogin.MloginProc")  // @Service("dev.mvc.mlogin.MloginProc")
  private MloginProcInter mloginProc;
  
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")  // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  
  public MloginCont() {
    System.out.println("-> AloginCont created.");  
  }
  
  /***************************************************************************************/
  
  /**
   * 로그인 내역 등록폼
   * @param model
   * @param mloginVO
   * @return
   */
  @GetMapping(value="/mlogin_insert_form") // http://localhost:9093/mlogin/mlogin_insert_form
  public String mlogin_insert(Model model, int memberno) {
    
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO);  // 이름때매 넣음
    
    ArrayList<MloginVO> mloginVO = this.mloginProc.mlogin_read(memberno);
    model.addAttribute("mloginVO", mloginVO);
    
    return "mlogin/mlogin_form";    // /template/mlogin/mlogin_form.html
  }

  
  
}
