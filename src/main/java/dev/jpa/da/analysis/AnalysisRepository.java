package dev.jpa.da.analysis;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
  /**
   * 전체 목록
   * @return
   */
  List<Analysis> findAllByOrderByAnalysisnoDesc();

  /**
   * 페이징 목록
   * @return
   */
  Page<Analysis> findAllByOrderByAnalysisnoDesc(Pageable pageable);

}

