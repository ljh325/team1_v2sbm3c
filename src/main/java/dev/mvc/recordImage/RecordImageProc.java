package dev.mvc.recordImage;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.recordImage.RecordImageProc")
public class RecordImageProc implements RecordImageProcInter {
  
  @Autowired
  private RecordImageDAOInter recordImageDAO;

  @Override
  public int rec_images_insert(RecordImageVO recordImageVO) {
    int cnt = this.recordImageDAO.rec_images_insert(recordImageVO);
    return cnt;
  }

  @Override
  public ArrayList<RecordImageVO> rec_images_list(int memberno) {
    ArrayList<RecordImageVO> list = this.recordImageDAO.rec_images_list(memberno);
    return list;
  }

  @Override
  public ArrayList<RecordImageVO> rec_images_read(HashMap<String, Object> map) {
    ArrayList<RecordImageVO> read = this.recordImageDAO.rec_images_read(map);
    return read;
  }

  @Override
  public int rec_images_cnt(int memberno) {
    int cnt = this.recordImageDAO.rec_images_cnt(memberno);
    return cnt;
  }

  @Override
  public int rec_images_update(RecordImageVO recordImageVO) {
    int cnt = this.recordImageDAO.rec_images_update(recordImageVO);
    return cnt;
  }

  @Override
  public int rec_images_delete(int exrecordno) {
    int cnt = this.recordImageDAO.rec_images_delete(exrecordno);
    return cnt;
  }

  @Override
  public ArrayList<RecordImageVO> one_images_read(int memberno) {
    ArrayList<RecordImageVO> read = this.recordImageDAO.one_images_read(memberno);
    return read;
  }

}
