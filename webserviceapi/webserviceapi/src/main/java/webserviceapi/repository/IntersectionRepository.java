package webserviceapi.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import webserviceapi.entity.IntersectionEntity;

import java.util.List;

@Repository
public interface IntersectionRepository extends CrudRepository<IntersectionEntity, Long> {

    @Query(value = "Select * \n" +
            "from ( \n" +
            "Select id = 1, latitude, longitude, count(*) as count\n" +
            "from coordinate\n" +
            "group by latitude, longitude) as  u\n" +
            "order by u.count desc;", nativeQuery = true)
    List<IntersectionEntity> intersectionCoordinates();
}
