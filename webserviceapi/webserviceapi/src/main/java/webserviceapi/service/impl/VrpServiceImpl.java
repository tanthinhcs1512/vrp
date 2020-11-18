package webserviceapi.service.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import webserviceapi.entity.CoordinateEntity;
import webserviceapi.entity.GRouteEntity;
import webserviceapi.entity.IntersectionEntity;
import webserviceapi.entity.NodeEntity;
import webserviceapi.entity.RouteEntity;
import webserviceapi.repository.CoordinateRepository;
import webserviceapi.repository.GRouteRepository;
import webserviceapi.repository.IntersectionRepository;
import webserviceapi.repository.NodeRepository;
import webserviceapi.repository.RouteRepository;
import webserviceapi.repository.jdbc.IntersectionCoordinateRepository;
import webserviceapi.service.VrpService;
import webserviceapi.shared.dto.IntersectionCoordinateDto;
import webserviceapi.shared.dto.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Autowired
    GRouteRepository gRouteRepository;

    @Autowired
    NodeRepository nodeRepository;

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
            String name = "INTERSECTION_";
            for (int i = 0; i < 20; i++) {
                int randomInteger = (int)(intersectionEntities.size() * Math.random());;
                IntersectionEntity intersectionEntity = new IntersectionEntity(intersectionEntities.get(randomInteger).getLatitude(),
                        intersectionEntities.get(randomInteger).getLongitude(),
                        intersectionEntities.get(randomInteger).getCount(), name + i);
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


    @Override
    public void createMainRoute() {
        BufferedReader reader;
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
            Iterable<IntersectionEntity> intersections = intersectionRepository.findAll();
            for (int i = 42; i < lines.size(); i++) {
                Iterable<CoordinateEntity> listAll = coordinateRepository.getCoordinatesByOriginAndDestination(lines.get(i)[3]);
                List<CoordinateEntity> _list = StreamSupport
                        .stream(listAll.spliterator(), false)
                        .collect(Collectors.toList());
                for (int j = 0; j < lines.size(); j++) {
                    if (i != j) {
                        List<CoordinateEntity> list = filter(_list, lines.get(i)[3], lines.get(j)[3]);
                        List<CoordinateEntity> rs = new ArrayList<>();
                        for (CoordinateEntity item : list) {
                            for (IntersectionEntity intersection: intersections){
                                IntersectionEntity ele = intersection;
                                if (item.getLatitude().equals(ele.getLatitude()) && item.getLongitude().equals(ele.getLongitude())) {
                                    rs.add(item);
                                }
                            }
                        }
                        //sort
                        RestTemplate restTemplate = new RestTemplate();
                        String KEY = "9449f814-ed66-4fa0-9c55-4e414545a667";
                        String URL = "";
                        if (rs.size() > 0 && rs != null) {
                            CoordinateComparer comparer = new CoordinateComparer();
                            Collections.sort(rs, comparer);
                            List<GRouteEntity> lst = new ArrayList<>();
                            if (rs.size() == 1) {
                                for (int m = 0; m < 2; m++) {
                                    if (m == 0) {
                                        URL = "https://graphhopper.com/api/1/route?point=" + lines.get(i)[1] + "," + lines.get(i)[2] +
                                                "&point="  + rs.get(0).getLatitude() + "," + rs.get(0).getLongitude() + "&vehicle=car&points_encoded=false&key=" + KEY;
                                        //connect to Graphhoper's API for information of routes
                                        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
                                        String responseBody = response.getBody();
                                        JSONObject resObj = new JSONObject(responseBody);
                                        JSONArray array = resObj.getJSONArray("paths");
                                        JSONObject res_path = array.getJSONObject(0);
                                        JSONArray bbox = res_path.getJSONArray("bbox");
                                        GRouteEntity grouteEntity = new GRouteEntity();
                                        grouteEntity.setIdPlaceOrigin(lines.get(i)[0]);
                                        grouteEntity.setOrigin(lines.get(i)[3]);
                                        grouteEntity.setLongitudeOrigin(Double.parseDouble(lines.get(i)[2]));
                                        grouteEntity.setLatitudeOrigin(Double.parseDouble(lines.get(i)[1]));

                                        grouteEntity.setIdPlaceDestination("-1");
                                        IntersectionEntity temp = null;
                                        for (IntersectionEntity intersection: intersections){
                                            IntersectionEntity ele = intersection;
                                            if (rs.get(0).getLatitude().equals(ele.getLatitude()) && rs.get(0).getLongitude().equals(ele.getLongitude())) {
                                                temp = ele;
                                                break;
                                            }
                                        }
                                        grouteEntity.setDestination(temp.getName());
                                        grouteEntity.setLongitudeDestination(temp.getLatitude());
                                        grouteEntity.setLatitudeDestination(temp.getLongitude());

                                        grouteEntity.setDistance((Double)res_path.get("distance"));
                                        grouteEntity.setTime((Integer)res_path.get("time"));
                                        grouteEntity.setWeight((Double)res_path.get("weight"));
                                        grouteEntity.setTransfer((Integer) res_path.get("transfers"));

                                        grouteEntity.setBbox_1((Double)bbox.get(0));
                                        grouteEntity.setBbox_2((Double)bbox.get(1));
                                        grouteEntity.setBbox_3((Double)bbox.get(2));
                                        grouteEntity.setBbox_4((Double)bbox.get(3));
                                        lst.add(grouteEntity);
                                    } else {
                                        URL = "https://graphhopper.com/api/1/route?point=" + rs.get(0).getLatitude() + "," + rs.get(0).getLongitude() +
                                                "&point="  + lines.get(j)[1] + "," + lines.get(j)[2] + "&vehicle=car&points_encoded=false&key=" + KEY;
                                        //connect to Graphhoper's API for information of routes
                                        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
                                        String responseBody = response.getBody();
                                        JSONObject resObj = new JSONObject(responseBody);
                                        JSONArray array = resObj.getJSONArray("paths");
                                        JSONObject res_path = array.getJSONObject(0);
                                        JSONArray bbox = res_path.getJSONArray("bbox");
                                        GRouteEntity grouteEntity = new GRouteEntity();
                                        grouteEntity.setIdPlaceOrigin("-2");
                                        IntersectionEntity temp = null;
                                        for (IntersectionEntity intersection: intersections){
                                            IntersectionEntity ele = intersection;
                                            if (rs.get(0).getLatitude().equals(ele.getLatitude()) && rs.get(0).getLongitude().equals(ele.getLongitude())) {
                                                temp = ele;
                                                break;
                                            }
                                        }
                                        grouteEntity.setOrigin(temp.getName());
                                        grouteEntity.setLatitudeOrigin(temp.getLatitude());
                                        grouteEntity.setLongitudeOrigin(temp.getLongitude());

                                        grouteEntity.setIdPlaceDestination(lines.get(j)[0]);
                                        grouteEntity.setDestination(lines.get(j)[3]);
                                        grouteEntity.setLongitudeDestination(Double.parseDouble(lines.get(j)[2]));
                                        grouteEntity.setLatitudeDestination(Double.parseDouble(lines.get(j)[1]));

                                        grouteEntity.setDistance((Double)res_path.get("distance"));
                                        grouteEntity.setTime((Integer)res_path.get("time"));
                                        grouteEntity.setWeight((Double)res_path.get("weight"));
                                        grouteEntity.setTransfer((Integer) res_path.get("transfers"));

                                        grouteEntity.setBbox_1((Double)bbox.get(0));
                                        grouteEntity.setBbox_2((Double)bbox.get(1));
                                        grouteEntity.setBbox_3((Double)bbox.get(2));
                                        grouteEntity.setBbox_4((Double)bbox.get(3));
                                        lst.add(grouteEntity);
                                    }
                                }
                            } else {
                                for (int e = 0; e < rs.size(); e++) {
                                    if (e == 0) {
                                        URL = "https://graphhopper.com/api/1/route?point=" + lines.get(i)[1] + "," + lines.get(i)[2] +
                                                "&point=" + rs.get(0).getLatitude() + "," + rs.get(0).getLongitude() + "&vehicle=car&points_encoded=false&key=" + KEY;
                                        //connect to Graphhoper's API for information of routes
                                        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
                                        String responseBody = response.getBody();
                                        JSONObject resObj = new JSONObject(responseBody);
                                        JSONArray array = resObj.getJSONArray("paths");
                                        JSONObject res_path = array.getJSONObject(0);
                                        JSONArray bbox = res_path.getJSONArray("bbox");
                                        GRouteEntity grouteEntity = new GRouteEntity();

                                        grouteEntity.setIdPlaceOrigin(lines.get(i)[0]);
                                        grouteEntity.setOrigin(lines.get(i)[3]);
                                        grouteEntity.setLongitudeOrigin(Double.parseDouble(lines.get(i)[2]));
                                        grouteEntity.setLatitudeOrigin(Double.parseDouble(lines.get(i)[1]));

                                        grouteEntity.setIdPlaceDestination("-1");
                                        IntersectionEntity temp = null;
                                        for (IntersectionEntity intersection : intersections) {
                                            IntersectionEntity ele = intersection;
                                            if (rs.get(0).getLatitude().equals(ele.getLatitude()) && rs.get(0).getLongitude().equals(ele.getLongitude())) {
                                                temp = ele;
                                                break;
                                            }
                                        }
                                        grouteEntity.setDestination(temp.getName());
                                        grouteEntity.setLongitudeDestination(temp.getLatitude());
                                        grouteEntity.setLatitudeDestination(temp.getLongitude());

                                        grouteEntity.setDistance((Double) res_path.get("distance"));
                                        grouteEntity.setTime((Integer) res_path.get("time"));
                                        grouteEntity.setWeight((Double) res_path.get("weight"));
                                        grouteEntity.setTransfer((Integer) res_path.get("transfers"));

                                        grouteEntity.setBbox_1((Double) bbox.get(0));
                                        grouteEntity.setBbox_2((Double) bbox.get(1));
                                        grouteEntity.setBbox_3((Double) bbox.get(2));
                                        grouteEntity.setBbox_4((Double) bbox.get(3));
                                        lst.add(grouteEntity);
                                    }
                                    else if (e == (lst.size() - 1)) {
                                        URL = "https://graphhopper.com/api/1/route?point=" + rs.get(0).getLatitude() + "," + rs.get(0).getLongitude() +
                                                "&point=" + lines.get(j)[1] + "," + lines.get(j)[2] + "&vehicle=car&points_encoded=false&key=" + KEY;
                                        //connect to Graphhoper's API for information of routes
                                        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
                                        String responseBody = response.getBody();
                                        JSONObject resObj = new JSONObject(responseBody);
                                        JSONArray array = resObj.getJSONArray("paths");
                                        JSONObject res_path = array.getJSONObject(0);
                                        JSONArray bbox = res_path.getJSONArray("bbox");
                                        GRouteEntity grouteEntity = new GRouteEntity();

                                        grouteEntity.setIdPlaceOrigin("-2");
                                        IntersectionEntity temp = null;
                                        for (IntersectionEntity intersection: intersections){
                                            IntersectionEntity ele = intersection;
                                            if (rs.get(0).getLatitude().equals(ele.getLatitude()) && rs.get(0).getLongitude().equals(ele.getLongitude())) {
                                                temp = ele;
                                                break;
                                            }
                                        }
                                        grouteEntity.setOrigin(temp.getName());
                                        grouteEntity.setLatitudeOrigin(temp.getLatitude());
                                        grouteEntity.setLongitudeOrigin(temp.getLongitude());

                                        grouteEntity.setIdPlaceDestination(lines.get(j)[0]);
                                        grouteEntity.setDestination(lines.get(j)[3]);
                                        grouteEntity.setLongitudeDestination(Double.parseDouble(lines.get(j)[2]));
                                        grouteEntity.setLatitudeDestination(Double.parseDouble(lines.get(j)[1]));

                                        grouteEntity.setDistance((Double) res_path.get("distance"));
                                        grouteEntity.setTime((Integer) res_path.get("time"));
                                        grouteEntity.setWeight((Double) res_path.get("weight"));
                                        grouteEntity.setTransfer((Integer) res_path.get("transfers"));

                                        grouteEntity.setBbox_1((Double) bbox.get(0));
                                        grouteEntity.setBbox_2((Double) bbox.get(1));
                                        grouteEntity.setBbox_3((Double) bbox.get(2));
                                        grouteEntity.setBbox_4((Double) bbox.get(3));
                                        lst.add(grouteEntity);
                                    }
                                    else {
                                        URL = "https://graphhopper.com/api/1/route?point=" + rs.get(e - 1).getLatitude() + "," + rs.get(e - 1).getLongitude() +
                                                "&point=" + rs.get(e).getLatitude() + "," + rs.get(e).getLongitude() + "&vehicle=car&points_encoded=false&key=" + KEY;
                                        //connect to Graphhoper's API for information of routes
                                        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
                                        String responseBody = response.getBody();
                                        JSONObject resObj = new JSONObject(responseBody);
                                        JSONArray array = resObj.getJSONArray("paths");
                                        JSONObject res_path = array.getJSONObject(0);
                                        JSONArray bbox = res_path.getJSONArray("bbox");
                                        GRouteEntity grouteEntity = new GRouteEntity();

                                        grouteEntity.setIdPlaceOrigin("-1");
                                        IntersectionEntity temp = null;
                                        for (IntersectionEntity intersection: intersections){
                                            IntersectionEntity ele = intersection;
                                            if (rs.get(e - 1).getLatitude().equals(ele.getLatitude()) && rs.get(e - 1).getLongitude().equals(ele.getLongitude())) {
                                                temp = ele;
                                                break;
                                            }
                                        }
                                        grouteEntity.setOrigin(temp.getName());
                                        grouteEntity.setLatitudeOrigin(temp.getLatitude());
                                        grouteEntity.setLongitudeOrigin(temp.getLongitude());

                                        grouteEntity.setIdPlaceDestination("-1");
                                        IntersectionEntity temp_des = null;
                                        for (IntersectionEntity intersection : intersections) {
                                            IntersectionEntity ele = intersection;
                                            if (rs.get(e).getLatitude().equals(ele.getLatitude()) && rs.get(e).getLongitude().equals(ele.getLongitude())) {
                                                temp_des = ele;
                                                break;
                                            }
                                        }
                                        grouteEntity.setDestination(temp_des.getName());
                                        grouteEntity.setLongitudeDestination(temp_des.getLatitude());
                                        grouteEntity.setLatitudeDestination(temp_des.getLongitude());

                                        grouteEntity.setDistance((Double) res_path.get("distance"));
                                        grouteEntity.setTime((Integer) res_path.get("time"));
                                        grouteEntity.setWeight((Double) res_path.get("weight"));
                                        grouteEntity.setTransfer((Integer) res_path.get("transfers"));

                                        grouteEntity.setBbox_1((Double) bbox.get(0));
                                        grouteEntity.setBbox_2((Double) bbox.get(1));
                                        grouteEntity.setBbox_3((Double) bbox.get(2));
                                        grouteEntity.setBbox_4((Double) bbox.get(3));
                                        lst.add(grouteEntity);
                                    }
                                }
                            }
                            gRouteRepository.saveAll(lst);
                        } else {
                            URL = "https://graphhopper.com/api/1/route?point=" + lines.get(i)[1] + "," + lines.get(i)[2] +
                                    "&point="  + lines.get(j)[1] + "," + lines.get(j)[2] + "&vehicle=car&points_encoded=false&key=" + KEY;
                            //connect to Graphhoper's API for information of routes
                            ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
                            String responseBody = response.getBody();
                            JSONObject resObj = new JSONObject(responseBody);
                            JSONArray array = resObj.getJSONArray("paths");
                            JSONObject res_path = array.getJSONObject(0);
                            JSONArray bbox = res_path.getJSONArray("bbox");
                            GRouteEntity grouteEntity = new GRouteEntity();
                            grouteEntity.setIdPlaceOrigin(lines.get(i)[0]);
                            grouteEntity.setOrigin(lines.get(i)[3]);
                            grouteEntity.setLongitudeOrigin(Double.parseDouble(lines.get(i)[2]));
                            grouteEntity.setLatitudeOrigin(Double.parseDouble(lines.get(i)[1]));

                            grouteEntity.setIdPlaceDestination(lines.get(j)[0]);
                            grouteEntity.setDestination(lines.get(j)[3]);
                            grouteEntity.setLongitudeDestination(Double.parseDouble(lines.get(j)[2]));
                            grouteEntity.setLatitudeDestination(Double.parseDouble(lines.get(j)[1]));

                            grouteEntity.setDistance((Double)res_path.get("distance"));
                            grouteEntity.setTime((Integer)res_path.get("time"));
                            grouteEntity.setWeight((Double)res_path.get("weight"));
                            grouteEntity.setTransfer((Integer) res_path.get("transfers"));

                            grouteEntity.setBbox_1((Double)bbox.get(0));
                            grouteEntity.setBbox_2((Double)bbox.get(1));
                            grouteEntity.setBbox_3((Double)bbox.get(2));
                            grouteEntity.setBbox_4((Double)bbox.get(3));
                            gRouteRepository.save(grouteEntity);
                        }
                        list.removeAll(list);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void insertNode() {
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(
                    "D:\\vrp\\vrp\\webserviceapi\\webserviceapi\\src\\main\\java\\webserviceapi\\service\\impl\\node.txt"));
            String line = reader.readLine();
            while (line != null) {
                String[] rs = line.split(" ");
                NodeEntity nodeEntity = new NodeEntity(rs[3], rs[0], Double.parseDouble(rs[1]), Double.parseDouble(rs[2]));
                nodeRepository.save(nodeEntity);
                // read next line
                line = reader.readLine();

            }
            reader.close();
            Iterable<IntersectionEntity> intersections = intersectionRepository.findAll();
            List<IntersectionEntity> _list = StreamSupport
                    .stream(intersections.spliterator(), false)
                    .collect(Collectors.toList());
            for (IntersectionEntity entity: _list) {
                NodeEntity nodeEntity = new NodeEntity(entity.getName(), "-1", entity.getLatitude(), entity.getLongitude());
                nodeRepository.save(nodeEntity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class CoordinateComparer implements Comparator<CoordinateEntity> {
        @Override
        public int compare(CoordinateEntity x, CoordinateEntity y) {
            // TODO: Handle null x or y values
            return y.getId().compareTo(x.getId());
        }
    }
    //lines.get(i)[3], lines.get(j)[3]
    public List<CoordinateEntity> filter(List<CoordinateEntity> list, String origin, String destination) {
        List<CoordinateEntity> rs = new ArrayList<>();
        for (CoordinateEntity ele: list) {
            if (ele.getOrigin().equals(origin) && ele.getDestination().equals(destination)){
                rs.add(ele);
            }
        }
        return rs;
    }

    @Override
    public List<Node> getAllNode() {
        List<Node> returnValue = new ArrayList<>();
        Iterable<NodeEntity> nodes = nodeRepository.findAll();
        List<NodeEntity> lst = StreamSupport
                .stream(nodes.spliterator(), false)
                .collect(Collectors.toList());
        for (int i = 0; i < lst.size(); i++) {
            Node node = new Node();
            BeanUtils.copyProperties(lst.get(i), node);
            returnValue.add(node);
        }
        return returnValue;
    }
}
