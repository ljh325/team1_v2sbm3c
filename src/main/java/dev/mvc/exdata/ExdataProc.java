package dev.mvc.exdata;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.exdata.ExdataProc")
public class ExdataProc implements ExdataProcInter {

  @Autowired
  private ExdataDAOInter exdataDAO;

  @Override
  public int create(ExdataVO exdataVO) {
    int cnt = this.exdataDAO.create(exdataVO);
    return cnt;
  }

  @Override
  public ArrayList<ExdataVO> list_all() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_lowmet_asc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_lowmet_asc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_lowmet_desc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_lowmet_desc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_midmet_asc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_midmet_asc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_midmet_desc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_midmet_desc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_highmet_asc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_highmet_asc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_highmet_desc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_highmet_desc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_lowact_asc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_lowact_asc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_lowact_desc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_lowact_desc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_midact_asc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_midact_asc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_midact_desc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_midact_desc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_highact_asc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_highact_asc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_highact_desc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_highact_desc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_lowrisk_asc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_lowrisk_asc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_lowrisk_desc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_lowrisk_desc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_midrisk_asc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_midrisk_asc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_midrisk_desc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_midrisk_desc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_highrisk_asc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_highrisk_asc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_all_highrisk_desc() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_all_highrisk_desc();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_lowmet_asc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_lowmet_asc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_lowmet_desc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_lowmet_desc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_midmet_asc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_midmet_asc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_midmet_desc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_midmet_desc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_highmet_asc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_highmet_asc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_highmet_desc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_highmet_desc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_lowact_asc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_lowact_asc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_lowact_desc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_lowact_desc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_midact_asc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_midact_asc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_midact_desc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_midact_desc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_highact_asc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_highact_asc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_highact_desc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_highact_desc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_lowrisk_asc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_lowrisk_asc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_lowrisk_desc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_lowrisk_desc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_midrisk_asc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_midrisk_asc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_midrisk_desc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_midrisk_desc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_highrisk_asc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_highrisk_asc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_part_highrisk_desc(String exgroup) {
    ArrayList<ExdataVO> list = this.exdataDAO.list_part_highrisk_desc(exgroup);
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_effect_low_weight() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_effect_low_weight();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_effect_mid_weight() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_effect_mid_weight();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_effect_high_weight() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_effect_high_weight();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_effect_low_muscle() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_effect_low_muscle();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_effect_mid_muscle() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_effect_mid_muscle();
    return list;
  }

  @Override
  public ArrayList<ExdataVO> list_effect_high_muscle() {
    ArrayList<ExdataVO> list = this.exdataDAO.list_effect_high_muscle();
    return list;
  }
  
  

}
