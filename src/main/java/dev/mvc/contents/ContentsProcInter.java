package dev.mvc.contents;

import java.util.ArrayList;
import java.util.HashMap;

public interface ContentsProcInter {
  /**
   * 게시글 등록 
   * @param contentsVO
   * @return 등록된 게시글 갯수
   */
  public int create(ContentsVO contentsVO);
  
  /**
   * 모든 등록된 게시글 목록
   * @return 게시글 목록
   */
  public ArrayList<ContentsVO> list_all();
  
  /**
   * 특정 카테고리 게시글 목록
   * @param cateno
   * @return 게시글 목록
   */
  public ArrayList<ContentsVO> list_cate(int cateno);
  
  /**
   * 특정 카테고리 등록된 게시글 갯수
   * @param cateno
   * @return 등록된 게시글 갯수
   */
  public int list_cate_count(int cateno);
  
  /**
   * 전체 등록된 게시글 갯수
   * @param cateno
   * @return 등록된 게시글 갯수
   */
  public int list_all_count();
  
  /**
   * 게시글 조회
   * @param contentsno
   * @return 게시글 내용
   */
  public ContentsVO read(int contentsno);
  
  /**
   * Youtube 등록/수정/삭제
   * @param hashmap
   * @return  등록/수정/삭제된 갯수
   */
  public int youtube(HashMap<String, Object> hashmap);
  
  /**
   * 게시글 전체 검색 목록
   * @param hashmap
   * @return 검색된 게시글 목록
   */
  public ArrayList<ContentsVO> list_all_search(HashMap<String, Object> hashmap);
  
  /**
   * 게시글 전체 검색 결과 수
   * @param hashmap
   * @return 검색 결과 수
   */
  public int list_all_search_count(HashMap<String, Object> hashmap);
  
  /**
   * 게시글 전체 검색 + 페이징 목록
   * @param hashmap
   * @return 검색된 게시글 목록
   */
  public ArrayList<ContentsVO> list_all_search_paging(HashMap<String, Object> hashmap);
  
  /**
   * 카테고리별 검색 목록
   * @param hashmap
   * @return 검색된 게시글 목록
   */
  public ArrayList<ContentsVO> list_cate_search(HashMap<String, Object> hashmap);
  
  /**
   * 카테고리별 검색 결과 수
   * @param hashmap
   * @return 검색 결과 수
   */
  public int list_cate_search_count(HashMap<String, Object> hashmap);
  
  /**
   * 카테고리별 검색 + 페이징 목록
   * @param hashmap
   * @return 검색된 게시글 목록
   */
  public ArrayList<ContentsVO> list_cate_search_paging(HashMap<String, Object> hashmap);
  
  /**
   * 패스워드 검사
   * @param hashmap
   * @return 패스워드 일치여부
   */
  public int password_check(HashMap<String, Object> hashmap);
  
  /**
   * 글 수정
   * @param contentsVO
   * @return 글 수정된 갯수
   */ 
  public int update_text(ContentsVO contentsVO);
  
  /**
   * 파일 수정
   * @param contentsVO
   * @return 파일 수정된 갯수
   */
  public int update_file(ContentsVO contentsVO);
  
  /**
   * 글 삭제
   * @param contentsno
   * @return 삭제된 글 갯수
   */
  public int delete(int contentsno);
  
  /**
   *  특정 카테고리에 속한 모든 게시글 삭제
   * @param cateno
   * @return 게시글 삭제 여부
   */
  public int delete_by_cate(int cateno);
  
  /**
   * 회원이 작성한 게시글 갯수
   * @param memberno
   * @return 작성한 게시글 갯수
   */
  public int count_by_member(int memberno);
  
  /**
   *  회원이 작성한 모든 게시글 삭제
   * @param memberno
   * @return 게시글 삭제 여부
   */
  public int delete_by_member(int memberno);
  
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
  
  /**
   * 조회수 증가
   * @param contentsno
   * @return 조회수 증가 여부
   */
  public int view(int contentsno);
  
  /**
   * 추천수 증가
   * @param contentsno
   * @return 추천수 증가 여부
   */
  public int recom(int contentsno);

}
