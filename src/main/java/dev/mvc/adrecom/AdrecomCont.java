package dev.mvc.adrecom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mvc.adcontents.AdcontentsProcInter;
import dev.mvc.cate.CateProcInter;
import dev.mvc.member.MemberProcInter;

@RequestMapping(value = "/adrecom")
@Controller
public class AdrecomCont {
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  @Autowired
  @Qualifier("dev.mvc.cate.CateProc") // @Component("dev.mvc.cate.CateProc")
  private CateProcInter cateProc;

  @Autowired
  @Qualifier("dev.mvc.adcontents.AdcontentsProc") // @Component("dev.mvc.contents.ContentsProc")
  private AdcontentsProcInter adcontentsProc;
  
  @Autowired
  @Qualifier("dev.mvc.adrecom.AdrecomProc")
  private AdrecomProcInter adrecomProc;
  
  public AdrecomCont() {
    System.out.println("-> AdrecomCont created.");
  }
  
  

}
