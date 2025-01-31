package com.example.Security.Salon.Config;

import com.example.Security.Salon.Utils.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthFilter  extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;


    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String userId = null;
        String role = null ;
        String jwtToken = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            userId = jwtService.getSubject(jwtToken);
            role = jwtService.getClaimsRole(jwtToken);
        }
        System.out.println("JWT Token: " + jwtToken);
        System.out.println("Extracted UserId: " + userId);
        System.out.println("Extracted Role: " + role);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
                UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserById(UUID.fromString(userId));
                if (jwtService.isTokenValid(jwtToken, userId)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }

        }
        filterChain.doFilter(request, response);
    }


}
