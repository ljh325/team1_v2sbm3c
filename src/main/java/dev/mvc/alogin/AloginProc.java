package dev.mvc.alogin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component("dev.mvc.alogin.AloginProc")
public class AloginProc implements AloginProcInter {
  
  @Autowired
  private AloginDAOInter aloginDAO;
  
  public AloginProc(){
    System.out.println("-> AloginProc created.");
  }


  
  @Override
  public int alogin_insert(AloginVO aloginVO) {
    int cnt = this.aloginDAO.alogin_insert(aloginVO);
    return cnt;
  }

  @Override
  public ArrayList<AloginVO> alogin_read(int adminsno) {
    ArrayList<AloginVO> aloginVO = this.aloginDAO.alogin_read(adminsno);
    return aloginVO;
  }

}
