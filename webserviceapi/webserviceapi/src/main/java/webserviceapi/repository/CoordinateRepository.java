package webserviceapi.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webserviceapi.entity.CoordinateEntity;

import java.util.List;


@Repository
public interface CoordinateRepository extends CrudRepository<CoordinateEntity, Long> {
    @Query(value = "Select * from coordinate where origin = :origin ", nativeQuery = true)
    List<CoordinateEntity> getCoordinatesByOriginAndDestination(@Param("origin") String origin);
}
