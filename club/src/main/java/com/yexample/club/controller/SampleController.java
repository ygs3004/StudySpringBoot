package com.yexample.club.controller;

import com.yexample.club.security.dto.ClubAuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    // 비 로그인 유저
    @PreAuthorize("permitAll()")
    @GetMapping("/all")
    public void exAll(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        log.info("exAll .......................");
        log.info("clubAuthMemberDTO: " + clubAuthMemberDTO);
    }

    // 로그인유저
    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        log.info("exMember .......................");
        log.info("clubAuthMemberDTO: " + clubAuthMemberDTO);
    }

    // 관리자
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        log.info("exAdmin .......................");
        log.info("clubAuthMemberDTO: " + clubAuthMemberDTO);
    }

    @PreAuthorize("#clubAuthMemberDTO != null && #clubAuthMemberDTO.username eq \"user75@yexample.com\"")
    @GetMapping("/exOnly")
    public String exOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        log.info("exAdmin .......................");
        log.info("clubAuthMemberDTO: " + clubAuthMemberDTO);

        return "/sample/admin";
    }
}
