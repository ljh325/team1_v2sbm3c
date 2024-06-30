package dev.mvc.patch;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PatchRepository extends JpaRepository<Patch, Integer> {
  // 모든 항목 검색
  List<Patch> findAll();
  // title로 LIKE 검색, find로 시작되어야 함, findBy + 컬럼명 + Containing.
  List<Patch> findByTitleContaining(String title);
  
  // rdate로 검색, findBy + 컬럼명 + Containing.
  List<Patch> findByRdateContaining(String rdate);

  // title or content로 검색
  List<Patch> findByTitleContainingOrContentContaining(String title, String content);
  
  // title and content로 검색
  List<Patch> findByTitleContainingAndContentContaining(String title, String content);

  // title 대소문자 무시 검색
  List<Patch> findByTitleContainingIgnoreCase(String title);
  
  // SQL 사용, 컬럼을 모두 명시해야함, 날짜 구간 검색
  @Query(value="SELECT patchno, title, content, viewcnt, rdate FROM patch WHERE (SUBSTR(rdate, 1, 10) >= :start_date) AND (SUBSTR(rdate, 1, 10) <= :end_date)", nativeQuery = true)
  List<Patch> findByRdatePeriod(@Param("start_date") String start_date, @Param("end_date") String end_date);
 
  // 페이징하지 않는 경우
  // rdate를 기준으로 내림차순 정렬하여 Patch 목록을 출력하는 메소드
  // List<Patch> findAllByOrderByRdateDesc();

  // 페이징 하는 경우
  // rdate를 기준으로 내림차순 정렬하여 Patch 페이징 목록을 출력하는 메소드
  // Oracle 12C~
  // Page<Patch> findAllByOrderByRdateDesc(Pageable pageable);
  
  // Oracle 11G
  @Query(value="SELECT patchno, title, content, viewcnt, rdate, r FROM ( SELECT patchno, title, content, viewcnt, rdate, rownum as r FROM ( SELECT patchno, title, content, viewcnt, rdate FROM patch ORDER BY rdate DESC )) WHERE r <= 500", nativeQuery = true)
  List<Patch> findAllByOrderByRdateDesc();
  
  // SQL 사용, 컬럼을 모두 명시해야함, 날짜 구간 검색, 날짜 내림 차순 정렬
  @Query(value="SELECT patchno, title, content, viewcnt, rdate FROM patch WHERE (SUBSTR(rdate, 1, 10) >= :start_date) AND (SUBSTR(rdate, 1, 10) <= :end_date) ORDER BY rdate DESC", nativeQuery = true)
  List<Patch> findByRdateDescPeriod(@Param("start_date") String start_date, @Param("end_date") String end_date);

  // 조회수 증가
  @Modifying
  @Transactional
  @Query(value="UPDATE patch SET viewcnt = viewcnt + 1 WHERE patchno=:id", nativeQuery = true)
  void increaseCnt(@Param("id") Long id);
  
  
}



