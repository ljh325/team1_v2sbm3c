package dev.mvc.history;

import java.util.ArrayList;


public interface HistoryDAOInter {
  
  /**
   * 운동 시작 체크
   * @param historyVO
   * @return 추가한 레코드 갯수
   */
  public int history_start(HistoryVO historyVO);
  
  /**
   * 운동 시작 체크
   * @param historyVO
   * @return 추가한 레코드 갯수
   */
  public int history_end(int memberno);
  
  /**
   * memberno로 운동 내역 조회
   * @param memberno
   * @return 운동 내역 + 총 토탈시간
   */
  public HistoryVO history_read(int memberno);

}
