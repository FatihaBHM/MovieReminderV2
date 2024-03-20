package org.lafabrique_epita.exposition.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Service
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper;

    public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwtToken = null;
            boolean isTokenExpired = true;
            String username = null;
            // "Authorization" : "Bearer jwtzdnezlkfezlkjdzldj..."
            String bearer = request.getHeader("Authorization");
            System.out.println(bearer);
            if (bearer != null && bearer.startsWith("Bearer") && bearer.length() > 7) {
                jwtToken = bearer.substring(7);

                isTokenExpired = jwtService.isTokenExpired(jwtToken);
                username = jwtService.extractUsername(jwtToken);
            }

            if (!isTokenExpired && username != null && (SecurityContextHolder.getContext().getAuthentication() == null)) {
                UserDetails user = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

            filterChain.doFilter(request, response);
        }catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            Map<String, ?> errors = Map.of("status", HttpServletResponse.SC_UNAUTHORIZED, "errorMessage", "JWT expired");
            response.getWriter().write(objectMapper.writeValueAsString(errors));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            Map<String, ?> errors = Map.of("status", HttpServletResponse.SC_BAD_REQUEST, "errorMessage", "Invalid JWT");
            response.getWriter().write(objectMapper.writeValueAsString(errors));
        }
    }
}

