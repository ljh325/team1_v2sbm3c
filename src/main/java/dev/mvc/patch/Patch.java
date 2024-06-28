package dev.mvc.patch;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//CREATE TABLE PATCH(
//    PATCHNO                           NUMBER(10)     NOT NULL,
//    TITLE                             VARCHAR2(100)    NOT NULL,
//    CONTENT                           CLOB(4000)     NOT NULL,
//    VIEWCNT                           NUMBER(7)    DEFAULT 0     NULL ,
//    MEMBERNO                          NUMBER(10)     NULL 
//);
@Entity @Getter @Setter @ToString
public class Patch {
  /**
   * 변경사항 번호, 식별자, sequence 자동 생성됨.
   * @Id: Primary Key
   */
  @Id
  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patch_seq")
  @SequenceGenerator(name="patch_seq", sequenceName="PATCH_SEQ", allocationSize=1)
  private int patchno;
  
  /** 제목 */
  private String title;
  
  /** 내용 */
  private String content;
  
  /** 조회수 */
  private int viewcnt;
  
  /** 등록일자 */
  private String rdate;
  
  public Patch() {
    
  }
  
  /**
   * 사용자로부터 입력 받는 필드만 명시
   * @param patchno
   * @param title
   * @param content
   * @param viewcnt
   */
  public Patch(String title, String content, String rdate, int viewcnt) {
    this.title = title;
    this.content = content;
    this.viewcnt = viewcnt;
    this.rdate = rdate;
  }
  
  
  

}
