package me.cher1shrxd.firstspringboot.global.config;

import lombok.RequiredArgsConstructor;
import me.cher1shrxd.firstspringboot.global.security.jwt.filter.JwtAuthenticationFilter;
import me.cher1shrxd.firstspringboot.global.security.jwt.filter.JwtExceptionFilter;
import me.cher1shrxd.firstspringboot.global.security.jwt.handler.JwtAccessDeniedHandler;
import me.cher1shrxd.firstspringboot.global.security.jwt.handler.JwtAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)

                .exceptionHandling((configurer) -> configurer
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                .authorizeHttpRequests((configurer) -> configurer
                        .requestMatchers(HttpMethod.POST, "/auth/signup", "/auth/login", "/auth/reissue").anonymous()
                        .requestMatchers(HttpMethod.GET, "/auth/me").authenticated()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/chat").permitAll()
                        .requestMatchers(HttpMethod.POST, "/ws/chat").permitAll()
                        .requestMatchers(HttpMethod.GET, "/ws/chat").permitAll()
                        .requestMatchers(HttpMethod.POST, "/chat").permitAll()
                        .requestMatchers(HttpMethod.GET, "/chat").permitAll()
                        .requestMatchers(HttpMethod.GET, "/todos/{todoId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/todos").permitAll()
                        .requestMatchers(HttpMethod.POST, "/todos").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/todos/{todoId}/check").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/todos/{todoId}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/todos/{todoId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/posts/{postId}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/posts").authenticated()
                        .requestMatchers(HttpMethod.POST, "/posts").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/posts/{postId}/check").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/posts/{postId}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/posts/{postId}").authenticated()
                )

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
                .build();
    }


    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}