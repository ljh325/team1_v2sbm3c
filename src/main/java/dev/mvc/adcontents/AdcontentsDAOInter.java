package dev.mvc.adcontents;

import java.util.ArrayList;
import java.util.HashMap;

public interface AdcontentsDAOInter {
  /**
   * 관리자 카테고리 게시글 생성
   * @param adcontentsVO
   * @return
   */
  public int create(AdcontentsVO adcontentsVO);
  
  /**
   * 모든 관리자 카테고리 게시글 목록
   * @return
   */
  public ArrayList<AdcontentsVO> list_all();
  
  /**
   * 특정 관리자 카테고리 게시글 목록
   * @param adcontentsno
   * @return
   */
  public ArrayList<AdcontentsVO> list_cate(int adcontentsno);
  
  /**
   * 특정 관리자 카테고리 등록된 게시글 갯수
   * @param cateno
   * @return
   */
  public int list_cate_count(int cateno);
  
  /**
   * 전체 관리자 카테고리 등록된 게시글 갯수
   * @return
   */
  public int list_all_count();
  
  /**
   * 게시글 조회
   * @param adcontentsno
   * @return
   */
  public AdcontentsVO read(int adcontentsno);
  
  /**
   * Youtube 등록, 수정, 삭제
   * @param hashmap
   * @return
   */
  public int youtube(HashMap<String, Object> hashmap);
  
  /**
   * 모든 등록된 게시글 검색 목록
   * @param hashmap
   * @return
   */
  public ArrayList<AdcontentsVO> list_all_search(HashMap<String, Object> hashmap);
  
  /**
   * 전체 게시글 검색 결과 수
   * @param hashmap
   * @return
   */
  public int list_all_search_count(HashMap<String, Object> hashmap);
  
  /**
   * 전체 검색 + 페이징 목록
   * @param hashmap
   * @return
   */
  public ArrayList<AdcontentsVO> list_all_search_paging(HashMap<String, Object>hashmap);
  
  /**
   * 카테고리별 검색 목록
   * @param hashmap
   * @return
   */
  public ArrayList<AdcontentsVO> list_cate_search(HashMap<String, Object> hashmap);
  
  /**
   * 카테고리별 검색 결과 수
   * @param hashmap
   * @return
   */
  public int list_cate_search_count(HashMap<String,Object> hashmap);
  
  /**
   * 카테고리별 검색 + 페이징 목록
   * @param hashmap
   * @return
   */
  public ArrayList<AdcontentsVO> list_cate_search_paging(HashMap<String, Object> hashmap);
  
  /**
   * 카테고리별 페이징 목록
   * @param hashmap
   * @return
   */
  public ArrayList<AdcontentsVO> list_cate_paging(HashMap<String, Object> hashmap);
  
  /**
   * 패스워드 검사
   * @param hashmap
   * @return
   */
  public int password_check(HashMap<String, Object> hashmap);
  
  /**
   * 글 수정
   * @param adcontentsVO
   * @return
   */
  public int update_text(AdcontentsVO adcontentsVO);
  
  /**
   * 파일 수정
   * @param adcontentsVO
   * @return
   */
  public int update_file(AdcontentsVO adcontentsVO);
  
  /**
   * 글 삭제
   * @param adcontentsno
   * @return
   */
  public int delete(int adcontentsno);
  
  /**
   * 특정 카테고리에 속한 모든 게시글 삭제
   * @param cateno
   * @return
   */
  public int delete_by_cate(int cateno);
  
  /**
   * 조회수 증가
   * @param adcontentsno
   * @return
   */
  public int view(int adcontentsno);
  
  /**
   * 추천수 증가
   * @param adcontentsno
   * @return
   */
  public int recom(int adcontentsno);
  
}
