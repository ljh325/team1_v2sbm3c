package dev.mvc.admin;

public class AdminVO {
  private int adminno;
  private String id;
  private String passwd;
  private String aname;
  private String adate;
  private int grade;
  
  public int getAdminno() {
    return adminno;
  }
  public void setAdminno(int adminno) {
    this.adminno = adminno;
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