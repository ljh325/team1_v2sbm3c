package dev.mvc.recom;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.recom.RecomProc")
public class RecomProc implements RecomProcInter {
  
  @Autowired
  private RecomDAOInter recomDAO;
  
  @Override
  public int create(RecomVO recomVO) {
    int cnt = this.recomDAO.create(recomVO);
    return cnt;
  }
  

  @Override
  public int recom_read(HashMap<String, Object> hashmap) {
    int cnt = this.recomDAO.recom_read(hashmap);
    return cnt;
  }
  


  @Override
  public RecomVO recom_check(HashMap<String, Object> hashmap) {
    RecomVO recomVO = this.recomDAO.recom_check(hashmap);
    return recomVO;
  }


  @Override
  public ArrayList<RecomVO> member_read(int memberno) {
    ArrayList<RecomVO> list = this.recomDAO.member_read(memberno);
    return list;
  }

  @Override
  public ArrayList<RecomVO> contents_read(int contentsno) {
    ArrayList<RecomVO> list = this.recomDAO.contents_read(contentsno);
    return list;
  }

  @Override
  public int recom(HashMap<String, Object> hashmap) {
    int cnt = this.recomDAO.recom(hashmap);
    return cnt;
  }

  @Override
  public int recom_cancel(HashMap<String, Object> hashmap) {
    int cnt = this.recomDAO.recom_cancel(hashmap);
    return cnt;
  }

}
