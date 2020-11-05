package webserviceapi.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import webserviceapi.entity.RoleEntity;
import webserviceapi.entity.UserEntity;
import webserviceapi.exception.UserServiceException;
import webserviceapi.model.response.ErrorMessages;
import webserviceapi.respository.RoleRepository;
import webserviceapi.respository.UserRepository;
import webserviceapi.security.UserPrincipal;
import webserviceapi.service.UserService;
import webserviceapi.shared.Ultils;
import webserviceapi.shared.dto.UserDto;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    private static final String userRole = "ROLE_USER";

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Ultils ultils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findUserByEmail(user.getEmail()) != null)
            throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserId = ultils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        UserEntity storedUserDetails = userRepository.save(userEntity);
        RoleEntity userRole = roleRepository.findByName(this.userRole);
        storedUserDetails.setRoles(Arrays.asList(userRole));

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByEmail(email);

        if (userEntity == null) throw new UserServiceException(ErrorMessages.EMAIL_NOT_FOUND.getErrorMessage());
        return new UserPrincipal(userEntity);
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findUserByEmail(email);
        if (userEntity == null) throw new UserServiceException(ErrorMessages.EMAIL_NOT_FOUND.getErrorMessage());

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }
}
