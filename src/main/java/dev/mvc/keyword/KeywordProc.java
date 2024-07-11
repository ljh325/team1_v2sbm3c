package dev.mvc.keyword;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




@Component("dev.mvc.keyword.KeywordProc")
public class KeywordProc implements KeywordProcInter{
  
  @Autowired
  private KeywordDAOInter keywordDAO;

  @Override
  public int keyword_insert(KeywordVO keywordVO) {
    int cnt = this.keywordDAO.keyword_insert(keywordVO);
    return cnt;
  }

  @Override
  public ArrayList<KeywordVO> keyword_all() {
    ArrayList<KeywordVO> list = this.keywordDAO.keyword_all();
    return list;
  }

  @Override
  public KeywordVO keyword_read(int reviewno) {
    KeywordVO keywordVO = this.keywordDAO.keyword_read(reviewno);
    return keywordVO;
  }

  @Override
  public int keyword_delete(int reviewno) {
    int cnt = this.keywordDAO.keyword_delete(reviewno);
    return cnt;
  }

  @Override
  public ArrayList<KeywordVO> keyword_and_count() {
    ArrayList<KeywordVO> list = this.keywordDAO.keyword_and_count();
    return list;
  }

  
  

}
