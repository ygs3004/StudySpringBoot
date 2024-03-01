package com.yexample.club.security.handler;

import com.yexample.club.security.dto.ClubAuthMemberDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Log4j2
public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private PasswordEncoder passwordEncoder;

    public ClubLoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("===============================");
        log.info("onAuthenticationSuccess");

        ClubAuthMemberDTO authMemberDTO = (ClubAuthMemberDTO) authentication.getPrincipal();
        boolean fromSocial = authMemberDTO.isFromSocial();
        String defaultPassword = "1111";
        boolean passwordResult = passwordEncoder.matches(defaultPassword, authMemberDTO.getPassword());
        boolean needModify = fromSocial && passwordResult;
        log.info("Need Modify Member ? " + needModify);
        log.info("fromSocial: " + fromSocial);
        log.info("passwordResult: " + passwordResult);
        log.info("authMemberDTO: " + authMemberDTO);
        log.info("passwordResult: " + authMemberDTO.getPassword());
        log.info("passwordResult: " + defaultPassword);

        if (needModify) {
            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
        }

    }

}
