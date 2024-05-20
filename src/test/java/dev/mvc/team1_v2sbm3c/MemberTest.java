package dev.mvc.team1_v2sbm3c;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;

@SpringBootTest
public class MemberTest {
  
  
  @Autowired
  @Qualifier("dev.mvc.member.MemberProc")  // @Service("dev.mvc.member.MemberProc")
  private MemberProcInter memberProc;

  /**
   * 회원가입 테스트
   * MemberVO
   * */
  @Test
  public void create() {
    MemberVO memberVO = new MemberVO();
    
    String id = "juch";
    String passwd = "11111";
    String mname = "박주찬";
    String tel = "01083720288";
    String zipcode = "123";
    String address1 = "dkdk";
    String address2 = "dkdk";  

    int birth = 990424;
    String sex = "M";
    
    memberVO.setId(id); // setId id값을 memberVO에 할당
    memberVO.setPasswd(passwd);
    memberVO.setMname(mname);
    memberVO.setTel(tel);
    memberVO.setZipcode(zipcode);
    memberVO.setAddress1(address1);
    memberVO.setAddress2(address2);
    memberVO.setBirth(birth);
    memberVO.setSex(sex);
    
    int cnt = this.memberProc.create(memberVO);
    
    System.out.println("-> cnt: " + cnt);
    //====================================================회원가입 //
    
    
  }
}
