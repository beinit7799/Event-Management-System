package com.EventManagementSystem.EventManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/css/**","/css/admin/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/user/**").hasRole("USER")

                        .requestMatchers("/admin/**","/css/admin/**" ).hasRole("ADMIN")
                        .requestMatchers("/sign-in","/sign-up","/api/events/**","/api/events/express-interest/{userId}")

                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/sign-in")
                        .successHandler(customAuthenticationSuccessHandler)
                        .permitAll())
                .authenticationProvider(authenticationProvider())
//                .logout(logout -> logout
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Logout endpoint
//                        .invalidateHttpSession(true) // Invalidate session
//                        .clearAuthentication(true) // Clear authentication details
//                        .deleteCookies("JSESSIONID") // Optional: Clear cookies
//                        .logoutSuccessHandler((request, response, authentication) -> {
//                            response.setStatus(200); // Custom response on successful logout
//                            response.getWriter().write("Logout successful");
//                        }))
                .build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }
}
