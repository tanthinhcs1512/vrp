package webserviceapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webserviceapi.entity.IntersectionEntity;
import webserviceapi.entity.NodeEntity;

@Repository
public interface NodeRepository extends CrudRepository<NodeEntity, Long> {
}
