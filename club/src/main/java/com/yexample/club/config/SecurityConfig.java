package com.yexample.club.config;

import com.yexample.club.security.handler.ClubLoginSuccessHandler;
import com.yexample.club.security.service.ClubUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {

    private final ClubUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/sample/all").permitAll();
            auth.requestMatchers("/sample/member").hasRole("USER");
            auth.requestMatchers("/sample/admin").hasRole("ADMIN");
        });

        // httpSecurity.formLogin(); -> Deprecate
        httpSecurity.formLogin(formLogin -> {
            formLogin
                    .usernameParameter("username")
                    .passwordParameter("password");
        });

        // 세션마다 생성되는 토큰값 disabled
        // 외부에서의 API 요청을 인정할때는 disabled
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.logout(logoutConfigurer -> {
        });

        httpSecurity.oauth2Login(oAuth2LoginConfigurer -> {
            oAuth2LoginConfigurer.successHandler(successHandler());
        });

        httpSecurity.rememberMe(rememberMeConfigurer -> {
            rememberMeConfigurer.tokenValiditySeconds(60 * 60 * 24 * 7);
            rememberMeConfigurer.userDetailsService(userDetailsService);
        });

        return httpSecurity.build();
    }

    @Bean
    public ClubLoginSuccessHandler successHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }

}