package com.ecommerce.ecommerce.Security.Filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.ecommerce.Entity.User;
import com.ecommerce.ecommerce.Utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    private JwtUtils jwtUtils;

    

    public AuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = "";
        String password = "";
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();

        } catch (Exception e) {
            e.printStackTrace();
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return getAuthenticationManager().authenticate(token);
        }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
            
            User user = (User) authResult.getPrincipal();
            String jwt = "";
      
            jwt = jwtUtils.generateAccesToken(user.getUsername());
          
            response.addHeader("Authorization", jwt);

            Map<String, Object> httpResponse = new HashMap<>();
            httpResponse.put("token", jwt);
            httpResponse.put("Message", "Autenticacion correcta");
            httpResponse.put("User", user.getUsername());
            httpResponse.put("id",user.getId());
            response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));

            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().flush();

            super.successfulAuthentication(request, response, chain, authResult);
    }  
    
    
}
