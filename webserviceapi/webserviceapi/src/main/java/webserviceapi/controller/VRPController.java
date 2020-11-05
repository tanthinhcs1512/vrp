package webserviceapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vrp")
public class VRPController {

    @GetMapping("/input")
    public String map() {
        return "map";
    }
}
