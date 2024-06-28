package dev.mvc.admin;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AdminVO {
  /** 관리자 번호*/
  private int adminsno;
  /** 아이디(이메일) */
  private String id;
  /** 패스워드 */
  private String passwd;
  /** 회원 성명 */
  private String aname;
  /** 가입 일 */
  private String adate;
  /** 등급 */
  private int grade;
  

  
}