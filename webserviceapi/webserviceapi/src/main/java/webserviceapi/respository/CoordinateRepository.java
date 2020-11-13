package webserviceapi.respository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webserviceapi.entity.CoordinateEntity;
import webserviceapi.entity.RoleEntity;

import java.util.List;


@Repository
public interface CoordinateRepository extends CrudRepository<CoordinateEntity, Long> {
}
