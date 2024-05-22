package dev.mvc.admin;

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
  
  public int getAdminsno() {
    return adminsno;
  }
  public void setAdminno(int adminsno) {
    this.adminsno = adminsno;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getPasswd() {
    return passwd;
  }
  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }
  public String getAname() {
    return aname;
  }
  public void setAname(String aname) {
    this.aname = aname;
  }
  public String getAdate() {
    return adate;
  }
  public void setAdate(String adate) {
    this.adate = adate;
  }
  public int getGrade() {
    return grade;
  }
  public void setGrade(int grade) {
    this.grade = grade;
  }
  
  
}