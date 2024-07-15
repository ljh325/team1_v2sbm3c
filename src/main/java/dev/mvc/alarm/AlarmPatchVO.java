package dev.mvc.alarm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AlarmPatchVO {
  /** 알림 번호 */
  private int alarmno;
  
  /** 회원 번호 */
  private int memberno;
  
  /** 변경사항 번호 */
  private int patchno;
  
  /** 변경사항 제목 */
  private String title;
  
  /** 등록일자 */
  private String rdate;
}
