package dev.mvc.adreply;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.adreply.AdreplyProc")
public class AdreplyProc implements AdreplyProcInter {

  @Autowired
  private AdreplyDAOInter adreplyDAO;
  
  public AdreplyProc(){
    System.out.println("-> AdreplyProc created.");
  }
  
  @Override
  public int create(AdreplyVO adreplyVO) {
    int cnt = this.adreplyDAO.create(adreplyVO);
    return cnt;
  }

  @Override
  public AdreplyVO admin_read(int reviewno) {
    AdreplyVO adreplyVO = this.adreplyDAO.admin_read(reviewno);
    return adreplyVO;
  }

  @Override
  public int all_count() {
    int cnt = this.adreplyDAO.all_count();
    return cnt;
  }

  @Override
  public int update(AdreplyVO adreplyVO) {
      int cnt = this.adreplyDAO.update(adreplyVO);
    return cnt;
  }

  @Override
  public int delete(int reviewno) {
    int cnt = this.adreplyDAO.delete(reviewno);
    return cnt;
  }

}
