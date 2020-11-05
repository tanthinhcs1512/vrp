package webserviceapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import webserviceapi.SpringApplicationContext;
import webserviceapi.model.request.Request;
import webserviceapi.model.response.Response;
import webserviceapi.service.UserService;
import webserviceapi.shared.dto.UserDto;
import webserviceapi.shared.dto.UserRespDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            Request creds = new ObjectMapper()
                    .readValue(req.getInputStream(), Request.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUser().getEmail(),
                    creds.getUser().getPassword(),
                    new ArrayList<>()));
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String username = ((UserPrincipal) auth.getPrincipal()).getUsername();

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPERATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                .compact();
        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");

        UserDto userDto = userService.getUser(username);
        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        response.addHeader("UserID", userDto.getUserId());
        response.setStatus(HttpStatus.OK.value());
        UserRespDto user = new UserRespDto();
        user.setEmail(userDto.getEmail());
        user.setToken(SecurityConstants.TOKEN_PREFIX + token);
        user.setUserId(userDto.getUserId());
        Response resp = new Response();
        resp.setUser(user);
        String json = new ObjectMapper().writeValueAsString(resp);
        response.getWriter().write(json);
        response.flushBuffer();
    }
}
