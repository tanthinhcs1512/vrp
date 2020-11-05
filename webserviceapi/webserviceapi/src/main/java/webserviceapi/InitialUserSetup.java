package webserviceapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import webserviceapi.entity.RoleEntity;
import webserviceapi.entity.UserEntity;
import webserviceapi.respository.RoleRepository;
import webserviceapi.respository.UserRepository;
import webserviceapi.shared.Ultils;

import java.util.Arrays;

@Component
public class InitialUserSetup {

    private static final String adminRole = "ROLE_ADMIN";
    private static final String userRole = "ROLE_USER";

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Ultils ultils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;


    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From application ready event...");
        RoleEntity adminRole =  createRole(this.adminRole);
        RoleEntity userRole =  createRole(this.userRole);

        if (userRepository.findUserByEmail("thinhtt4@fpt") == null) {
            UserEntity adminUser = new UserEntity();
            adminUser.setEmail("thinhtt4@fpt");
            adminUser.setFirstName("thinh");
            adminUser.setLastName("tran");
            adminUser.setUserId(ultils.generateUserId(30));
            adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("12345678"));
            adminUser.setRoles(Arrays.asList(adminRole));
            userRepository.save(adminUser);
        }
    }

    private RoleEntity createRole(String name) {
        RoleEntity roleEntity = roleRepository.findByName(name);
        if (roleEntity == null) {
            roleEntity = new RoleEntity(name);
            roleRepository.save(roleEntity);
        }
        return roleEntity;
    }
}
