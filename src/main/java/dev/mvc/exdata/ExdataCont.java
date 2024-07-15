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
import dev.mvc.mh.MhVO;
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
    if (session.getAttribute("memberno") != null) {
      ArrayList<CateVOMenu> menu1 = this.cateProc.menu();
      model.addAttribute("menu1", menu1);

      ArrayList<HtcVOMenu> menu = this.htcProc.menu();
      model.addAttribute("menu", menu);

      int memberno = (int) session.getAttribute("memberno");
      MemberVO memberVO = this.memberProc.read(memberno);
      model.addAttribute("memberVO", memberVO);

      MhVO mhVO = this.mhProc.read(59);
      model.addAttribute("mhVO", mhVO);

      return "exdata/read";
    } else {
      return "redirect:/member/login";
    }
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
  public String list_all_exdata_cal_desc(int intensity) {

    if (intensity == 1) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_lowmet_desc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 2) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_midmet_desc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 3) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_highmet_desc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else {
      return null;
    }
  }

  /**
   * 운동 데이터 근육활성도 오름차순 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_all_exdata_act_asc")
  public String list_all_exdata_act_asc(int intensity) {

    if (intensity == 1) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_lowact_asc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 2) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_midact_asc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 3) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_highact_asc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else {
      return null;
    }

  }

  /**
   * 운동 데이터 근육활성도 내림차순 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_all_exdata_act_desc")
  public String list_all_exdata_act_desc(int intensity) {

    if (intensity == 1) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_lowact_desc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 2) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_midact_desc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 3) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_highact_desc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else {
      return null;
    }
  }

  /**
   * 운동 데이터 부상위험도 오름차순 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_all_exdata_risk_asc")
  public String list_all_exdata_risk_asc(int intensity) {

    if (intensity == 1) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_lowrisk_asc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 2) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_midrisk_asc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 3) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_highrisk_asc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else {
      return null;
    }

  }

  /**
   * 운동 데이터 부상위험도 내림차순 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_all_exdata_risk_desc")
  public String list_all_exdata_risk_desc(int intensity) {

    if (intensity == 1) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_lowrisk_desc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 2) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_midrisk_desc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 3) {
      ArrayList<ExdataVO> list = this.exdataProc.list_all_highrisk_desc();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else {
      return null;
    }
  }

  /**
   * 부위별 운동 데이터 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_part_exdata")
  public String list_part_exdata(String part) {

    ArrayList<ExdataVO> list = this.exdataProc.list_part(part);

    JSONObject obj = new JSONObject();
    obj.put("res", list);

    return obj.toString();
  }

  /**
   * 부위별 운동 데이터 소모칼로리 오름차순 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_part_exdata_cal_asc")
  public String list_part_exdata_cal_asc(int intensity, String part) {

    if (intensity == 1) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_lowmet_asc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 2) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_midmet_asc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 3) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_highmet_asc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else {
      return null;
    }

  }

  /**
   * 부위별 운동 데이터 소모칼로리 내림차순 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_part_exdata_cal_desc")
  public String list_part_exdata_cal_desc(int intensity, String part) {

    if (intensity == 1) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_lowmet_desc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 2) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_midmet_desc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 3) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_highmet_desc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else {
      return null;
    }
  }

  /**
   * 부위별 운동 데이터 근육활성도 오름차순 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_part_exdata_act_asc")
  public String list_part_exdata_act_asc(int intensity, String part) {

    if (intensity == 1) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_lowact_asc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 2) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_midact_asc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 3) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_highact_asc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else {
      return null;
    }

  }

  /**
   * 부위별 운동 데이터 근육활성도 내림차순 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_part_exdata_act_desc")
  public String list_part_exdata_act_desc(int intensity, String part) {

    if (intensity == 1) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_lowact_desc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 2) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_midact_desc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 3) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_highact_desc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else {
      return null;
    }
  }

  /**
   * 부위별 운동 데이터 부상위험도 오름차순 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_part_exdata_risk_asc")
  public String list_part_exdata_risk_asc(int intensity, String part) {

    if (intensity == 1) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_lowrisk_asc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 2) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_midrisk_asc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 3) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_highrisk_asc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else {
      return null;
    }

  }

  /**
   * 부위별 운동 데이터 부상위험도 내림차순 전송
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_part_exdata_risk_desc")
  public String list_part_exdata_risk_desc(int intensity, String part) {

    if (intensity == 1) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_lowrisk_desc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 2) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_midrisk_desc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 3) {
      ArrayList<ExdataVO> list = this.exdataProc.list_part_highrisk_desc(part);
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else {
      return null;
    }
  }

  /**
   * 목적별 효율성 순위 정렬
   * 
   * @param commentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_effect")
  public String list_effect(int intensity, String purpose) {

    if (intensity == 1 && "weight".equals(purpose)) {
      ArrayList<ExdataVO> list = this.exdataProc.list_effect_low_weight();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 2 && "weight".equals(purpose)) {
      ArrayList<ExdataVO> list = this.exdataProc.list_effect_mid_weight();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 3 && "weight".equals(purpose)) {
      ArrayList<ExdataVO> list = this.exdataProc.list_effect_high_weight();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else if (intensity == 1 && "muscle".equals(purpose)) {
      ArrayList<ExdataVO> list = this.exdataProc.list_effect_low_muscle();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    }

    else if (intensity == 2 && "muscle".equals(purpose)) {
      ArrayList<ExdataVO> list = this.exdataProc.list_effect_mid_muscle();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    }

    else if (intensity == 3 && "muscle".equals(purpose)) {
      ArrayList<ExdataVO> list = this.exdataProc.list_effect_high_muscle();
      JSONObject obj = new JSONObject();
      obj.put("res", list);

      return obj.toString();

    } else {
      return null;
    }
  }
  
  /**
   * 운동 데이터 차트 조회 폼
   * 
   * @param commentsno
   * @return
   */
  @GetMapping(value = "/chart")
  public String list_exdata_chart_form(HttpSession session, Model model) {
    if (session.getAttribute("memberno") != null) {
      ArrayList<CateVOMenu> menu1 = this.cateProc.menu();
      model.addAttribute("menu1", menu1);

      ArrayList<HtcVOMenu> menu = this.htcProc.menu();
      model.addAttribute("menu", menu);

      int memberno = (int) session.getAttribute("memberno");
      MemberVO memberVO = this.memberProc.read(memberno);
      model.addAttribute("memberVO", memberVO);

      MhVO mhVO = this.mhProc.read(59);
      model.addAttribute("mhVO", mhVO);

      return "exdata/chart_test";
    } else {
      return "redirect:/member/login";
    }
  }

}
