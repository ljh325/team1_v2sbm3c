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

import dev.mvc.member.MemberProcInter;
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
  
  /**
   * 운동 데이터 조회 폼
  * @param commentsno
  * @return
  */
 @GetMapping(value="/")
 public String list_exdata_form(HttpSession session, Model model ) {
   
   
   return "exdata/read";     
 }
  
  /**
   * 운동 데이터 전송
  * @param commentsno
  * @return
  */
 @ResponseBody
 @GetMapping(value="/list_exdata")
 public String list_exdata() {
   
   ArrayList<ExdataVO> list = this.exdataProc.list_all();
   
   JSONObject obj = new JSONObject();
   obj.put("res_reply", list);
   

   return obj.toString();     
 }
  
  

}
