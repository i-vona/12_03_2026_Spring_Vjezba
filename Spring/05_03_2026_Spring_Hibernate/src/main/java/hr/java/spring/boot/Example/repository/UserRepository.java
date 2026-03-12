package hr.java.spring.boot.Example.repository;

import hr.java.spring.boot.Example.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserInfo, Long> {

    UserInfo findByUsername(String username);
}