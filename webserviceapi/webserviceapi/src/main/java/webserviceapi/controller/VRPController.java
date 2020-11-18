package webserviceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webserviceapi.model.response.NodeCoordResponseModel;
import webserviceapi.service.VrpService;
import webserviceapi.shared.dto.Node;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/vrp")
public class VRPController {

    @Autowired
    VrpService vrpService;


    //INSERT all point coordinates from one place to another place, get all paths INTO DATABASE TABLE "coordinate".
    @GetMapping("/data/coordinate/9449f814-ed66-4fa0-9c55-4e414545a667")
    public String insertPointCoordinates() throws IOException, InterruptedException {
        vrpService.insertDataCoordinate();
        return "coordinate";
    }

    //INSERT all intersection point coordinates among routes INTO DATABASE TABLE "intersection_point"
    @GetMapping("/data/midpoint/9449f814-ed66-4fa0-9c55-4e414545a667")
    public String insertIntersectionPointCoordinates() {
        vrpService.insertIntersectionCoordinates();
        return "midPoint";
    }

    //INSERT all intersection point coordinates among routes INTO DATABASE TABLE "intersection_point"
    @GetMapping("/data/route/9449f814-ed66-4fa0-9c55-4e414545a667")
    public String insertRoute() {
        vrpService.insertRoutes();
        return "route";
    }

    @GetMapping("/data/createMainRoute/9449f814-ed66-4fa0-9c55-4e414545a667")
    public String createMainRoute() {
        vrpService.createMainRoute();
        return "main route";
    }

    @GetMapping("/data/node/9449f814-ed66-4fa0-9c55-4e414545a667")
    public String node(){
        vrpService.insertNode();
        return "node";
    }

    @GetMapping("/node")
    public NodeCoordResponseModel getNode() {
        NodeCoordResponseModel returnValue = new NodeCoordResponseModel();
        List<Node> rs = vrpService.getAllNode();
        returnValue.setLst(rs);
        return returnValue;
    }

}
