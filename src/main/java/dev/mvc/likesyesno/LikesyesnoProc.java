package dev.mvc.likesyesno;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("dev.mvc.likesyesno.LikesyesnoProc")
public class LikesyesnoProc implements LikesyesnoProcInter{

  @Autowired
  private LikesyesnoDAOInter likesyesnoDAO;
  
  @Override
  public int like_insert(LikesyesnoVO likesyesnoVO) {
    int cnt = this.likesyesnoDAO.like_insert(likesyesnoVO);
    return cnt;
  }

  @Override
  public ArrayList<LikesyesnoVO> like_read(int exrecordno) {
    ArrayList<LikesyesnoVO> list =this.likesyesnoDAO.like_read(exrecordno);
    return list;
  }

  @Override
  public int like_cnt(int exrecordno) {
    int cnt = this.likesyesnoDAO.like_cnt(exrecordno);
    return cnt;
  }

  @Override
  public int unlike(HashMap<String, Object> map) {
    int cnt = this.likesyesnoDAO.unlike(map);
    return cnt;
  }

  @Override
  public LikesyesnoVO like_read_one(HashMap<String, Object> map) {
    LikesyesnoVO like = this.likesyesnoDAO.like_read_one(map);
    return like;
  }

}
