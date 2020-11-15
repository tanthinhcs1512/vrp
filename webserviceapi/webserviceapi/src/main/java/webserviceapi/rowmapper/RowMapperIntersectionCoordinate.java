package webserviceapi.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import webserviceapi.shared.dto.IntersectionCoordinateDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapperIntersectionCoordinate implements RowMapper<IntersectionCoordinateDto> {
    @Override
    public IntersectionCoordinateDto mapRow(ResultSet resultSet, int i) throws SQLException {
        IntersectionCoordinateDto intersectionCoordinateDto = new IntersectionCoordinateDto();
        intersectionCoordinateDto.setCount(resultSet.getInt("count"));
        intersectionCoordinateDto.setLatitude(resultSet.getDouble("latitude"));
        intersectionCoordinateDto.setLongitude(resultSet.getDouble("longitude"));
        return intersectionCoordinateDto;
    }
}
