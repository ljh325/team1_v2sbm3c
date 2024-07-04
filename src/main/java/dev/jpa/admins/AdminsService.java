package dev.jpa.admins;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminsService {
  private final AdminsRepository repository;
  
  /** 생성자 */
  @Autowired
  public AdminsService(AdminsRepository repository) {
    this.repository = repository;
  }
  
  /**
   * 관리자 로그인
   * @param id
   * @param passwd
   * @return
   */
  public Optional<Admins> find_by_id_and_passwd(String id, String passwd){
    return repository.findByIdAndPasswd(id, passwd);
  }
}
