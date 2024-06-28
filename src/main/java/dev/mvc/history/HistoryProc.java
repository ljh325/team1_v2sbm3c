package dev.mvc.history;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component("dev.mvc.history.HistoryProc")
public class HistoryProc implements HistoryProcInter{
  
  @Autowired
  private HistoryDAOInter historyDAO;
  
  public HistoryProc(){
    System.out.println("-> HistoryProc created.");
  }

  @Override
  public int insert_history(HistoryVO historyVO) {
    int cnt = this.historyDAO.insert_history(historyVO);
    return cnt;
  }

  @Override
  public ArrayList<HistoryVO> read_history(HistoryVO historyVO) {
    ArrayList<HistoryVO> list = this.historyDAO.read_history(historyVO);
    return list;
  }

  @Override
  public HistoryVO time_for_history(int memberno) {
    HistoryVO historyVO = this.historyDAO.time_for_history(memberno);
    return historyVO;
  }

  @Override
  public ArrayList<HistoryVO> count_history(int memberno) {
    ArrayList<HistoryVO> historyVO = this.historyDAO.count_history(memberno);
    return historyVO;
  }

  @Override
  public int all_count(int memberno) {
    int cnt = this.historyDAO.all_count(memberno);
    return cnt;
  }

  @Override
  public int history_update(HistoryVO historyVO) {
    int cnt = this.historyDAO.history_update(historyVO);
    return cnt;
  }

  @Override
  public int delete_sectoin_history(HashMap<String, Object> map) {
    int cnt = this.historyDAO.delete_sectoin_history(map);
    return cnt;
  }

  @Override
  public int delete_date_history(HashMap<String, Object> map) {
    int cnt = this.historyDAO.delete_date_history(map);
    return cnt;
  }

  @Override
  public HistoryVO record_read(HashMap<String, Object> map) {
    HistoryVO historyVO = this.historyDAO.record_read(map);
    return historyVO;
  }

  @Override
  public int exrecordno_max() {
    int max = this.historyDAO.exrecordno_max();
    return max;
  }

  @Override
  public int ex_cnt(int exrecordno) {
    int cnt = this.historyDAO.ex_cnt(exrecordno);
    return cnt;
  }

  
  

  

}
