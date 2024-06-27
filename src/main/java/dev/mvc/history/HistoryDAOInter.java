package dev.mvc.history;


import java.util.ArrayList;
import java.util.HashMap;


public interface HistoryDAOInter {
  
  /**
   * 운동기록 등록 
   * @param historyVO
   * @return
   */
  public int insert_history(HistoryVO historyVO);
  
  
  /**
   * 회원별 운동기록 리스트조회
   * @param map
   * @return
   */
  public ArrayList<HistoryVO> read_history(HistoryVO historyVO);
  
  /**
   * 회원별 운동기록 상세 목록조회
   * @param map
   * @return
   */
  public HistoryVO record_read(HashMap<String, Object> map);
  
  /**
   * 회원별 + 날짜별 총 운동 시간(time)
   * @param memberno
   * @return
   */
  public HistoryVO time_for_history(int memberno);
  
  /**
   * 회원별 + 날짜별 총 운동 횟수(count)
   * @param memberno
   * @return
   */
  public ArrayList<HistoryVO> count_history(int memberno);
  
  /**
   * 회원 운동 기록 전체 시간
   * @param memberno
   * @return
   */
  public int all_count(int memberno);
  
  /**
   * 운동기록번호의 최댓값
   * @return
   */
  public int exrecordno_max();
  
  /**
   * 운동 기록 수정
   * @param historyVO
   * @return
   */
  public int history_update(HistoryVO historyVO);
  
  /**
   * 운동 기록 부분 삭제
   * @param map
   * @return
   */
  public int delete_sectoin_history(HashMap<String, Object> map);
  
  /**
   * 날짜별 운동 기록 삭제
   * @param map
   * @return
   */
  public int delete_date_history(HashMap<String, Object> map);
  
  

}
