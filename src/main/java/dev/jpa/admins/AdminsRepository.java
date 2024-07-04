package dev.jpa.admins;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminsRepository extends JpaRepository<Admins, Integer> {
  /**
   * 직원 로그인
   * @param id
   * @param passwd
   * @return
   */
  Optional<Admins> findByIdAndPasswd(String id, String passwd);

}
