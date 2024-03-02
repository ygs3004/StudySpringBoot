package com.yexample.club.config;

import com.yexample.club.security.filter.ApiCheckFilter;
import com.yexample.club.security.filter.ApiLoginFilter;
import com.yexample.club.security.handler.ApiLoginFailHandler;
import com.yexample.club.security.handler.ClubLoginSuccessHandler;
import com.yexample.club.security.service.ClubUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
@Log4j2
public class SecurityConfig {

    private final ClubUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        httpSecurity.authenticationManager(authenticationManager);

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

        httpSecurity.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(apiLoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public ClubLoginSuccessHandler successHandler() {
        return new ClubLoginSuccessHandler(passwordEncoder());
    }

    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter("/notes/**/*");
    }

    public ApiLoginFilter apiLoginFilter(AuthenticationManager authenticationManager) {
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login");
        apiLoginFilter.setAuthenticationManager(authenticationManager);
        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());
        return apiLoginFilter;
    }

}