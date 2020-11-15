package webserviceapi.repository.jdbc;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import webserviceapi.rowmapper.RowMapperIntersectionCoordinate;
import webserviceapi.shared.dto.IntersectionCoordinateDto;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class IntersectionCoordinateRepository {

    private final String SQL_INTERSECTION_COORDINATE = "Select * \n" +
            "from ( \n" +
            "Select count(*) as count, latitude, longitude\n" +
            "from coordinate\n" +
            "group by latitude, longitude) as  u\n" +
            "order by u.count desc;";

    private final JdbcTemplate jdbcTemplate;

    public IntersectionCoordinateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<IntersectionCoordinateDto> getIntersectionCoordinates() {
        try {
            RowMapper<IntersectionCoordinateDto> rowMapper =  new RowMapperIntersectionCoordinate();
            List<IntersectionCoordinateDto> lst = jdbcTemplate.query(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_INTERSECTION_COORDINATE);
                return ps;
            }, rowMapper);
            return lst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
