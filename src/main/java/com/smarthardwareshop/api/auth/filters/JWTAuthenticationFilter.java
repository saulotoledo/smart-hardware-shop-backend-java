package com.smarthardwareshop.api.auth.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarthardwareshop.api.users.auth.UserDetailsImpl;
import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

/**
 * A JWT authentication filter. It defines the login URL and prepares the user token in case
 * of a successful login.
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * The authentication manager.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * The authentication expiration time.
     */
    private final int expirationTime;

    /**
     * The authentication token secret.
     */
    private final String tokenSecret;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, int expirationTime, String tokenSecret) {
        this.authenticationManager = authenticationManager;
        this.expirationTime = expirationTime;
        this.tokenSecret = tokenSecret;
        this.setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
        throws AuthenticationException {

        try {
            String requestStr = IOUtils.toString(req.getInputStream());
            JsonNode jsonNode = new ObjectMapper().readTree(requestStr);
            String username = jsonNode.get("username").asText();
            String password = jsonNode.get("password").asText();

            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    Arrays.asList()
                )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
        Authentication auth) throws IOException {

        String username = ((UserDetailsImpl) auth.getPrincipal()).getUsername();
        String token = JWT.create()
            .withSubject(username)
            .withExpiresAt(new Date(System.currentTimeMillis() + this.expirationTime))
            .sign(Algorithm.HMAC512(this.tokenSecret.getBytes()));

        String body = username + " " + token;

        res.getWriter().write(body);
        res.getWriter().flush();
    }
}
