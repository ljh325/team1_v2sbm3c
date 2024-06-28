package dev.mvc.history;

import lombok.Getter;
import lombok.Setter;


@Setter @Getter
public class HistoryVO {
  
/**********************************/
/* Table Name: 운동기록 */
/**********************************/
//  EXRECORDNO                        NUMBER(10)     NOT NULL    PRIMARY KEY,  (운동기록번호)
//  EXNAME                            VARCHAR2(100)    NOT NULL,    (운동명)
//  EXTYPE                            VARCHAR2(100)    NOT NULL,    (운동유형)
//  HISCALORIE                        NUMBER(10)     NULL ,         (소모칼로리)
//  DURATION                          VARCHAR2(100)    NULL ,       (운동시간)
//  NOTES                             VARCHAR2(1000)     NULL ,     (메모)
//  STARTDATE                         DATE     NOT NULL,            (등록날짜)
//  MEMBERNO                          NUMBER(10)     NOT NULL (FK)  (회원번호)
  
  /** 운동기록번호 */
  private int exrecordno;
  /** 운동명 */
  private String exname;
  /** 운동유형 */
  private String extype;
  /** 소모칼로리 */
  private int hiscalorie; 
  /** 운동시간 */
  private int duration;
  /** 메모 */
  private String notes;
  /** 등록날짜 */
  private String startdate;
  /** 회원번호 */
  private int memberno;
  
  private int total_exercises;
  
  private String start_date;
  
  

}
