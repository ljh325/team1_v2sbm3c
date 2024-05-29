package dev.mvc.foodcate;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.mvc.tool.Security;

@Component("dev.mvc.foodcate.FoodCateProc")
public class FoodCateProc implements FoodCateProcInter {

  @Autowired 
  private FoodCateDAOInter foodCateDAO;
  
  @Autowired
  Security security;
  
  @Override
  public int create(FoodCateVO foodCateVO) {
    int cnt = this.foodCateDAO.create(foodCateVO);
    return cnt;
  }

  @Override
  public ArrayList<FoodCateVO> list_all() {
    ArrayList<FoodCateVO> list = this.foodCateDAO.list_all();
    return list;
  }
  @Override
  public int delete(int foodcateno) {
    int cnt = this.foodCateDAO.delete(foodcateno);
    return cnt;
  }

}
