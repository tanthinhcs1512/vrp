package webserviceapi.repository;

import org.springframework.data.repository.CrudRepository;
import webserviceapi.entity.GRouteEntity;
import webserviceapi.entity.RouteEntity;

public interface GRouteRepository extends CrudRepository<GRouteEntity, Long> {
}
