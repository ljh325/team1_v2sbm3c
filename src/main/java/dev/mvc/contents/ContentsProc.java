package dev.mvc.contents;

import java.util.ArrayList;
import java.util.HashMap;

public class ContentsProc implements ContentsProcInter {

  @Override
  public int create(ContentsVO contentsVO) {
    
    return 0;
  }

  @Override
  public ArrayList<ContentsVO> list_all() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<ContentsVO> list_cate(int cateno) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int list_cate_count(int cateno) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public ContentsVO read(int contentsno) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int youtube(HashMap<String, Object> hashmap) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public ArrayList<ContentsVO> list_cate_search(HashMap<String, Object> hashmap) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int list_cate_search_count(HashMap<String, Object> hashmap) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public ArrayList<ContentsVO> list_cate_search_paging(HashMap<String, Object> hashmap) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int password_check(HashMap<String, Object> hashmap) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int update_text(ContentsVO contentsVO) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int update_file(ContentsVO contentsVO) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int delete(int contentsno) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int delete_by_cate(int cateno) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int count_by_member(int memberno) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int delete_by_member(int memberno) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String pagingBox(int cateno, int now_page, String word, String list_file, int search_count,
      int record_per_page, int page_per_block) {
    // TODO Auto-generated method stub
    return null;
  }
  
  

}
