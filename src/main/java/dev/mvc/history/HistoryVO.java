package dev.mvc.history;

import lombok.Getter;
import lombok.Setter;


@Setter @Getter
public class HistoryVO {
  
/**********************************/
/* Table Name: 운동기록 */
/**********************************/
//  CREATE TABLE HISTORY(
//      HISTORYNO   NUMBER(10)   NOT NULL  PRIMARY KEY,  //운동기록
//      START_TIME  DATE         NULL,                   //운동기록번호
//      LAST_TIME   DATE         NULL,                   //운동시작시간
//      MEMBERNO    NUMBER(10)   NOT NULL                //회원 번호
//  );
  
  /** 운동기록 */
  private int historyno;
  /** 운동기록번호 */
  private String start_time = "";
  /** 운동시작시간 */
  private String last_time = "";
  /** 회원 번호 */
  private int memberno; 
  /** 총 운동 시간 */
  private int totalt; 
  
  
  
}
