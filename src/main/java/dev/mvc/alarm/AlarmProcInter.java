package dev.mvc.alarm;

import java.util.ArrayList;

public interface AlarmProcInter {
  /**
   * 알림 등록
   * @param alarmVO
   * @return
   */
  public int create(AlarmVO alarmVO);
  
  /**
   * 회원 전체 알림 목록
   * @return
   */
  public ArrayList<AlarmPatchVO> list_all();
  
  /**
   * 회원별 알림 목록
   * @param memberno
   * @return
   */
  public ArrayList<AlarmPatchVO> alarm_list(int memberno);
  
  /**
   * 회원별 알림 갯수
   * @param memberno
   * @return
   */
  public int alarm_cnt(int memberno);
  
  /**
   * 알림 제거
   * @param alarmno
   * @return
   */
  public int delete(int alarmno);
}
