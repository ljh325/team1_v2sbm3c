package dev.mvc.comments;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.cate.CateVO;
import dev.mvc.cate.CateVOMenu;
import dev.mvc.contents.Contents;
import dev.mvc.contents.ContentsProcInter;
import dev.mvc.contents.ContentsVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
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
  public String create(HttpSession session, Model model, CommentsVO commentsVO, int contentsno, RedirectAttributes ra,
                            int now_page, String word ) {
    int memberno = (int) session.getAttribute("memberno"); // adminno FK
    commentsVO.setMemberno(memberno);
    

    MemberVO memberVO = this.memberProc.read(memberno);
    
    commentsVO.setId(memberVO.getId());

    this.commentsProc.create(commentsVO);
    
    ra.addAttribute("contentsno", contentsno);
    ra.addAttribute("word", word);
    ra.addAttribute("now_page", now_page);

    return "redirect:/contents/read";

  }

  /**
   * 댓글 목록
   * 
   * @param session
   * @param model
   * @return
   */
  @GetMapping(value = "/comment_list")
  public String comment_list(HttpSession session, Model model, CommentsVO commentsVO, int contentsno) {

    ArrayList<CommentsVO> list = this.commentsProc.comment_list(contentsno);
    model.addAttribute("list", list);

    return "comments/comm_list";

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

    CommentsVO commentsVO = this.commentsProc.read(commentsno);
    model.addAttribute("commentsVO", commentsVO);
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
