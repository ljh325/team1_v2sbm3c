package dev.mvc.alarm;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.alarm.AlarmProc")
public class AlarmProc implements AlarmProcInter {
  
  @Autowired
  private AlarmDAOInter alarmDAO;
  
  @Override
  public int create(AlarmVO alarmVO) {
    int cnt = this.alarmDAO.create(alarmVO);
    return cnt;
  }

  @Override
  public ArrayList<AlarmPatchVO> list_all() {
    ArrayList<AlarmPatchVO> list =  this.alarmDAO.list_all();
    return list;
  }

  @Override
  public ArrayList<AlarmPatchVO> alarm_list(int memberno) {
    ArrayList<AlarmPatchVO> list = this.alarmDAO.alarm_list(memberno);
    return list;
  }

  @Override
  public int alarm_cnt(int memberno) {
    int cnt = this.alarmDAO.alarm_cnt(memberno);
    return cnt;
  }

  @Override
  public int delete(int alarmno) {
    int cnt = this.alarmDAO.delete(alarmno);
    return cnt;
  }

}
