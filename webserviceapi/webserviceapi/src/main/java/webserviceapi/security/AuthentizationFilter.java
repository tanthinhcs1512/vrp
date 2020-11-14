package webserviceapi.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import webserviceapi.AppRouteConstant;
import webserviceapi.entity.RoleEntity;
import webserviceapi.entity.UserEntity;
import webserviceapi.repository.RoleRepository;
import webserviceapi.repository.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class AuthentizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public AuthentizationFilter(AuthenticationManager authenticationManager,
                                UserRepository userRepository,
                                RoleRepository roleRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
        throws IOException, ServletException {

        String header = req.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            filterChain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(req, res);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        System.out.println(req.getMethod());
        String token = req.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null) {
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
            String email = Jwts.parser()
                    .setSigningKey(SecurityConstants.TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            if (email != null) {
                    UserEntity userEntity = userRepository.findUserByEmail(email);
                UserPrincipal userPrincipal = new UserPrincipal(userEntity);
                System.out.println(req.getRequestURL().toString());
                List<String> rolesNames =  userPrincipal.userEntity.getRoles().stream()
                        .map(RoleEntity::getName)
                        .collect(Collectors.toList());
                //delete after complete
                if (rolesNames.contains("ROLE_ADMIN"))
                    return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                String[] routes = AppRouteConstant.routes;
                String curRoute = req.getRequestURL().toString().replace("http://localhost:8000/", "");
                if (SecurityConstants.CHECK_AUTHORIZATION.equals(curRoute)) {
                    return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                }
                String[] subCurRoute = curRoute.split("/");
                if (Arrays.asList(subCurRoute).contains(SecurityConstants.ROUTES)){
                    return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                }
                String route = "";
                if (subCurRoute.length == 0) return null;
                for (int index = subCurRoute.length - 1; index >= 0; index--) {
                    if (Arrays.asList(routes).contains(subCurRoute[index])){
                        route = subCurRoute[index];
                        List<RoleEntity> roleEntities = roleRepository.findRoleByScreenPath(route);
                        if (roleEntities.size() > 0) {
                            List<String> roleNamesString = new ArrayList<>();
                            for (RoleEntity r : roleEntities) {
                                roleNamesString.add(r.getName());
                            }
                            List<String> roleNames =  userPrincipal.userEntity.getRoles().stream()
                                    .map(RoleEntity::getName)
                                    .collect(Collectors.toList());
                            List<String> intersect = roleNames.stream()
                                    .filter(roleNamesString::contains)
                                    .collect(Collectors.toList());
                            if (intersect != null & intersect.size() <= 0) return null;
                            return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                        }
                    }
                }
                return null;
            }
            return null;
        }
        return null;
    }

}
