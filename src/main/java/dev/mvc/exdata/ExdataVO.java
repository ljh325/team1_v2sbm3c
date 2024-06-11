package dev.mvc.exdata;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//CREATE TABLE EXDATA(
//    EXDATANO                          NUMBER(10)     NOT NULL   PRIMARY KEY,
//    EXGROUP                           VARCHAR2(100)  NOT NULL,
//    EXNAME                            VARCHAR2(100)    NULL ,
//    MUSCLE                            VARCHAR2(100)    NULL ,
//    MUSCLESUB                         VARCHAR2(100)    NULL ,
//    EXLEVEL                           NUMBER(10)     NOT NULL,
//    LOWMET                            NUMBER(10)     NOT NULL,
//    MIDMET                            NUMBER(10)     NOT NULL,
//    HIGHMET                           NUMBER(10)     NOT NULL,
//    LOWACT                            NUMBER(10)     NOT NULL,
//    MIDACT                            NUMBER(10)     NOT NULL,
//    HIGHACT                           NUMBER(10)     NOT NULL,
//    LOWRISK                           NUMBER(10)     NOT NULL,
//    MIDRISK                           NUMBER(10)     NOT NULL,
//    HIGHRISK                          NUMBER(10)     NOT NULL
//);

@Getter @Setter @ToString
public class ExdataVO {
  /** 운동데이터번호 */
  private int exdatano;
  
  /** 운동근육그룹 */
  private String exgroup;
  
  /** 운동명 */
  private String exname;
  
  /** 주동근 */
  private String muscle;
  
  /** 협응근 */
  private String musclesub;
  
  /** 난이도 */
  private int exlevel;
  
  /** 낮은강도MET */
  private float lowmet;
  
  /** 중간강도MET */
  private float midmet;
  
  /** 높은강도MET */
  private float highmet;
  
  /** 낮은강도근육활성도 */
  private int lowact;
  
  /** 중간강도근육활성도 */
  private int midact;
  
  /** 높은강도근육활성도 */
  private int highact;
  
  /** 낮은강도부상위험도 */
  private int lowrisk;
  
  /** 중간강도부상위험도 */
  private int midrisk;
  
  /** 높은강도부상위험도 */
  private int highrisk;
  
  /** 효율 순위 */
  private int rank;
  
  /** 낮은강도 효율 */
  private int loweffect;
  
  /** 중간강도 효율 */
  private int mideffect;
  
  /** 높은강도 효율 */
  private int higheffect;
}
