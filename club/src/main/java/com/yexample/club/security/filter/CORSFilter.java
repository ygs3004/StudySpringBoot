package com.yexample.club.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String[] allowHeadersArr = {
                HttpHeaders.ORIGIN,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                HttpHeaders.AUTHORIZATION,
                "Key",
                "X-Requested-With"
        };
        String allowHeader = String.join(", ", allowHeadersArr);

        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, allowHeader);

        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            filterChain.doFilter(request, response);
        }
    }

}
