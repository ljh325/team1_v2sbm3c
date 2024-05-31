package dev.mvc.healthrecom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import dev.mvc.goals.GoalsVO;
import dev.mvc.mh.MhVO;
import dev.mvc.foodcate.FoodCateVO;

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
     * gpt에게서 해당하는 식품코드들 또한 가져온다 
     */
    String input = "1323456,21313,113313,1313133";

    // 1. 문자열을 쉼표로 분리하기
    String[] stringNumbers = input.split(",");

    // 2. 문자열 배열을 정수형 배열로 변환하기
    int[] intNumbers = new int[stringNumbers.length];
    for (int i = 0; i < stringNumbers.length; i++) {
        intNumbers[i] = Integer.parseInt(stringNumbers[i]);
    }

    // 결과 출력 (확인용)
    for (int number : intNumbers) {
      
        System.out.println(number);
    }
    
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


  @Override
  public int delete_g(int goalsno){
    int cnt = this.healthrecomDAO.delete_g(goalsno);// TODO Auto-generated method stub
    return cnt;
  }
  
  @Override
  public int delete_m(int mhno){
    int cnt = this.healthrecomDAO.delete_m(mhno);// TODO Auto-generated method stub
    return cnt; 
  }



  
}
