package com.creche.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.creche.security.user.Permission.ADMIN_CREATE;
import static com.creche.security.user.Permission.ADMIN_DELETE;
import static com.creche.security.user.Permission.ADMIN_READ;
import static com.creche.security.user.Permission.ADMIN_UPDATE;
import static com.creche.security.user.Permission.GUEST_CREATE;
import static com.creche.security.user.Permission.GUEST_DELETE;
import static com.creche.security.user.Permission.GUEST_READ;
import static com.creche.security.user.Permission.GUEST_UPDATE;
import static com.creche.security.user.Role.ADMIN;
import static com.creche.security.user.Role.GUEST;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
    "/api/v1/users/**"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/children/**").permitAll()
                                .requestMatchers("/api/v1/guest/**").hasAnyRole(ADMIN.name(), GUEST.name())
                                .requestMatchers(GET, "/api/v1/guest/**").hasAnyAuthority(ADMIN_READ.name(), GUEST_READ.name())
                                .requestMatchers(POST, "/api/v1/guest/**").hasAnyAuthority(ADMIN_CREATE.name(), GUEST_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/guest/**").hasAnyAuthority(ADMIN_UPDATE.name(), GUEST_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/guest/**").hasAnyAuthority(ADMIN_DELETE.name(), GUEST_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }
}
