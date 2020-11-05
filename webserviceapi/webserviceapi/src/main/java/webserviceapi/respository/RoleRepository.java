package webserviceapi.respository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webserviceapi.entity.RoleEntity;

import java.util.List;


@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);

    RoleEntity findRolesById(long id);

    @Query(value = "SELECT p.*\n" +
            "FROM roles p", nativeQuery = true)
    List<RoleEntity> findAllRoles();

    @Query(value = "SELECT p.*\n" +
            "FROM roles p JOIN screens_roles pt ON p.id = pt.roles_id\n" +
            "JOIN screens t ON pt.screens_id = t.id\n" +
            "WHERE t.path = :path", nativeQuery = true)
    List<RoleEntity> findRoleByScreenPath(@Param("path") String path);

    @Query(value = "SELECT p.*\n" +
            "FROM roles p JOIN screens_roles pt ON p.id = pt.roles_id\n" +
            "JOIN screens t ON pt.screens_id = t.id\n" +
            "WHERE t.id = :id", nativeQuery = true)
    List<RoleEntity> _findRoleByScreenId(@Param("id") long id);
}
