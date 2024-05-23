package dev.mvc.cate;

import java.util.ArrayList;

public interface CateProcInter {
  
  /**
   * 카테고리 생성
   * @param cateVO
   * @return 등록된 카테고리 수
   */
  public int create(CateVO cateVO);
  
  /**
   * 카테고리 전체보기
   * @return 카테고리 목록
   */
  public ArrayList<CateVO> list_all();
  
  /**
   * 카테고리 조회
   * @param cateno
   * @return
   */
  public CateVO read(int cateno);
  
  /**
   * 카테고리 내용 수정
   * @param cateVO
   * @return 수정된 카테고리 수
   */
  public int update(CateVO cateVO);
  
  /**
   * 카테고리 제거
   * @param cateno
   * @return 제거된 카테고리 수
   */
  public int delete(int cateno);
  
  /**
   * 카테고리 자료수 증가
   * @param cateno
   * @return 증가한 자료 수
   */
  public int cate_count_increase(int cateno);
  
  /**
   * 카테고리 자료수 감소
   * @param cateno
   * @return 감소한 자료 수
   */
  public int cate_count_decrease(int cateno);
  
  /**
   * 카테고리 전체 자료수
   * @return 전체 자료수
   */
  public int cate_count_read();
  
  /**
   * 카테고리 출력 순서 높임
   * @param cateno
   * @return 출력 순서 높아진 카테고리 수
   */
  public int update_seqno_forward(int cateno);
  
  /**
   * 카테고리 출력 순서 낮춤
   * @param cateno
   * @return 출력 순서 낮아진 카테고리 수
   */
  public int update_seqno_backward(int cateno);
  
  /**
   * 카테고리 공개 설정
   * @param cateno
   * @return 공개된 카테고리 수
   */
  public int update_visible_y(int cateno);
  
  /** 
   * 카테고리 비공개 설정
   * @param cateno
   * @return 비공개된 카테고리 수
   */
  public int update_visible_n(int cateno);
  
  /**
   * 카테고리 관리자 전용 설정
   * @param cateno
   * @return 설정된 카테고리 수
   */
  public int update_admins_y(int cateno);
  
  /** 
   * 카테고리 관리자 전용 설정 해제
   * @param cateno
   * @return 설정된 카테고리 수
   */
  public int update_admins_n(int cateno);
  
  /**
   * 회원/비회원에게 공개할 중분류 목록
   * @return 중분류 목록
   */
  public ArrayList<CateVO> list_all_name_y();
  
  /**
   * 회원/비회원에게 공개할 소분류 목록
   * @param name
   * @return 소분류 목록
   */
  public ArrayList<CateVO> list_all_namesub_y(String name);
  
  /**
   * 관리자용 검색 목록
   * @param word
   * @return 검색 결과
   */
  public ArrayList<CateVO> list_search(String word);
  
  /**
   * 관리자용 검색 목록 페이징
   * @param map
   * @return 검색 결과
   */
  public ArrayList<CateVO> list_search_paging(String word, int now_page, int record_per_page);
  
  /**
   * 검색된 레코드 수
   * @param word
   * @return 검색된 레코드 갯수
   */
  public int list_search_count(String word);
  
  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param now_page  현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @param search_count 검색 레코드수   
   * @param record_per_page 페이지당 레코드 수
   * @param page_per_block 블럭당 페이지 수
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int now_page, String word, String list_file, int search_count, 
                                      int record_per_page, int page_per_block); 
  
  /** 메뉴 */
  public ArrayList<CateVOMenu> menu();
}
