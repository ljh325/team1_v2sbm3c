package dev.mvc.comments;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.cate.CateVO;
import dev.mvc.cate.CateVOMenu;
import dev.mvc.contents.Contents;
import dev.mvc.contents.ContentsProcInter;
import dev.mvc.contents.ContentsVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import dev.mvc.reply.ReplyVO;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;

@RequestMapping(value = "/comments")
@Controller
public class CommentsCont {
  @Autowired
  @Qualifier("dev.mvc.comments.CommentsProc")
  private CommentsProcInter commentsProc;

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  @Qualifier("dev.mvc.contents.ContentsProc") // @Component("dev.mvc.contents.ContentsProc")
  private ContentsProcInter contentsProc;

  public CommentsCont() {
    System.out.println("-> CommentsCont Created");
  }
  
  
  

  /**
   * 댓글 생성
   * 
   * @param model
   * @param contentsno
   * @param ra
   * @return
   */
  @PostMapping(value = "/create")
  @ResponseBody
  public String create(HttpSession session, @RequestBody CommentsVO commentsVO ) {
    int memberno = (int) session.getAttribute("memberno"); // adminno FK
    commentsVO.setMemberno(memberno);

    int cnt = this.commentsProc.create(commentsVO);
    JSONObject json = new JSONObject();
    json.put("res", cnt);
    System.out.println("res->>: " + json.toString());
    return json.toString();

  }

  /**
  컨텐츠별 댓글 목록 
  {
  "list":[
  {"memberno":2,"rdate":"2024-05-31 15:54:51","replyno":6,"id":"admin","content":"675","contentsno":12},
  {"memberno":2,"rdate":"2024-05-31 15:53:39","replyno":5,"id":"admin","content":"456","contentsno":12},
  {"memberno":2,"rdate":"2024-05-31 13:05:00","replyno":4,"id":"admin","content":"대기업의 쪼개기 지분 상장","contentsno":12}
  ]
  }
  * http://localhost:9093/comments/list_by_contentsno_join?contentsno=30
  * @param contentsno
  * @return
  */
 @ResponseBody
 @GetMapping(value="/list_by_contentsno_join")
 public String list_by_contentsno_join(int contentsno) {
   // String msg="JSON 출력";
   // return msg;
   
   ArrayList<CommentsMemberVO> list = this.commentsProc.comment_list(contentsno);
   
   JSONObject obj = new JSONObject();
   obj.put("res", list);
   
   System.out.println("-> obj.toString(): " + obj.toString());

   return obj.toString();     
 }

  /**
   * 댓글 수정
   * 
   * @param session
   * @param model
   * @param commentsno
   * @param ra
   * @return
   */
  @GetMapping(value = "/update")
  public String update(HttpSession session, Model model, int commentsno, int contentsno, int now_page, String word) {

    CommentsMemberVO commentsMemberVO = this.commentsProc.read(commentsno);
    model.addAttribute("commentsVO", commentsMemberVO);
    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    
    return "comments/update";
  }

  /**
   * 댓글 수정 Proc
   * @param session
   * @param model
   * @param ra
   * @param commentsVO
   * @param contentsno
   * @return
   */
  @PostMapping(value = "/update")
  public String update(HttpSession session, Model model, RedirectAttributes ra, CommentsVO commentsVO,
                              int contentsno, int now_page, String word) {
    
    
    int cnt = this.commentsProc.update(commentsVO); // 글수정
    
    ra.addAttribute("contentsno", contentsno);
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    return "redirect:/contents/read"; // 페이지 자동 이동

  }
  
  /**
   * 댓글 삭제
   * 
   * @return
   */
  @GetMapping(value = "/delete")
  public String delete(RedirectAttributes ra, Model model , int commentsno, int contentsno, int now_page, String word) {
    
   
    System.out.println("---->> commentsno: " + commentsno);
    int cnt = this.commentsProc.delete(commentsno);

    ra.addAttribute("contentsno", contentsno);
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    return "redirect:/contents/read";

  }
  
  
}
