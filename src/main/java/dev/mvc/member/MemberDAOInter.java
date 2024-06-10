package dev.mvc.member;

import java.util.ArrayList;
import java.util.HashMap;

import dev.mvc.contents.ContentsVO;

public interface MemberDAOInter {
  /**
   * 중복 아이디 검사
   * @param id
   * @return 중복 아이디 갯수
   */
  public int checkID(String id);
  
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
   * 수정 처리
   * @param memberVO
   * @return
   */
  public int update(MemberVO memberVO);
  
  /**
   * 파일 정보 수정
   * @param memberVO
   * @return 처리된 레코드 갯수
   */
  public int update_profile(MemberVO memberVO);
 
  /**
   * 회원 삭제 처리
   * @param memberno
   * @return
   */
  public int delete(int memberno);
  
  /**
   * 로그인 처리
   * @param map
   * @return
   */
  public int login(HashMap<String, Object> map);
  
  /**
   * 아이디 찾기
   * @param map
   * @return
   */
  public ArrayList<MemberVO> find_id(MemberVO memberVO);
  
  /**
   * 비밀번호 찾기
   * @param map
   * @return
   */
  public MemberVO find_passwd(HashMap<String, Object> map);
  
}
