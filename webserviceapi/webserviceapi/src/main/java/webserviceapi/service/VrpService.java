package webserviceapi.service;

import webserviceapi.shared.dto.Node;

import java.util.List;

public interface VrpService {
    void insertDataCoordinate();
    void insertIntersectionCoordinates();
    void insertRoutes();
    void createMainRoute();
    void insertNode();
    List<Node> getAllNode();
}
