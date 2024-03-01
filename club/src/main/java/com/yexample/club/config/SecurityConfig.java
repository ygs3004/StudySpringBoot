package com.yexample.club.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user1")
//                .password(passwordEncoder().encode("1"))
//                .roles("USER")
//                .build();
//
//        log.info("userDetailService.............................");
//        log.info(user);
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/sample/all").permitAll();
            auth.requestMatchers("/sample/member").hasRole("USER");
            auth.requestMatchers("/sample/member").hasRole("ANONYMOUS");
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
        });

        return httpSecurity.build();
    }

}