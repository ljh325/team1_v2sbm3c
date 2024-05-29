package dev.mvc.reply;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.comments.CommentsProcInter;
import dev.mvc.comments.CommentsVO;
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
  
  /**
   * 대댓글 생성 폼
   * 
   * @param session
   * @param model
   * @param commentsno
   * @param ra
   * @return
   */
  @GetMapping(value = "/create")
  public String create(HttpSession session, Model model, int memberno, int commentsno, int contentsno, String word, int now_page) {
    
    MemberVO memberVO = this.memberProc.read(memberno);
    model.addAttribute("memberVO", memberVO);
    
    CommentsVO commentsVO = this.commentsProc.read(commentsno);
    model.addAttribute("commentsVO", commentsVO);
    
    ContentsVO contentsVO = this.contentsProc.read(contentsno);
    model.addAttribute("contentsVO", contentsVO);
    
    ArrayList<ReplyVO> list = this.replyProc.reply_list(commentsno);
    model.addAttribute("list",list);
    
    model.addAttribute("word",word);
    model.addAttribute("now_page", now_page);
    
    return "reply/create";
  }
  
  /**
   * 대댓글 생성
   * 
   * @param model
   * @param contentsno
   * @param ra
   * @return
   */
  @PostMapping(value = "/create")
  public String create(HttpSession session, Model model, ReplyVO replyVO,   
                             RedirectAttributes ra, HttpServletRequest request, int contentsno, int commentsno, int comment_memberno,
                             String word, int now_page) {
   
    
    int memberno = (int) session.getAttribute("memberno");
    replyVO.setMemberno(memberno);
    

    MemberVO memberVO = this.memberProc.read(memberno);
    replyVO.setId(memberVO.getId());

    this.replyProc.create(replyVO);

    ra.addAttribute("contentsno", contentsno);
    ra.addAttribute("commentsno", commentsno);
    ra.addAttribute("memberno", comment_memberno);
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    return "redirect:/reply/create";

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

    CommentsVO commentsVO = this.commentsProc.read(commentsno);
    model.addAttribute("commentsVO", commentsVO);
    
    ReplyVO replyVO = this.replyProc.read(replyno);
    model.addAttribute("replyVO", replyVO);
    
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
