package dev.mvc.healthrecom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import dev.mvc.goals.GoalsVO;
import dev.mvc.mh.MhVO;

//@Component("dev.mvc.healthrecom.HealthrecomProc")
@Service("dev.mvc.healthrecom.HealthrecomProc")
public class HealthrecomProc implements HealthrecomProcInter {
  @Autowired
  private HealthrecomDAOInter healthrecomDAO;
  
  public HealthrecomProc() {
    // System.out.println("-> HealthrecomProc created.");  
  }
 


    



  @Override
  public int create(MhVO mhVO,GoalsVO goalsVO) {
    
    int mhno = mhVO.getMhno();
    int goals  = goalsVO.getGoalsno();
    String hrecom = "";

        /*gpt에게서 추천 내용을 가져오는 코드 
     * 
     */
   
    int cnt = this.healthrecomDAO.create(hrecom,goals,mhno); 
    return cnt;
  }







  @Override
  public ArrayList<HealthrecomVO> list_all(int memberno) {
    ArrayList<HealthrecomVO> list = this.healthrecomDAO.list_all(memberno);
    return list;
  }







  @Override
  public HealthrecomVO read(int healthrecomno) {
    HealthrecomVO healthrecomVO = this.healthrecomDAO.read(healthrecomno);
    return healthrecomVO;
  }













  @Override
  public int delete(int healthrecomno) {
    int cnt = this.healthrecomDAO.delete(healthrecomno);// TODO Auto-generated method stub
    return cnt;
  }







  
}
