package webserviceapi.service;

import java.io.IOException;

public interface VrpService {
    void insertDataCoordinate() throws IOException, InterruptedException;
    void insertIntersectionCoordinates();
}
