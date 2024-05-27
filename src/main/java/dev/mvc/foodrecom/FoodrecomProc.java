package dev.mvc.foodrecom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import dev.mvc.goals.GoalsVO;
import dev.mvc.mh.MhVO;

//@Component("dev.mvc.foodrecom.FoodrecomProc")
@Service("dev.mvc.foodrecom.FoodrecomProc")
public class FoodrecomProc implements FoodrecomProcInter {
  @Autowired
  private FoodrecomDAOInter foodrecomDAO;
  
  public FoodrecomProc() {
    // System.out.println("-> FoodrecomProc created.");  
  }
 


    



  @Override
  public int create(MhVO mhVO,GoalsVO goalsVO) {
    
    int mhno = mhVO.getMhno();
    int goals  = goalsVO.getGoalsno();
    String frecom = "";

        /*gpt에게서 추천 내용을 가져오는 코드 
     * 
     */
   
    int cnt = this.foodrecomDAO.create(frecom,goals,mhno); 
    return cnt;
  }







  @Override
  public ArrayList<FoodrecomVO> list_all(int memberno) {
    ArrayList<FoodrecomVO> list = this.foodrecomDAO.list_all(memberno);
    return list;
  }







  @Override
  public FoodrecomVO read(int foodrecomno) {
    FoodrecomVO foodrecomVO = this.foodrecomDAO.read(foodrecomno);
    return foodrecomVO;
  }













  @Override
  public int delete(int foodrecomno) {
    int cnt = this.foodrecomDAO.delete(foodrecomno);// TODO Auto-generated method stub
    return cnt;
  }







  
}
