package com.smarthardwareshop.api.configuration;

import com.smarthardwareshop.api.auth.filters.JWTAuthenticationFilter;
import com.smarthardwareshop.api.auth.filters.JWTAuthorizationFilter;
import com.smarthardwareshop.api.users.auth.UserDetailsServiceImpl;
import com.smarthardwareshop.api.users.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // TODO: Use the JWT filters with dependency injection and move the values below to them as needed.

    /**
     * The authentication expiration time.
     */
    @Value("${auth.expiration_time}")
    private int expirationTime;

    /**
     * Authentication token secret.
     */
    @Value("${auth.secret}")
    private String tokenSecret;

    /**
     * Returns the default password encoder.
     *
     * @return The default password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Returns the user details service.
     *
     * @return The user details service.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**
     * Prepares and returns the authentication provider.
     *
     * @return The authentication provider.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Configures the user details service and the password encoder.
     *
     * @param auth The authentication manager builder.
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService()).passwordEncoder(this.passwordEncoder());
    }

    /**
     * Configures the routes permissions.
     *
     * @param http The HTTP security object.
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors().disable()
            .authorizeRequests()
                .antMatchers(new String[] {
                    "/swagger-ui.html",
                    "/webjars/**",
                    "/v2/**",
                    "/swagger-resources/**",
                    "/v3/api-docs/**",
                    "/swagger-ui/**"
                }).permitAll()
                .antMatchers(HttpMethod.GET, "/news/**").permitAll()
                .antMatchers(HttpMethod.GET, "/products/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()
                .antMatchers("/users/*/orders/**").hasAnyAuthority(Role.CUSTOMER.toString())
                .antMatchers("/news/**").hasAnyAuthority(Role.ADMIN.toString())
                .antMatchers("/products/**").hasAnyAuthority(Role.ADMIN.toString())
                .antMatchers("/users/**").hasAnyAuthority(Role.ADMIN.toString())
            .anyRequest().authenticated()
            .and()
                .addFilter(new JWTAuthenticationFilter(
                        this.authenticationManager(), this.expirationTime, this.tokenSecret))
                .addFilter(new JWTAuthorizationFilter(
                        this.authenticationManager(), this.userDetailsService(), this.tokenSecret))
                // disabling session creation:
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
