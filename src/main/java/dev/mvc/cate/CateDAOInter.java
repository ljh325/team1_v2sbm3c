package dev.mvc.cate;

import java.util.ArrayList;
import java.util.Map;

public interface CateDAOInter {
  
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
  public ArrayList<CateVO> list_search_paging(Map<String, Object> map);
  
  /**
   * 검색된 레코드 수
   * @param word
   * @return 검색된 레코드 갯수
   */
  public int list_search_count(String word);
  
}
