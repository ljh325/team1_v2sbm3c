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
  public String create(HttpSession session, @RequestBody CommentsVO commentsVO) {
    int memberno = (int) session.getAttribute("memberno"); // adminno FK
    commentsVO.setMemberno(memberno);

    int cnt = this.commentsProc.create(commentsVO);
    JSONObject json = new JSONObject();
    json.put("res", cnt);
    return json.toString();

  }

  /**
   * 컨텐츠별 댓글 목록 { "list":[ {"memberno":2,"rdate":"2024-05-31
   * 15:54:51","replyno":6,"id":"admin","content":"675","contentsno":12},
   * {"memberno":2,"rdate":"2024-05-31
   * 15:53:39","replyno":5,"id":"admin","content":"456","contentsno":12},
   * {"memberno":2,"rdate":"2024-05-31
   * 13:05:00","replyno":4,"id":"admin","content":"대기업의 쪼개기 지분
   * 상장","contentsno":12} ] }
   * http://localhost:9093/comments/list_by_contentsno_join?contentsno=30
   * 
   * @param contentsno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/list_by_contentsno_join")
  public String list_by_contentsno_join(int contentsno) {
    // String msg="JSON 출력";
    // return msg;

    ArrayList<CommentsMemberVO> list = this.commentsProc.comment_list(contentsno);

    JSONObject obj = new JSONObject();
    obj.put("res", list);

    return obj.toString();
  }
  
  /**
   * 댓글 조회
   * 
   * @param replyno
   * @return
   */
  @ResponseBody
  @GetMapping(value = "/read", produces = "application/json")
  public String read(int commentsno) {

    CommentsMemberVO commentsMemberVO = this.commentsProc.read(commentsno);
//   -> obj.toString(): {"res":"ReplyVO(replyno=41, contentsno=12, memberno=2, content=댓글 수정, rdate=2024-06-03 13:21:24)"}
    JSONObject row = new JSONObject();

    row.put("commentsno", commentsMemberVO.getCommentsno());
    row.put("contentsno", commentsMemberVO.getContentsno());
    row.put("memberno", commentsMemberVO.getMemberno());
    row.put("contents", commentsMemberVO.getContents());
    row.put("rdate", commentsMemberVO.getRdate());
    row.put("replycnt", commentsMemberVO.getReplycnt());
    row.put("id", commentsMemberVO.getId());
    row.put("thumbs", commentsMemberVO.getThumbs());
    row.put("grade", commentsMemberVO.getGrade());

    JSONObject obj = new JSONObject();
    obj.put("res", row);

    return obj.toString();
  }

//  /**
//   * 댓글 수정
//   * 
//   * @param session
//   * @param model
//   * @param commentsno
//   * @param ra
//   * @return
//   */
//  @GetMapping(value = "/update")
//  public String update(HttpSession session, Model model, int commentsno, int contentsno, int now_page, String word) {
//
//    CommentsMemberVO commentsMemberVO = this.commentsProc.read(commentsno);
//    model.addAttribute("commentsVO", commentsMemberVO);
//    model.addAttribute("word", word);
//    model.addAttribute("now_page", now_page);
//    
//    return "comments/update";
//  }
//
//  /**
//   * 댓글 수정 Proc
//   * @param session
//   * @param model
//   * @param ra
//   * @param commentsVO
//   * @param contentsno
//   * @return
//   */
//  @PostMapping(value = "/update")
//  public String update(HttpSession session, Model model, RedirectAttributes ra, CommentsVO commentsVO,
//                              int contentsno, int now_page, String word) {
//    
//    
//    int cnt = this.commentsProc.update(commentsVO); // 글수정
//    
//    ra.addAttribute("contentsno", contentsno);
//    ra.addAttribute("word", word);
//    ra.addAttribute("now_page", now_page);
//
//    return "redirect:/contents/read"; // 페이지 자동 이동
//
//  }

  /**
   * 댓글 수정 처리
   * 
   * @param replyVO
   * @return
   */
  @PostMapping(value = "/update")
  @ResponseBody
  public String update(HttpSession session, @RequestBody CommentsVO commentsVO) {

    int memberno = (int) session.getAttribute("memberno"); // 보안성 향상

    int cnt = 0;
    if (memberno == commentsVO.getMemberno()) { // 회원 자신이 쓴 글만 수정 가능

      cnt = this.commentsProc.update(commentsVO);
    }
    JSONObject json = new JSONObject();
    json.put("res", cnt); // 1: 성공, 0: 실패

    return json.toString();
  }

/**
 * 댓글 삭제 처리
 * @param session
 * @param commentsVO
 * @return
 */
  @PostMapping(value = "/delete")
  @ResponseBody
  public String delete(HttpSession session, @RequestBody CommentsVO commentsVO) {
    System.out.println("-> 삭제할 수신 데이터: " + commentsVO.toString());

    int memberno = (int) session.getAttribute("memberno"); // 보안성 향상

    int cnt = 0;
    if (memberno == commentsVO.getMemberno()) { // 회원 자신이 쓴 글만 수정 가능

      cnt = this.commentsProc.delete(commentsVO.getCommentsno());
    }
    JSONObject json = new JSONObject();
    json.put("res", cnt); // 1: 성공, 0: 실패

    return json.toString();
  }

}
