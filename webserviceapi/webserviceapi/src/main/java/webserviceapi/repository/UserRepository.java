package webserviceapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webserviceapi.entity.UserEntity;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findUserByEmail(String email);
    UserEntity findUserByUserId(String userId);
    UserEntity findUserById(Integer id);
    @Query(value = "SELECT s.*\n" +
            "FROM users AS s", nativeQuery = true)
    List<UserEntity> findUser();
}
