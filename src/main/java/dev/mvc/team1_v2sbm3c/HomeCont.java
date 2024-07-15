package dev.mvc.team1_v2sbm3c;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.mvc.cate.CateProcInter;
import dev.mvc.cate.CateVO;
import dev.mvc.cate.CateVOMenu;
import dev.mvc.health.HealthProcInter;
import dev.mvc.htc.HtcProcInter;
import dev.mvc.htc.HtcVOMenu;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import dev.mvc.mh.MhProcInter;
import dev.mvc.mh.MhVO;
import dev.mvc.tool.Security;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeCont {
  @Autowired
  @Qualifier("dev.mvc.cate.CateProc")
  private CateProcInter cateProc;

  @Autowired
  @Qualifier("dev.mvc.htc.HtcProc")
  private HtcProcInter htcProc;

  @Autowired
  @Qualifier("dev.mvc.mh.MhProc")
  private MhProcInter mhProc;

  @Autowired
  @Qualifier("dev.mvc.member.MemberProc") // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;
  
  @Autowired
  @Qualifier("dev.mvc.health.HealthProc") // @Component("dev.mvc.health.HealthProc")
  private HealthProcInter healthProc;

  public HomeCont() {
    System.out.println("-> HomeCont created.");
  }

  @GetMapping(value = "/")
  public String home(HttpSession session, Model model) { // 파일명 return

    if (this.memberProc.isMember(session)) { // 로그인이 되어있으면 = 세션에 값이 있으면
      int memberno = (int) session.getAttribute("memberno");
      ArrayList<CateVOMenu> menu1 = this.cateProc.menu();
      ArrayList<HtcVOMenu> menu = this.htcProc.menu();
      MhVO mhVO = this.mhProc.read_n(memberno);
      MemberVO memberVO = this.memberProc.read(memberno);
      if (mhVO != null) {
        float kg = mhVO.getKg();
        float cm = mhVO.getCm() / 100;
        float bmi_float = kg / (cm * cm); // 소수점 제한 안한 값
        String bmi = String.format("%.2f", bmi_float); //소숫점 2째자리까지
        
        System.out.println("kg -> " + kg);
        System.out.println("cm -> " + cm);
        System.out.println("bmi -> " + bmi);
        
        
          String result = "";
          String risk = "";
          String exercise_recommendation = "";
          String diet_recommendation = "";
          
          if (bmi_float < 18.5) {
              result += " 저체중";
              risk += "영양 결핍, 빈혈 위험이 있습니다.";
              exercise_recommendation += "근육을 증가시키기 위한 저강도 운동과 단백질 섭취를 권장합니다.";
              diet_recommendation += "고단백 식품과 영양가 있는 식사를 권장합니다.";
          } else if (bmi_float < 23) {
              result += "정상";
              risk = "건강한 상태입니다.";
              exercise_recommendation += "유산소 운동과 근력 운동을 균형 있게 하십시오.";
              diet_recommendation += "균형 잡힌 식단을 유지하십시오.";
          } else if (bmi_float < 25) {
              result += "과체중";
              risk = "심혈관 질환 및 당뇨병 위험이 있습니다.";
              exercise_recommendation += "체중 감량을 위한 유산소 운동을 권장합니다.";
              diet_recommendation += "저칼로리 식단과 적절한 영양 섭취를 권장합니다.";
          } else {
              result += "비만";
              risk = "고혈압, 당뇨병, 심혈관 질환 및 관절염 위험이 있습니다.";
              exercise_recommendation += "체중 감량을 위한 저강도 유산소 운동과 식이 조절을 권장합니다.";
              diet_recommendation += "저칼로리 식단과 함께 식사량을 조절하십시오.";
          }
          //if (gender == 'male' and waist >= 102) or (gender == 'female' and waist >= 88):
          //  risk += " 또한, 복부비만으로 심혈관 질환 위험이 증가합니다."

          //if (gender == 'male' and muscle_mass <= 34) or (gender == 'female' and muscle_mass <= 24):
          //  risk += " 근감소증 위험이 있습니다."
        
          
          model.addAttribute("bmi", bmi);
          model.addAttribute("result" ,result);
          model.addAttribute("risk" ,risk);
          model.addAttribute("exercise_recommendation" ,exercise_recommendation);
          model.addAttribute("diet_recommendation" ,diet_recommendation);   
      } else {
        model.addAttribute("nulls","등록된 건강정보가 없습니다.");
      }
      model.addAttribute("menu1", menu1);
      model.addAttribute("menu", menu);
      model.addAttribute("mhVO", mhVO);
      model.addAttribute("memberVO", memberVO);

      return "index"; // /mh/list_all.html
    } else {

      ArrayList<CateVOMenu> menu1 = this.cateProc.menu();
      ArrayList<HtcVOMenu> menu = this.htcProc.menu();
      model.addAttribute("menu1", menu1);
      model.addAttribute("menu", menu);

      return "index"; // /mh/list_all.html
    }
  }

}
