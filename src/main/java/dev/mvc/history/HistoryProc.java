package dev.mvc.history;

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
  public int history_start(HistoryVO historyVO) {
    int cnt = this.historyDAO.history_start(historyVO);
    return cnt;
  }

  @Override
  public int history_end(int memberno) {
    int cnt = this.historyDAO.history_end(memberno); 
    return cnt;
  }

  @Override
  public HistoryVO history_read(int memberno) {
    HistoryVO historyVO = this.history_read(memberno);
    return historyVO;
  }
  
  

}
