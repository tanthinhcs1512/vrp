package webserviceapi.controller;

import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webserviceapi.service.VrpService;

import java.io.IOException;
import java.text.DecimalFormat;

@RestController
@RequestMapping("/vrp")
public class VRPController {

    @Autowired
    VrpService vrpService;


    //INSERT all point coordinates from one place to another place, get all paths INTO DATABASE TABLE "coordinate".
    @GetMapping("/data/coordinate/9449f814-ed66-4fa0-9c55-4e414545a667")
    public String insertPointCoordinates() throws IOException, InterruptedException {
        vrpService.getDataCoordinate();
        return "coordinate";
    }

    //INSERT all intersection point coordinates among routes INTO DATABASE TABLE "intersection_point"
    @GetMapping("/data/midpoint/9449f814-ed66-4fa0-9c55-4e414545a667")
    public String insertIntersectionPointCoordinates() {
        return "midPoint";
    }


//    public double CalculationByDistance() {
//        int Radius = 6371;// radius of earth in Km
//        double lat1 = 50.924327;
//        double lat2 = 50.924967;
//        double lon1 = 2.865072;
//        double lon2 = 2.8652;
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//                + Math.cos(Math.toRadians(lat1))
//                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
//                * Math.sin(dLon / 2);
//        double c = 2 * Math.asin(Math.sqrt(a));
//        double valueResult = Radius * c;
//        double km = valueResult / 1;
//        DecimalFormat newFormat = new DecimalFormat("####");
//        int kmInDec = Integer.valueOf(newFormat.format(km));
//        double meter = valueResult % 1000;
//        int meterInDec = Integer.valueOf(newFormat.format(meter));
//
//        return Radius * c;
//    }
}
