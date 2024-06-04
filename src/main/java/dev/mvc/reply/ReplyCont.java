package dev.mvc.reply;

import java.util.ArrayList;

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

import dev.mvc.comments.CommentsProcInter;
import dev.mvc.comments.CommentsVO;
import dev.mvc.comments.CommentsMemberVO;
import dev.mvc.contents.ContentsProcInter;
import dev.mvc.contents.ContentsVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping(value = "/reply")
@Controller
public class ReplyCont {
  
  @Autowired
  @Qualifier("dev.mvc.reply.ReplyProc")
  private ReplyProcInter replyProc;
  
  @Autowired
  @Qualifier("dev.mvc.comments.CommentsProc")
  private CommentsProcInter commentsProc;

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  @Qualifier("dev.mvc.contents.ContentsProc") // @Component("dev.mvc.contents.ContentsProc")
  private ContentsProcInter contentsProc;
  
  public ReplyCont() {
    System.out.println("-> ReplyCont created");
  }
  
//  /**
//   * 대댓글 생성 폼
//   * 
//   * @param session
//   * @param model
//   * @param commentsno
//   * @param ra
//   * @return
//   */
//  @GetMapping(value = "/create")
//  public String create(HttpSession session, Model model, int memberno, int commentsno, int contentsno, String word, int now_page) {
//    
//    MemberVO memberVO = this.memberProc.read(memberno);
//    model.addAttribute("memberVO", memberVO);
//    
//    CommentsMemberVO commentsMemberVO = this.commentsProc.read(commentsno);
//    model.addAttribute("commentsVO", commentsMemberVO);
//    
//    ContentsVO contentsVO = this.contentsProc.read(contentsno);
//    model.addAttribute("contentsVO", contentsVO);
//    
//    ArrayList<ReplyVO> list = this.replyProc.reply_list(commentsno);
//    model.addAttribute("list",list);
//    
//    model.addAttribute("word",word);
//    model.addAttribute("now_page", now_page);
//    
//    return "reply/create";
//  }
  
  /**
   * 대댓글 생성
   * 
   * @param model
   * @param contentsno
   * @param ra
   * @return
   */
  @PostMapping(value = "/create")
  @ResponseBody
  public String create( @RequestBody ReplyVO replyVO ) {
//    int memberno = (int) session.getAttribute("memberno"); // adminno FK
//    replyVO.setMemberno(memberno);

    int cnt = this.replyProc.create(replyVO);
    int reply_cnt = this.commentsProc.increase_replycnt(replyVO.getCommentsno());
    System.out.println("reply -> : " + reply_cnt);
    JSONObject json = new JSONObject();
    json.put("res", cnt);
    return json.toString();

  }
  
  /**
  댓글별 대댓글 목록 
  {
  "list":[
  {"memberno":2,"rdate":"2024-05-31 15:54:51","replyno":6,"id":"admin","content":"675","contentsno":12},
  {"memberno":2,"rdate":"2024-05-31 15:53:39","replyno":5,"id":"admin","content":"456","contentsno":12},
  {"memberno":2,"rdate":"2024-05-31 13:05:00","replyno":4,"id":"admin","content":"대기업의 쪼개기 지분 상장","contentsno":12}
  ]
  }
  * http://localhost:9093/reply/list_by_commentsno_join?commentsno=3
  * @param commentsno
  * @return
  */
 @ResponseBody
 @GetMapping(value="/list_by_commentsno_join")
 public String list_by_commentsno_join(int commentsno) {
   // String msg="JSON 출력";
   // return msg;
   
   ArrayList<ReplyMemberVO> list = this.replyProc.reply_list(commentsno);
   
   JSONObject obj = new JSONObject();
   obj.put("res_reply", list);
   

   return obj.toString();     
 }
  

  /**
   * 대댓글 수정
   * @param session
   * @param model
   * @param commentsno
   * @param contentsno
   * @param replyno
   * @param memberno
   * @return
   */
  @GetMapping(value = "/update")
  public String update(HttpSession session, Model model, int commentsno, int contentsno, int replyno, int now_page, String word) {

    CommentsMemberVO commentsMemberVO = this.commentsProc.read(commentsno);
    model.addAttribute("commentsVO", commentsMemberVO);
    
    ReplyMemberVO replyMemberVO = this.replyProc.read(replyno);
    model.addAttribute("replyVO", replyMemberVO);
    
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    
    return "reply/update";
  }

  /**
   * 대댓글 수정 Proc
   * @param session
   * @param model
   * @param ra
   * @param commentsVO
   * @param contentsno
   * @return
   */
  @PostMapping(value = "/update")
  public String update(HttpSession session, Model model, RedirectAttributes ra, ReplyVO replyVO,
                              int contentsno, int commentsno, int memberno, int now_page, String word) {
    
    
    this.replyProc.update(replyVO); // 글수정

    ra.addAttribute("contentsno", contentsno);
    ra.addAttribute("commentsno", commentsno);
    ra.addAttribute("memberno", memberno);
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    return "redirect:/reply/create";

  }
  
  /**
   * 대댓글 삭제
   * 
   * @return
   */
  @GetMapping(value = "/delete")
  public String delete(RedirectAttributes ra, Model model , int replyno, int contentsno, int commentsno, int memberno, int now_page, String word) {
    
   
    this.replyProc.delete(replyno);

    ra.addAttribute("contentsno", contentsno);
    ra.addAttribute("commentsno", commentsno);
    ra.addAttribute("memberno", memberno);
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    return "redirect:/reply/create";

  }
  
  /**
   * 대댓글 전체 삭제
   * 
   * @return
   */
  @GetMapping(value = "/delete_all")
  public String delete_all(RedirectAttributes ra, Model model , int contentsno, int commentsno, int memberno,int now_page, String word) {
    
   
    this.replyProc.delete_all(commentsno);

    ra.addAttribute("contentsno", contentsno);
    ra.addAttribute("commentsno", commentsno);
    ra.addAttribute("memberno", memberno);
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    return "redirect:/reply/create";

  }
  
  
  

}
