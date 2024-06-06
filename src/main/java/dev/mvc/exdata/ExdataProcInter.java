package dev.mvc.exdata;

import java.util.ArrayList;

public interface ExdataProcInter {
  /**
   * 운동 데이터 생성
   * @param exdataVO
   * @return
   */
  public int create(ExdataVO exdataVO);
  
  /**
   *  전체 운동 데이터 목록
   * @return
   */
  public ArrayList<ExdataVO> list_all();
  
  /**
   * 낮은 강도 소모칼로리 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_lowmet_asc();
  
  /**
   * 낮은 강도 소모칼로리 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_lowmet_desc();
  
  /**
   * 중간강도 소모칼로리 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_midmet_asc();
  
  /**
   * 중간강도 소모칼로리 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_midmet_desc();
  
  /**
   * 높은강도 소모칼로리 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_highmet_asc();
  
  /**
   * 높은강도 소모칼로리 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_highmet_desc();
  
  /**
   * 낮은강도 근육활성도 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_lowact_asc();
  
  /**
   * 낮은강도 근육활성도 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_lowact_desc();
  
  /**
   * 중간강도 근육활성도 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_midact_asc();
  
  /**
   * 중간강도 근육활성도 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_midact_desc();
  
  /**
   * 높은강도 근육활성도 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_highact_asc();
  
  /**
   * 높은강도 근육활성도 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_highact_desc();
  
  /**
   * 낮은강도 부상위험도 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_lowrisk_asc();
  
  /**
   * 낮은강도 부상위험도 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_lowrisk_desc();
  
  /**
   * 중간강도 부상위험도 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_midrisk_asc();
  
  /**
   * 중간강도 부상위험도 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_midrisk_desc();
  
  /**
   * 높은강도 부상위험도 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_highrisk_asc();
  
  /**
   * 높은강도 부상위험도 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_all_highrisk_desc();
  
//  ---------------------------부위별 정렬-----------------------------------------------
  /**
   *  부위별 운동 데이터 목록
   * @return
   */
  public ArrayList<ExdataVO> list_part(String exgroup);
  
  /**
   * 부위별 낮은 강도 소모칼로리 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_lowmet_asc(String exgroup);
  
  /**
   * 부위별 낮은 강도 소모칼로리 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_lowmet_desc(String exgroup);
  
  /**
   * 부위별 중간강도 소모칼로리 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_midmet_asc(String exgroup);
  
  /**
   * 부위별 중간강도 소모칼로리 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_midmet_desc(String exgroup);
  
  /**
   * 부위별 높은강도 소모칼로리 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_highmet_asc(String exgroup);
  
  /**
   * 부위별 높은강도 소모칼로리 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_highmet_desc(String exgroup);
  
  /**
   * 부위별 낮은강도 근육활성도 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_lowact_asc(String exgroup);
  
  /**
   * 부위별 낮은강도 근육활성도 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_lowact_desc(String exgroup);
  
  /**
   * 부위별 중간강도 근육활성도 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_midact_asc(String exgroup);
  
  /**
   * 부위별 중간강도 근육활성도 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_midact_desc(String exgroup);
  
  /**
   * 부위별 높은강도 근육활성도 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_highact_asc(String exgroup);
  
  /**
   * 부위별 높은강도 근육활성도 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_highact_desc(String exgroup);
  
  /**
   * 부위별 낮은강도 부상위험도 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_lowrisk_asc(String exgroup);
  
  /**
   * 부위별 낮은강도 부상위험도 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_lowrisk_desc(String exgroup);
  
  /**
   * 부위별 중간강도 부상위험도 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_midrisk_asc(String exgroup);
  
  /**
   * 부위별 중간강도 부상위험도 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_midrisk_desc(String exgroup);
  
  /**
   * 부위별 높은강도 부상위험도 오름차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_highrisk_asc(String exgroup);
  
  /**
   * 부위별 높은강도 부상위험도 내림차순 정렬
   * @return
   */
  public ArrayList<ExdataVO> list_part_highrisk_desc(String exgroup);
  
}
