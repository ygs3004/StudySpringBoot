package com.yexample.club.security.filter;

import com.yexample.club.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {

    private final AntPathMatcher antPathMatcher;
    private String pattern;
    private JWTUtil jwtUtil;

    public ApiCheckFilter(String pattern, JWTUtil jwtUtil) {
        this.antPathMatcher = new AntPathMatcher();
        this.pattern = pattern;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("REQUEST URI: " + request.getRequestURI());
        log.info(antPathMatcher.match(pattern, request.getRequestURI()));

        if (antPathMatcher.match(pattern, request.getRequestURI())) {
            log.info("Api Check Filter..............................................");

            boolean checkHeader = checkAuthHeader(request);
            if (checkHeader) {
                filterChain.doFilter(request, response);
                return;
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "FAIL CHECK API TOKEN";
                json.put("code", HttpServletResponse.SC_FORBIDDEN);
                json.put("message", message);

                PrintWriter out = response.getWriter();
                out.print(json);
                return;
            }
        }

        filterChain.doFilter(request, response);

    }

    private boolean checkAuthHeader(HttpServletRequest request) {

        boolean checkResult = false;

        String authHeader = request.getHeader("Authorization");
        String tokenPrefix = "Bearer ";
        log.info("checkAuthHeader: " + authHeader);
        int tokenStartIndex = tokenPrefix.length();

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(tokenPrefix)) {
            log.info("Authorization exist: " + authHeader);

            String email = jwtUtil.validateAndExtract(authHeader.substring(tokenStartIndex));
            log.info("Validate Email: " + email);
            checkResult = email.length() > 0;
        }

        return checkResult;
    }

}
