package dev.mvc.keyword;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class KeywordVO {

  
//======================= 주찬 ========================//
  
//  KEYWORDNO                         NUMBER(10)     NOT NULL    PRIMARY KEY,
//  KEYWORDNAME                       VARCHAR2(100)  NOT NULL,
//  WORD                              VARCHAR2(100)  NOT NULL,
//  REVIEWNO                          NUMBER(10)  
  
  /* 키워드 번호 */
  private int keywordno;
  /* 키워드 이름 */
  private String keywordname;
  /* 키워드 단어 */
  private String  word;
  /* 리뷰 번호*/
  private int reviewno;

  
  
  
  
}
