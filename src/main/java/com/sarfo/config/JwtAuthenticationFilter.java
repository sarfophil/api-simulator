package com.sarfo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sarfo.domain.Account;
import com.sarfo.dto.JwtTokenDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final AppProperties appProperties;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,AppProperties appProperties){
        this.authenticationManager = authenticationManager;
        this.appProperties = appProperties;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        Account credential = null;
        List<GrantedAuthority> mapRolesToGrantedAuthorities = null;
        try {
            // Reading user email,password and mapping it to the Credential object
            credential = new ObjectMapper().readValue(request.getInputStream(),Account.class);
            mapRolesToGrantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));

        } catch (IOException e) {
            log.info("Authentication {}", "Login Failed");
        }
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credential.getEmail(),
                        credential.getPassword(),
                        mapRolesToGrantedAuthorities
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        // Extract roles into a proper list
        List<String> roles = authResult.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Creation of Jwt Toke
        String token = JWT.create()
                .withSubject(((UserDetails)authResult.getPrincipal()).getUsername()) // JWT Subject
                .withExpiresAt(new Date(System.currentTimeMillis() + appProperties.getJwt().getExpirationTime()))// Lifetime of the token
                .withIssuer(appProperties.getJwt().getIssuer())
                .withClaim("roles",roles)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC512(appProperties.getJwt().getSecret().getBytes())); // Sign with the secret key

        // Properties to be added to the responses' header
        String headerString = appProperties.getJwt().getHeaderString();
        String tokenPrefix = appProperties.getJwt().getTokenPrefix();

        // Return the Jwt response to the client
        response.addHeader(headerString, tokenPrefix+ token);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(new Gson().toJson(new JwtTokenDto(token)));
    }
}
