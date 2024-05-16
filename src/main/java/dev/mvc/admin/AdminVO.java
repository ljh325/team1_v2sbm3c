package dev.mvc.admin;

public class AdminVO {
  private int adminno;
  private String adminid;
  private String password;
  private int grade;
  
  public int getAdminno() {
    return adminno;
  }
  public void setAdminno(int adminno) {
    this.adminno = adminno;
  }
  public String getAdminid() {
    return adminid;
  }
  public void setAdminid(String adminid) {
    this.adminid = adminid;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public int getGrade() {
    return grade;
  }
  public void setGrade(int grade) {
    this.grade = grade;
  }
  
  @Override
  public String toString() {
    return "AdminVO [adminno=" + adminno + ", adminid=" + adminid + ", password=" + password + ", grade=" + grade + "]";
  }
  
  
}


