package dev.jpa.da.analysis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 생성자 자동 생성
@Service  // 알고리즘 구현 클래스, 메모리에 자동 등록됨
public class AnalysisService {
  private final AnalysisRepository repository;  
  // private final PasswordEncoder pe;
  
  /**
   * 데이터 분석 결과 등록, 수정
   * @param damember
   * @return
   */
  public Analysis save(Analysis analysis) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String now = sdf.format(new Date());
    analysis.setRdate(now);
    
    this.repository.save(analysis);
    
    return analysis;    
  }
  
  /**
   * 전체 목록
   * @return
   */
  public List<Analysis> list_all() {
    return this.repository.findAllByOrderByAnalysisnoDesc();
  }
  
  /**
   * 페이징 목록
   * @return
   */
  public Page<Analysis> list(int page) {
    Pageable pageable = PageRequest.of(page, 3); // 페이지 번호, 페이지 사이즈
    return this.repository.findAllByOrderByAnalysisnoDesc(pageable);
  }
  
  /**
   * 조회
   * @param model
   * @param analysisno
   * @return
   */
  public Analysis read(long analysisno) {
    Optional<Analysis> analysis = this.repository.findById(analysisno); // PK
    return analysis.get();

  }
  
  /**
   * 삭제
   * @param id
   */
  public void delete(Long id) {
    repository.deleteById(id);
  }
    
}


