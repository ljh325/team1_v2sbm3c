package dev.jpa.admins;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity @Getter @Setter @ToString
public class Admins {
  /** 관리자 번호 */
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="admin_seq")
  @SequenceGenerator(name="admin_seq", sequenceName="ADMIN_SEQ", allocationSize=1)
  @Column(name = "adminsno", nullable = false)
  private int adminsno;
  
  /** 아이디(이메일) */
  @Column(name = "id", length = 20, nullable = false)
  private String id;
  
  /** 패스워드 */
  @Column(name = "passwd", length = 50, nullable = false)
  private String passwd;
  
  /** 회원 성명 */
  @Column(name = "aname", length = 20, nullable = false)
  private String aname;
  
  /** 가입 일 */
  @Column(name = "adate", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date adate;
  
  /** 등급 */
  @Column(name = "grade", nullable = false)
  private int grade;

  
  public Admins() {
    
  }
  
  /**
   * 사용자로부터 입력받는 필드만 명시
   * @param id
   * @param passwd
   * @param aname
   * @param adate
   * @param grade
   */
  public Admins(String id, String passwd, String aname, Date adate, int grade) {
    this.id = id;
    this.passwd = passwd;
    this.aname = aname;
    this.adate = adate;
    this.grade = grade;
  }
  
  
}


