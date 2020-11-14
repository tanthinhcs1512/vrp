package webserviceapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webserviceapi.entity.RouteEntity;

@Repository
public interface RouteRepository extends CrudRepository<RouteEntity, Long> {
}
