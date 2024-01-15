package it.unicam.cs.ids.digitalterritory.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if(authException.getMessage().contains("scaduto")){
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, authException.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.setContentType("application/json");
        response.getOutputStream().println("{ \"error\": \"" + authException.getMessage() + "\" }");
    }
}
