package dev.jpa.patch;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/patch")
public class PatchController {
  
  @Autowired
  private PatchService service;
  
  public PatchController() {
    System.out.println("-> PatchController created.");
  }
  
  /**
   * HTML 특수 문자의 변경
   * https://en.wikipedia.org/wiki/List_of_XML_and_HTML_character_entity_references    
   * @param str
   * @return
   */
  public static synchronized String convertChar(String str) {
    str = str.replace("&", "&amp;");  // 특수 문자 -> 엔티티로 변경 -> 브러우저 출력시 기능이 없는 단순 문자로 출력
    str = str.replace("<", "&lt;");
    str = str.replace(">", "&gt;");
    str = str.replace("'", "&apos;");
    str = str.replace("\"", "&quot;");
    str = str.replace("\r\n", "<BR>");
    str = str.replace(" ", "&nbsp;");
    return str;
  }
  
  /**
   * Create - POST 요청을 처리하여 새로운 MyEntity 객체를 생성
   * @RequestBody ISSUE entity: JSON → JAVA 객체로 매핑
   * http://localhost:9100/patch
   * @PostMapping(consumes = "application/json") 생략 가능, 기본적으로 JSON 입출력
   * @param entity
   * @return
   */
  @PostMapping
  public ResponseEntity<Patch> createEntity(@RequestBody Patch entity) {
    System.out.println("-> 레코드 추가: " + entity.getTitle());
    
    Patch savedEntity = service.saveEntity(entity);
    return ResponseEntity.ok(savedEntity); // 생성된 엔티티 반환
  }
  
  /**
   * 전체 목록
   * GET 요청을 처리하여 모든 Entity 객체의 리스트를 반환
   * http://localhost:9100/patch/find_all
   * @return
   */
  @GetMapping(path = "/find_all")
  public List<Patch> find_all() {
    return service.find_all();
  }
  
  /**
   *  rdate 내림차순 정렬
   * @return
   */
  @GetMapping(path = "/find_all_by_order_by_rdate_desc")
  public List<Patch> find_all_by_order_by_rdate_desc() {
    return service.find_all_by_order_by_radte_desc();
  }
  
  /**
   * 조회, GET 요청을 처리하여 특정 ID를 가진 Entity 객체를 반환
   * 찾은 경우 객체 반환, 찾지 못한 경우 404 반환
   * http://localhost:9100/issue/12
   * @param id
   * @return
   */
  @GetMapping(path = "/{id}")
  public ResponseEntity<Patch> find_by_id(@PathVariable("id") Integer id) {
      System.out.println("-> service.find_by_id(id): " + service.find_by_id(id));
      System.out.println("-> service.find_by_id(id).map(ResponseEntity::ok): " + service.find_by_id(id).map(ResponseEntity::ok));
      // result -> ResponseEntity.ok(result): result 파라미터를 ok 메소드로 전달, 람다식
      
      // 조회수 증가
      service.increaseCnt(id);
      
      // type 1
      return service.find_by_id(id).map(result -> ResponseEntity.ok(result)).orElseGet(() -> ResponseEntity.notFound().build());
      
      // type 2
//      return service.find_by_id(id).map(existingEntity -> {
//        existingEntity.setTitle(convertChar(existingEntity.getTitle()));
//        existingEntity.setContent(convertChar(existingEntity.getContent()));
//        return ResponseEntity.ok(existingEntity);
//      }).orElseGet(() -> ResponseEntity.notFound().build()); // 찾지 못한 경우 404 반환
  }
  
  /**
   * 수정
   * PUT 요청을 처리하여 특정 ID를 가진 Entity 객체를 업데이트
   * http://localhost:9100/patch/12
   * @param id
   * @param entity
   * @return
   */
  @PutMapping(path = "/{id}")
  public ResponseEntity<Patch> updateEntity(@PathVariable("id") Integer id, @RequestBody Patch entity) {
    // id를 이용한 레코드 조회 -> existingEntity 객체에 할당 -> {} 실행 값 저장 -> DBMS 저장 -> 상태 코드 200 출력
    return service.find_by_id(id).map(existingEntity -> {
      existingEntity.setTitle(entity.getTitle());
      existingEntity.setContent(entity.getContent());
      
      return ResponseEntity.ok(service.saveEntity(existingEntity));
    }).orElseGet(() -> ResponseEntity.notFound().build()); // 찾지 못한 경우 404 반환
  }

  /**
   * DELETE 요청을 처리하여 특정 ID를 가진 Entity 객체를 삭제
   * http://localhost:9100/patch/12
   * @param id
   * @return
   */
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteEntity(@PathVariable("id") Integer id) {
    if (service.find_by_id(id).isPresent()) { // Entity가 존재하면
      service.deleteEntity(id); // 삭제
      return ResponseEntity.ok().build(); // 성공적으로 삭제된 경우 200 반환
    } else {
      return ResponseEntity.notFound().build(); // 찾지 못한 경우 404 반환
    }
  }

}
