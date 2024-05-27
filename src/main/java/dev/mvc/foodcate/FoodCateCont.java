package dev.mvc.foodcate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.contents.Contents;
import dev.mvc.contents.ContentsVO;
import dev.mvc.goals.GoalsVO;
import dev.mvc.healthrecom.HealthrecomProcInter;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import dev.mvc.mh.MhVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/foodcate")
@Controller
public class FoodCateCont {
  @Autowired
  @Qualifier("dev.mvc.foodcate.FoodCateProc")
  private FoodCateProcInter foodCateProc;
  


  
  
  /** 페이지당 출력할 레코드 갯수, nowPage는 1부터 시작 */
  public int record_per_page = 3;

  /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
  public int page_per_block = 10;

 
  
  public FoodCateCont() {
    System.out.println("-> FoodCate created.");  
  }
  
  /**
   * 
   * @param session
   * @param model
   * @param foodrecomno
   * @return
   */
  @GetMapping(value = "/list_all")
  public String list_all(HttpSession session,Model model) {
    ArrayList<FoodCateVO> list = this.foodCateProc.list_all();
    FoodCateVO foodCateVO = new FoodCateVO();
    model.addAttribute("list", list);
    model.addAttribute("foodCateVO", foodCateVO);
    return "/foodcate/list_all";
  }
  
  /**
   * 파일 업로드 실행
   * @param session 관리자번호, 확인등을 위한 세션
   * @param model
   * @param foodCateVO 사용자가 추가한 정보를 포함하는 foodCateVO
   * @param foodcode 식품 db에서 식품 식별을 위한 코드
   * @param ra
   * @return
   */
  
  @PostMapping(value = "/create")
  public String create(HttpSession session,Model model,@Valid FoodCateVO foodCateVO, RedirectAttributes ra) {
    
    String fname = ""; // 원본 파일명 image
    String fupname = ""; // 저장된 파일명, image
    String thumb = ""; // preview image

    String upDir = Contents.getUploadDir(); // 파일을 업로드할 폴더 준비
    System.out.println("-> upDir: " + upDir);

    MultipartFile mf = foodCateVO.getFileMF();

    fname = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
    System.out.println("-> 원본 파일명 산출 file1: " + fname);

    long fsize = mf.getSize(); // 파일 크기
    if (fsize > 0) { // 파일 크기 체크, 파일을 올리는 경우
      if (Tool.checkUploadFile(fname) == true) { // 업로드 가능한 파일인지 검사
        // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
        fupname = Upload.saveFileSpring(mf, upDir);

        if (Tool.isImage(fupname)) { // 이미지인지 검사
          // thumb 이미지 생성후 파일명 리턴됨, width: 200, height: 150
          thumb = Tool.preview(upDir, fupname, 200, 150);
        }
        foodCateVO.setFname(fname); // 순수 원본 파일명
        foodCateVO.setFupname(fupname); // 저장된 파일명(파일명 중복 처리)
        foodCateVO.setThumb(thumb); // 원본이미지 축소판
        foodCateVO.setFsize(fsize); // 파일 크기

      } else { // 전송 못 하는 파일 형식
        ra.addFlashAttribute("code", "check_upload_file_fail"); // 업로드 할 수 없는 파일
        ra.addFlashAttribute("cnt", 0); // 업로드 할 수 없는 파일
        ra.addFlashAttribute("url", "/foodcate/msg"); // msg.html, redirect parameter 적용
        return "redirect:/foodcate/msg";

      }

    } else { // 글만 등록 하는 경우
      System.out.println("-> 글만 등록");
    }

    // ------------------------------------------------------------------------------
    // 파일 전송 코드 종료
    // ------------------------------------------------------------------------------

    // Call By Reference: 메모리 공유, Hashcode 전달
    int adminsno = (int) session.getAttribute("adminsno"); // 관리자 번호
    foodCateVO.setAdminsno(adminsno);  // 식단을 입력한 관리자의 번호를 입력
    
    int cnt = this.foodCateProc.create(foodCateVO);

    // ------------------------------------------------------------------------------
    // PK의 return
    // ------------------------------------------------------------------------------
    // System.out.println("--> contentsno: " + contentsVO.getContentsno());
    // mav.addObject("contentsno", contentsVO.getContentsno()); // redirect
    // parameter 적용
    // ------------------------------------------------------------------------------

    if (cnt == 1) {
//        type 1
//        return "<h1>파일 업로드 성공</h1>"; // 연속 파일 업로드 발생

//        type 2
//        model.addAttribute("cnt", cnt);
//        model.addAttribute("code","create_success");
//        return "contents/msg";

//        type3 권장
//        return "redirect:/contents/list_all";
//        ra.addFlashAttribute("cateno", contentsVO.getCateno()); // controller-> controller: X
  

      return "redirect:/foodcate/list_all";

//        return "redirect:/contents/list_by_cateno?cateno=" + contentsVO.getCateno();

    } else {

      ra.addFlashAttribute("cnt", 0); // 업로드 할 수 없는 파일
      ra.addFlashAttribute("url", "/foodcate/msg"); // msg.html, redirect parameter 적용
      return "redirect:/foodcate/msg"; // Post -> Get - param...
    }

//    } else { // 로그인 실패한 경우

//      return "redirect:/member/login_form_need";
  }

  @PostMapping(value = "/delete")
  public String list_all(HttpSession session,Model model,@RequestParam("foodcateno") int foodcateno) {
    
    int cnt = this.foodCateProc.delete(foodcateno);
    ArrayList<FoodCateVO> list = this.foodCateProc.list_all();
    FoodCateVO foodCateVO = new FoodCateVO();
    model.addAttribute("list", list);
    model.addAttribute("foodCateVO", foodCateVO);

    return "redirect:/foodcate/list_all";
  }
  
  }
  
  
  
  

