package dev.mvc.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;  // 구현 클래스를 교체하기 쉬운 구조 지원

import dev.mvc.contents.ContentsVO;
// import javax.servlet.http.HttpSession; // Spring Boot ~ 2.9
import jakarta.servlet.http.HttpSession; //  Spring Boot 3.0~

public interface MemberProcInter {
  /**
   * 중복 아이디 검사
   * @param id
   * @return 중복 아이디 갯수
   */
  public int checkID(String id);
  
  /**
   * 중복 닉네임 검사
   * @param nickname
   * @return
   */
  public int nickCheck(String nickname);
  
  /**
   * 회원 가입
   * @param memberVO
   * @return 추가한 레코드 갯수
   */
  public int create(MemberVO memberVO);

  /**
   * 회원 전체 목록
   * @return
   */
  public ArrayList<MemberVO> list();

  /**
   * memberno로 회원 정보 조회
   * @param memberno
   * @return
   */
  public MemberVO read(int memberno);
  
  /**
   * id로 회원 정보 조회
   * @param id
   * @return
   */
  public MemberVO readById(String id);

  
  /**
   * 로그인된 회원 계정인지 검사합니다.
   * @param session
   * @return true: 사용자
   */
  public boolean isMember(HttpSession session);

  /**
   * 로그인된 회원 관리자 계정인지 검사합니다.
   * @param session
   * @return true: 사용자
   */
  public boolean isMemberAdmin(HttpSession session);
  
  /**
   * 신규 등록자 수
   * @return
   */
  public int new_user_count();
  
  /**
   * 일반회원 전체 수
   * @return
   */
  public int user_count_normal();
  
  
  /**
   * 수정 처리
   * @param memberVO
   * @return
   */
  public int update(MemberVO memberVO);
  
  /**
   * 프로필 사진 수정 및 소개글 닉네임 수정
   * @param memberVO
   * @return
   */
  public int profile_update_proc(MemberVO memberVO);
  
  /**
   * 소개글 닉네임 수정
   * @param memberVO
   * @return
   */
  public int profile_nickIntro(MemberVO memberVO);
  
  /**
   * 파일 정보 수정
   * @param memberVO
   * @return 처리된 레코드 갯수
   */
  public int update_profile(MemberVO memberVO);
  
  /**
   * 관리자 권환 회원등급 변경
   * @param memberVO
   * @return
   */
  public int grade_update(MemberVO memberVO);
 
  /**
   * 회원 삭제 처리
   * @param memberno
   * @return
   */
  public int delete(int memberno);
  
  /**
   * 로그인 처리
   */
  public int login(HashMap<String, Object> map);
  
  /**
   * 아이디 찾기
   * @param memberVO
   * @return
   */
  public ArrayList<MemberVO> find_id(MemberVO memberVO);
  
  /**
   * 비밀번호 찾기
   * @param memberVO
   * @return
   */
  public MemberVO find_passwd(MemberVO memberVO);
  
  
  /* ============================================================================= */
  /* ============================================================================= */
  /* 검색 및 페이징------------------------------------------------------------ */
  
  /**
   * 이름, 아이디별 검색 목록
   * @param map
   * @return
   */
  public ArrayList<MemberVO> list_by_search(HashMap<String, Object> hashMap);
  
  /**
   * 이름, 아이디별 검색된 레코드 갯수
   * @param map
   * @return
   */
  public int list_by_search_count(HashMap<String, Object> hashMap);
  
  /**
   * 이름, 아이디별 검색 목록 + 페이징
   * @param contentsVO
   * @return
   */
  public ArrayList<MemberVO> list_by_search_paging(HashMap<String, Object> map);
  
  /**
   * 등급별 검색된 레코드 갯수
   * @param hasMap
   * @return
   */
  public int grade_list_by_search_count(HashMap<String, Object> hasMap);
  
  /**
   * 등급 별 검색 + 페이징
   * @param map
   * @return
   */
  public ArrayList<MemberVO> grade_list_by_search_paging(HashMap<String, Object> map);
  
  /* ============================================================================= */
  /** 
   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
   *
   * @param now_page 현재 페이지
   * @param word 검색어
   * @param list_file 목록 파일명
   * @param search_count 검색 레코드수   
   * @param record_per_page 페이지당 레코드 수
   * @param page_per_block 블럭당 페이지 수
   * @return 페이징 생성 문자열
   */ 
  public String pagingBox(int now_page, String word, String list_file, int search_count, 
                                      int record_per_page, int page_per_block);   

  /* ============================================================================= */
  /* ============================================================================= */
  /* ============================================================================= */
}




