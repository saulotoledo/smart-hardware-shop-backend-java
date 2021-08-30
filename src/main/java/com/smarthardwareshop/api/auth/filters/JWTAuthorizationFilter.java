package com.smarthardwareshop.api.auth.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A JWT authorization filter.
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    /**
     * Token prefix.
     */
    private static final String TOKEN_PREFIX = "Bearer ";

    /**
     * The authorization header string.
     */
    private static final String HEADER_STRING = "Authorization";

    /**
     * The user details service.
     */
    private final UserDetailsService userDetailsService;

    /**
     * The authentication token secret.
     */
    private final String tokenSecret;

    /**
     * Constructor.
     *
     * @param authManager The authentication manager.
     * @param userDetailsService The user details service.
     * @param tokenSecret The token secret.
     */
    public JWTAuthorizationFilter(AuthenticationManager authManager, UserDetailsService userDetailsService, String tokenSecret) {
        super(authManager);
        this.userDetailsService = userDetailsService;
        this.tokenSecret = tokenSecret;
    }

    /**
     * Filtering to verify token.
     *
     * @param req HTTP servlet request.
     * @param res HTTP servlet response.
     * @param chain The filter chain.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String header = req.getHeader(this.HEADER_STRING);

        if (header != null && header.startsWith(this.TOKEN_PREFIX)) {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(req, res);
    }

    /**
     * Gets the JWT token from the Authorization header and validates it.
     *
     * @param request The HTTP servlet request.
     * @return The authentication token or null if the token does not exist.
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(this.HEADER_STRING);

        if (token != null) {
            String username = JWT.require(Algorithm.HMAC512(this.tokenSecret.getBytes()))
                .build()
                .verify(token.replace(this.TOKEN_PREFIX, ""))
                .getSubject();

            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null,
                    this.userDetailsService.loadUserByUsername(username).getAuthorities());
            }
        }

        return null;
    }
}
