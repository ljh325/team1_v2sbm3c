package dev.mvc.mh;

import java.util.ArrayList;
import java.util.Map;

public interface MhDAOInter {
  /**
   * 등록
   * insert id="create" parameterType="dev.mvc.mh.MhVO"
   * @param mhVO
   * @return 등록한 레코드 갯수
   */
  public int create(MhVO mhVO);
  
  /**
   * 전체 목록
   * select id="list_all" resultType="dev.mvc.mh.MhVO"     
   * @return 레코드 목록
   */
  public ArrayList<MhVO> list_all(int memberno);
  
  /**
   * 조회
   * select id="read" resultType="dev.mvc.mh.MhVO" parameterType="int"
   * @param mhno
   * @return
   */
  public MhVO read(int mhno);
  
  /**
   * 수정
   * update id="update" parameterType="dev.mvc.mh.MhVO"    
   * @param mhVO
   * @return 수정된 레코드 갯수
   */
  public int update(MhVO mhVO);
  
  /**
   * delete
   * delete id="delete" parameterType="Integer"
   * @param mhno
   * @return
   */
  public int delete(int mhno);

  /**
   * 관리자용 검색 목록
   * select id="list_search" resultType="dev.mvc.mh.MhVO" parameterType="String"
   * @param map
   * @return 조회한 레코드 목록
   */
  public ArrayList<MhVO> list_search(String word);    
      
  /**
   * 검색목록 페이징
   * select id="list_search_paging" resultType="dev.mvc.mh.MhVO" parameterType="Map"
   * @param map
   * @return 조회한 레코드 목록
   */
  public ArrayList<MhVO> list_search_paging(Map<String, Object> map);
  
  /**
   * 검색된 레코드 수
   * @param word
   * @return
   */
  public int list_search_count(String word);
  
}








