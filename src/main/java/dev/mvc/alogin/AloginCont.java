package dev.mvc.alogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import dev.mvc.admin.AdminProcInter;
import dev.mvc.admin.AdminVO;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

@RequestMapping("/alogin")
@Controller
public class AloginCont {

    @Autowired
    @Qualifier("dev.mvc.alogin.AloginProc")
    private AloginProcInter aloginProc;

    @Autowired
    @Qualifier("dev.mvc.admin.AdminProc")
    private AdminProcInter adminProc;

    public AloginCont() {
        System.out.println("-> AloginCont created.");
    }

    @GetMapping(value = "/alogin_form")
    public String alogin_insert(Model model, Integer adminsno) {
        if (adminsno == null) {
            throw new IllegalArgumentException("adminsno parameter is required");
        }

        AdminVO adminVO = this.adminProc.read(adminsno);
        model.addAttribute("adminVO", adminVO);

        ArrayList<AloginVO> aloginVO = this.aloginProc.alogin_read(adminsno);
        model.addAttribute("aloginVO", aloginVO);

        return "alogin/alogin_form";
    }
    /**
     * 관리자 로그인 내역 조회
     * @param model
     * @param adminVO
     * @return
     */
    @GetMapping(value="/list")
    public String list(HttpSession session, Model model) {

      int adminsno = (int)session.getAttribute("adminsno");
      System.out.println("-> adminsno: " + adminsno);
      ArrayList<AloginVO> list = this.aloginProc.alogin_read(adminsno);
      System.out.println("-> list.size(): " + list.size());
      model.addAttribute("list", list); // 회원 목록 반환
      return "alogin/list"; // templates/alogin/list.html
    }

    
}
