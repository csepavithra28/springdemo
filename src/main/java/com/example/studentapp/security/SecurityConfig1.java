package com.example.studentapp.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.studentapp.service.CustomUserDetails;

@Configuration
public class SecurityConfig1 {

    @Autowired
    private CustomUserDetails userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**","/js/**","/images/**","/uploads/**","/register","/login").permitAll()
                .requestMatchers("/courses/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                .requestMatchers("/students/delete/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .successHandler((request, response, authentication) -> {
                    boolean isAdmin = authentication.getAuthorities()
                            .stream()
                            .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

                    if (isAdmin) {
                        response.sendRedirect("/admin/dashboard");
                    } else {
                        response.sendRedirect("/user/dashboard");
                    }
                })

            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .userDetailsService(userDetailsService)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
