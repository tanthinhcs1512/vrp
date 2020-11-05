package webserviceapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webserviceapi.AppRouteConstant;
import webserviceapi.entity.RoleEntity;
import webserviceapi.entity.UserEntity;
import webserviceapi.respository.RoleRepository;
import webserviceapi.respository.UserRepository;
import webserviceapi.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/checkAuth")
    public Boolean checkAuth(Principal principal, @RequestParam("screenId") String screenId) {
        String[] subCurRoute = screenId.split("/");
        String route = "";
        if (subCurRoute.length == 0) return null;
        for (int index = subCurRoute.length - 1; index >= 2; index--) {
            if (Arrays.asList(AppRouteConstant.routes).contains(subCurRoute[index])){
                route = subCurRoute[index];
                List<RoleEntity> roleEntities = roleRepository.findRoleByScreenPath(route);
                if (roleEntities.size() > 0) {
                    List<String> roleNamesString = new ArrayList<>();
                    for (RoleEntity r : roleEntities) {
                        roleNamesString.add(r.getName());
                    }
                    UserEntity user =  userRepository.findUserByEmail(principal.getName());
                    List<String> roleNames =  user.getRoles().stream()
                            .map(RoleEntity::getName)
                            .collect(Collectors.toList());
                    List<String> intersect = roleNames.stream()
                            .filter(roleNamesString::contains)
                            .collect(Collectors.toList());
                    if (intersect != null & intersect.size() <= 0) return false;

                    return true;
                }
            }
        }
        return false;
    }
}
