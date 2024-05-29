package dev.mvc.adcontents;

import java.util.ArrayList;
import java.util.HashMap;

public interface AdcontentsProcInter {
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
  
  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param cateno          카테고리번호 
   * @param now_page      현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @param search_count 검색 레코드수   
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int cateno, int now_page, String word, String list_file, int search_count, int record_per_page,
      int page_per_block);
  
  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param cateno          카테고리번호 
   * @param now_page      현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @param search_count 검색 레코드수   
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox_all(int now_page, String word, String list_file, int search_count, int record_per_page,
      int page_per_block);
}
