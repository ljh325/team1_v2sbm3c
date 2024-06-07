package dev.mvc.exdata;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.cate.CateProcInter;
import dev.mvc.cate.CateVOMenu;
import dev.mvc.htc.HtcProcInter;
import dev.mvc.htc.HtcVOMenu;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import dev.mvc.mh.MhProcInter;
import dev.mvc.reply.ReplyMemberVO;
import jakarta.servlet.http.HttpSession;

@RequestMapping(value = "/exdata")
@Controller
public class ExdataCont {

  @Autowired
  @Qualifier("dev.mvc.exdata.ExdataProc") // @Service("dev.mvc.member.MemberProc")
  private ExdataProcInter exdataProc;

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  @Qualifier("dev.mvc.mh.MhProc") // @Service("dev.mvc.member.MemberProc")
  private MhProcInter mhProc;

  @Autowired
  @Qualifier("dev.mvc.cate.CateProc") // @Component("dev.mvc.cate.CateProc")
  private CateProcInter cateProc;

  @Autowired
  @Qualifier("dev.mvc.htc.HtcProc")
  private HtcProcInter htcProc;

  /**
   * 운동 데이터 조회 폼
   * 
   * @param commentsno
   * @return
   */
  @GetMapping(value = "/")
  public String list_exdata_form(HttpSession session, Model model) {
    ArrayList<CateVOMenu> menu1 = this.cateProc.menu();
    model.addAttribute("menu1", menu1);

    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);

    int memberno = (int) session.getAttribute("memberno");
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO);

    return "exdata/read";
  }

  /**
   * 운동 데이터 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_all_exdata")
  public String list_all_exdata() {

    ArrayList<ExdataVO> list = this.exdataProc.list_all();

    JSONObject obj = new JSONObject();
    obj.put("res", list);

    return obj.toString();
  }

  /**
   * 운동 데이터 소모칼로리 오름차순 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_all_exdata_cal_asc")
  public String list_all_exdata_cal_asc(int intensity) {
    
    if (intensity == 1) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_lowmet_asc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);
      
      return obj.toString();
      
    } else if (intensity == 2) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_midmet_asc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);
      
      return obj.toString();
      
    } else if (intensity == 3) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_highmet_asc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);
      
      return obj.toString();
      
    } else {
      return null;
    }
    
  }

  /**
   * 운동 데이터 소모칼로리 내림차순 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_all_exdata_cal_desc")
  public String list_all_exdata_cal_desc() {

    ArrayList<ExdataVO> list = this.exdataProc.list_all_lowmet_desc();

    JSONObject obj = new JSONObject();
    obj.put("res", list);

    return obj.toString();
  }

}
