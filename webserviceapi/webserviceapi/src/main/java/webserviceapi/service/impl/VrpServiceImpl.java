package webserviceapi.service.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import webserviceapi.entity.CoordinateEntity;
import webserviceapi.entity.IntersectionEntity;
import webserviceapi.entity.RouteEntity;
import webserviceapi.repository.CoordinateRepository;
import webserviceapi.repository.IntersectionRepository;
import webserviceapi.repository.RouteRepository;
import webserviceapi.repository.jdbc.IntersectionCoordinateRepository;
import webserviceapi.service.VrpService;
import webserviceapi.shared.dto.IntersectionCoordinateDto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VrpServiceImpl implements VrpService {

    @Autowired
    CoordinateRepository coordinateRepository;

    @Autowired
    RouteRepository routeRepository;

    @Autowired
    IntersectionRepository intersectionRepository;

    @Autowired
    IntersectionCoordinateRepository intersectionCoordinateRepository;

    @Override
    public void insertDataCoordinate() {

        //my key api
        String KEY = "9449f814-ed66-4fa0-9c55-4e414545a667";
        BufferedReader reader;
        //String URL = "https://graphhopper.com/api/1/route?point=50.924327,2.865072&point=50.924967,2.8652&vehicle=car&points_encoded=false&key=" + KEY;
        List<String[]> lines = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(
                    "D:\\vrp\\vrp\\webserviceapi\\webserviceapi\\src\\main\\java\\webserviceapi\\service\\impl\\node.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] rs = line.split(" ");
                lines.add(rs);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 62; i < lines.size(); i++) {
            List<CoordinateEntity> coordinateEntities = new ArrayList<>();
            for (int j = 0; j < lines.size(); j++) {
                if (i != j) {
                    String URL = "https://graphhopper.com/api/1/route?point=" + lines.get(i)[1] + "," + lines.get(i)[2] +
                            "&point="  + lines.get(j)[1] + "," + lines.get(j)[2] + "&vehicle=car&points_encoded=false&key=" + KEY;
                    //connect to Graphhoper's API for information of routes
                    ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
                    String responseBody = response.getBody();
                    JSONObject resObj = new JSONObject(responseBody);
                    JSONArray array = resObj.getJSONArray("paths");
                    JSONObject res_path = array.getJSONObject(0);
                    JSONObject res_points = res_path.getJSONObject("points");
                    JSONArray res_coordinates = res_points.getJSONArray("coordinates");
                    for (int k = 0; k < res_coordinates.length(); k++) {
                        String[] coordinate = res_coordinates.get(k).toString().split(",");
                        CoordinateEntity coordinateEntity = new CoordinateEntity(
                                lines.get(i)[0],
                                lines.get(i)[3],
                                lines.get(j)[3],
                                Double.parseDouble(coordinate[1].replace("]", "")),
                                Double.parseDouble(coordinate[0].replace("[", ""))
                        );
                        coordinateEntities.add(coordinateEntity);
                    }
                    coordinateRepository.saveAll(coordinateEntities);
                }
            }
        }
    }

    @Override
    public void insertIntersectionCoordinates() {
        List<IntersectionCoordinateDto> intersectionEntities = intersectionCoordinateRepository.getIntersectionCoordinates();
        if (intersectionEntities.size() > 0 && intersectionEntities != null) {
            for (int i = 0; i < 10; i++) {
                int randomInteger = (int)(intersectionEntities.size() * Math.random());;
                IntersectionEntity intersectionEntity = new IntersectionEntity(intersectionEntities.get(randomInteger).getLatitude(),
                        intersectionEntities.get(randomInteger).getLongitude(),
                        intersectionEntities.get(randomInteger).getCount());
                intersectionRepository.save(intersectionEntity);
            }
        }
    }

    @Override
    public void insertRoutes() {
        //my key api
        String KEY = "9449f814-ed66-4fa0-9c55-4e414545a667";
        BufferedReader reader;
        //String URL = "https://graphhopper.com/api/1/route?point=50.924327,2.865072&point=50.924967,2.8652&vehicle=car&points_encoded=false&key=" + KEY;
        List<String[]> lines = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(
                    "D:\\vrp\\vrp\\webserviceapi\\webserviceapi\\src\\main\\java\\webserviceapi\\service\\impl\\node.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] rs = line.split(" ");
                lines.add(rs);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 50; i < lines.size(); i++) {
            System.out.println(lines.get(i)[3] + "\n");
            List<RouteEntity> routeEntities = new ArrayList<>();
            for (int j = 0; j < lines.size(); j++) {
                if (i != j) {
                    String URL = "https://graphhopper.com/api/1/route?point=" + lines.get(i)[1] + "," + lines.get(i)[2] +
                            "&point="  + lines.get(j)[1] + "," + lines.get(j)[2] + "&vehicle=car&points_encoded=false&key=" + KEY;
                    //connect to Graphhoper's API for information of routes
                    ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
                    String responseBody = response.getBody();
                    JSONObject resObj = new JSONObject(responseBody);
                    JSONArray array = resObj.getJSONArray("paths");
                    JSONObject res_path = array.getJSONObject(0);
                    JSONArray bbox = res_path.getJSONArray("bbox");
                    RouteEntity routeEntity = new RouteEntity();
                    routeEntity.setIdPlaceOrigin(lines.get(i)[0]);
                    routeEntity.setOrigin(lines.get(i)[3]);
                    routeEntity.setLongitudeOrigin(Double.parseDouble(lines.get(i)[2]));
                    routeEntity.setLatitudeOrigin(Double.parseDouble(lines.get(i)[1]));

                    routeEntity.setIdPlaceDestination(lines.get(j)[0]);
                    routeEntity.setDestination(lines.get(j)[3]);
                    routeEntity.setLongitudeDestination(Double.parseDouble(lines.get(j)[2]));
                    routeEntity.setLatitudeDestination(Double.parseDouble(lines.get(j)[1]));

                    routeEntity.setDistance((Double)res_path.get("distance"));
                    routeEntity.setTime((Integer)res_path.get("time"));
                    routeEntity.setWeight((Double)res_path.get("weight"));
                    routeEntity.setTransfer((Integer) res_path.get("transfers"));

                    routeEntity.setBbox_1((Double)bbox.get(0));
                    routeEntity.setBbox_2((Double)bbox.get(1));
                    routeEntity.setBbox_3((Double)bbox.get(2));
                    routeEntity.setBbox_4((Double)bbox.get(3));
                    routeEntities.add(routeEntity);
                }
            }
            if (routeEntities.size() > 0 && routeEntities != null) {
                routeRepository.saveAll(routeEntities);
            }
        }
    }
}
