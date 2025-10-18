package com.exemplo.demo.configs;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import com.exemplo.demo.security.JWTAuthenticationFilter;
// import com.exemplo.demo.security.JWTAuthorizationFilter;
// import com.exemplo.demo.security.JWTUtil;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // private AuthenticationManager authenticationManager;

    // @Autowired
    // private UserDetailsService userDetailsService;

    @Autowired
    // private JWTUtil jwtUtil;

    private static final String[] PUBLIC_MATCHERS = {
        "/"
    };
    private static final String[] PUBLIC_MATCHERS_POST = {
        "/user",
        "/login"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.disable()) // Desativa CORS
                .csrf(csrf -> csrf.disable()); // Desativa CSRF

        //     AuthenticationManagerBuilder authenticationManagerBuilder = http
        //             .getSharedObject(AuthenticationManagerBuilder.class);
        //     authenticationManagerBuilder.userDetailsService(this.userDetailsService)
        //             .passwordEncoder(bCryptPasswordEncoder());
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
                .requestMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated()
        );
        // .authenticationManager(authenticationManager)
        // .addFilter(new JWTAuthenticationFilter(authenticationManager, jwtUtil))
        // .addFilter(new JWTAuthorizationFilter(authenticationManager, jwtUtil, userDetailsService));
        http.sessionManagement(session
                -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
