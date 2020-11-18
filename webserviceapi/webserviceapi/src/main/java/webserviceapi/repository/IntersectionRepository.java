package webserviceapi.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webserviceapi.entity.IntersectionEntity;

import java.util.List;

@Repository
public interface IntersectionRepository extends CrudRepository<IntersectionEntity, Long> {

    @Query(value = "Select * from intersection", nativeQuery = true)
    List<IntersectionEntity> intersectionCoordinates();
}
