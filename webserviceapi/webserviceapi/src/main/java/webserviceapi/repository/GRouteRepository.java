package webserviceapi.repository;

import org.springframework.data.repository.CrudRepository;
import webserviceapi.entity.GRouteEntity;

public interface GRouteRepository extends CrudRepository<GRouteEntity, Long> {
}
