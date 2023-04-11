package br.com.gabezk.achadoseperdidos.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String[] AUTH_WHITELIST = {
            // -- Endpoints
            "/api/post/allApproved",
            "/api/post/allByCity",
            "/api/post/byId",
            "/api/post/allByUserAndStatus",
            // -- Swagger UI v3
            "/v3/api-docs/**",
            "v3/api-docs/**",
            "/swagger-ui/**",
            "swagger-ui/**"
    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/api/user/byId").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/post", "/api/user").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST,"/api/auth/register", "/api/auth/authenticate").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/user", "/api/user/pass", "/api/post").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/post/byUserId").hasAnyAuthority("USER", "ADMIN")

                .requestMatchers(HttpMethod.GET, AUTH_WHITELIST).permitAll()

                .requestMatchers(HttpMethod.GET, "/api/user", "/api/user/exists/phone", "/api/user/exists/id", "/api/user/exists/email", "/api/user/byPhone", "/api/user/byEmail", "/api/user/allByRoIe", "/api/post", "/api/post/allByUserId", "/api/post/allByStatus").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/user/role", "/api/post/status").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/user", "/api/post").hasAuthority("ADMIN")


                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

        return http.build();
    }
}
