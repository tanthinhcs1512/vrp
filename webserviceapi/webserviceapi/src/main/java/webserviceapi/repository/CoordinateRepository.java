package webserviceapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webserviceapi.entity.CoordinateEntity;


@Repository
public interface CoordinateRepository extends CrudRepository<CoordinateEntity, Long> {
}
