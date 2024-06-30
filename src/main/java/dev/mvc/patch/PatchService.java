package dev.mvc.patch;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Process 역할
 */
@Service
public class PatchService {
  private final PatchRepository repository;
  
  @Autowired
  public PatchService(PatchRepository repository) {
    this.repository = repository;
  }
  
  /** CREATE , INSERT ~ , UPDATE ~ */
  public Patch saveEntity(Patch entity) {
    return repository.save(entity); // method/SQL 자동 생성
  }
  
  /** 모든 레코드 출력 */
  public List<Patch> find_all() {
    return repository.findAll();
  }
  
  /** title 검색 */
  public List<Patch> find_by_title(String title){
    return repository.findByTitleContaining(title); // SQL 자동 생성
  }
  
  /** rdate 특정 날짜로 검색 */
  public List<Patch> find_by_rdate(String rdate) {
    return repository.findByRdateContaining(rdate);
  }
  
  /** title or content로 검색 */
  public List<Patch> find_by_title_or_content(String title, String content) {
    return repository.findByTitleContainingOrContentContaining(title, content);
  }
  
  /** title and content로 검색 */
  public List<Patch> find_by_title_and_content(String title, String content){
    return repository.findByTitleContainingAndContentContaining(title, content);
  }
  
  /** title 대소문자 무시 검색 */
  public List<Patch> find_by_title_ignorecase(String title){
    return repository.findByTitleContainingIgnoreCase(title);
  }
  
  /** 날짜 지정 검색 */
  public List<Patch> find_by_rdate_period(String start_date, String end_date){
    return repository.findByRdatePeriod(start_date, end_date);
  }
  
  /** 날짜 내림 차순, 500건 읽어오기 */
  public List<Patch> find_all_by_order_by_radte_desc(){
    return repository.findAllByOrderByRdateDesc();
  }
  
  public List<Patch> find_all_by_order_by_rdate_desc_period(String start_date, String end_date) {
    return repository.findByRdateDescPeriod(start_date, end_date);
  }
  
  /**
   * 조회수 증가
   * @param 
   */
  public void increaseCnt(Long id) {
    repository.increaseCnt(id);
  }
  
  
  
  
  
}
