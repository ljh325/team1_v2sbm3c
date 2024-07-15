package dev.mvc.alarm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AlarmVO {
  /** 알림 번호 */
  private int alarmno;
  
  /** 회원 번호 */
  private int memberno;
  
  /** 변경사항 번호 */
  private int patchno;
}
