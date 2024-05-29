package dev.mvc.adcontents;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mvc.admin.AdminProcInter;
import dev.mvc.cate.CateProcInter;
import dev.mvc.cate.CateVO;
import dev.mvc.cate.CateVOMenu;
import dev.mvc.comments.CommentsProcInter;
import dev.mvc.contents.ContentsProcInter;
import dev.mvc.contents.ContentsVO;
import dev.mvc.htc.HtcProcInter;
import dev.mvc.htc.HtcVOMenu;
import dev.mvc.member.MemberProcInter;
import dev.mvc.reply.ReplyProcInter;
import dev.mvc.tool.Security;

@RequestMapping(value = "/adcontents")
@Controller
public class AdcontentsCont {
  
  @Autowired
  @Qualifier("dev.mvc.admin.AdminProc") // @Service("dev.mvc.member.MemberProc")
  private AdminProcInter adminProc;

  @Autowired
  @Qualifier("dev.mvc.cate.CateProc") // @Component("dev.mvc.cate.CateProc")
  private CateProcInter cateProc;

  @Autowired
  @Qualifier("dev.mvc.adcontents.AdcontentsProc") // @Component("dev.mvc.contents.ContentsProc")
  private AdcontentsProcInter adcontentsProc;

  @Autowired
  @Qualifier("dev.mvc.comments.CommentsProc")
  private CommentsProcInter commentsProc;

  @Autowired
  @Qualifier("dev.mvc.reply.ReplyProc")
  private ReplyProcInter replyProc;

  @Autowired
  @Qualifier("dev.mvc.htc.HtcProc")
  private HtcProcInter htcProc;

  @Autowired
  Security security;
  
  public AdcontentsCont() {
    System.out.println("-> AdcontentsCont created.");
  }
  
  /**
   * 관리자 게시글 작성 폼
   * 
   * @param model
   * @param contentsVO
   * @param cateno
   * @return
   */
  @GetMapping(value = "/create")
  public String create(Model model, AdcontentsVO adcontentsVO, int cateno) {
    ArrayList<CateVOMenu> menu1 = this.cateProc.menu();
    model.addAttribute("menu1", menu1);

    ArrayList<HtcVOMenu> menu = this.htcProc.menu();
    model.addAttribute("menu", menu);

    CateVO cateVO = this.cateProc.read(cateno);
    model.addAttribute("cateVO", cateVO);

    return "adcontents/create"; // /templates/contents/create.html
  }

}
