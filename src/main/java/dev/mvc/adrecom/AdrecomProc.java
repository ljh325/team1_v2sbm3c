package dev.mvc.adrecom;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("dev.mvc.adrecom.AdrecomProc")
public class AdrecomProc implements AdrecomProcInter {

  @Autowired
  private AdrecomDAOInter adrecomDAO;
  
  @Override
  public int create(AdrecomVO adrecomVO) {
    int cnt = this.adrecomDAO.create(adrecomVO);
    return cnt;
  }

  @Override
  public int adrecom_read(HashMap<String, Object> hashmap) {
    int cnt = this.adrecomDAO.adrecom_read(hashmap);
    return cnt;
  }

  @Override
  public AdrecomVO adrecom_check(HashMap<String, Object> hashmap) {
    AdrecomVO adrecomVO = this.adrecom_check(hashmap);
    return adrecomVO;
  }

  @Override
  public ArrayList<AdrecomVO> member_read(int memberno) {
    ArrayList<AdrecomVO> list =  this.adrecomDAO.member_read(memberno);
    return list;
  }

  @Override
  public ArrayList<AdrecomVO> adcontents_read(int adcontentsno) {
    ArrayList<AdrecomVO> list =  this.adrecomDAO.adcontents_read(adcontentsno);
    return list;
  }

  @Override
  public int adrecom(HashMap<String, Object> hashmap) {
    int cnt = this.adrecomDAO.adrecom(hashmap);
    return cnt;
  }

  @Override
  public int adrecom_cancel(HashMap<String, Object> hashmap) {
    int cnt = this.adrecomDAO.adrecom_cancel(hashmap);
    return cnt;
  }

}
