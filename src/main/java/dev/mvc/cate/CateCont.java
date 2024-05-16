package dev.mvc.cate;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@RequestMapping("/cate")
@Controller
public class CateCont {

  @Autowired
  @Qualifier("dev.mvc.cate.CateProc")
  private CateProcInter cateProc;

  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
  public int record_per_page = 10;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_block = 10;

  public CateCont() {
    System.out.println("-> CateCont created.");
  }

  @GetMapping(value = "/create") // http://localhost:9091/cate/create
  public String create(Model model, CateVO cateVO) {

    ArrayList<CateVO> list = this.cateProc.list_all();
    model.addAttribute("list", list);

    return "cate/create"; // /templates/cate/create.html
  }
  
  @PostMapping(value="/create") // http://localhost:9091/cate/create
  public String create(Model model, @Valid CateVO cateVO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "/cate/create";
    }
    
    int cnt = this.cateProc.create(cateVO);
    System.out.println("-> cnt: " + cnt);
    
    if (cnt == 1) {
      model.addAttribute("code", "create_success");
      model.addAttribute("name", cateVO.getName());
      model.addAttribute("namesub", cateVO.getNamesub());
    } else {
      model.addAttribute("code", "create_fail");
      model.addAttribute("cnt", cnt);
      return "/cate/msg"; // /templates/cate/msg.html
    }
    return "cate/create";
  }
  
  @GetMapping(value="/list_all")
  public String list_all(Model model) {
    ArrayList<CateVO> list = this.cateProc.list_all();
    model.addAttribute("list",list);
    
    return "/cate/list_all"; // /cate/list_all.html
  }

}
