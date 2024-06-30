package dev.mvc.patch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
   * http://localhost:9093/patch
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
   * http://localhost:9093/patch/find_all
   * @return
   */
  @GetMapping(path = "/find_all")
  public List<Patch> find_all() {
    return service.find_all();
  }

}
