package com.yexample.club.security.service;

import com.yexample.club.entity.ClubMember;
import com.yexample.club.entity.ClubMemberRole;
import com.yexample.club.repository.ClubMemberRepository;
import com.yexample.club.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        log.info("========================================");
        log.info("userRequest: " + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName: " + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("========================================");
        oAuth2User.getAttributes().forEach( (key, value) -> {
            log.info(key + ": " + value);
        });

        String email = null;
        if(clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
        }

        log.info("EMAIL: " + email);
        ClubMember member = saveSocialMember(email);

        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
          member.getEmail(),
          member.getPassword(),
          true,
          member.getRoleSet().stream().map(
                  role -> new SimpleGrantedAuthority("ROLE_" + ClubMemberRole.USER)
          ).collect(Collectors.toList()),
          oAuth2User.getAttributes()
        );


        log.info("Social Member: " + member);

        clubAuthMemberDTO.setName(member.getName());

        return clubAuthMemberDTO;
    }

    private ClubMember saveSocialMember(String email) {

        Optional<ClubMember> result = clubMemberRepository.findByEmail(email, true);
        if (result.isPresent()) {
            return result.get();
        }

        ClubMember clubMember = ClubMember.builder()
                .email(email)
                .name(email)
                .password(passwordEncoder.encode("1111"))
                .fromSocial(true)
                .build();

        clubMember.addMemberRole(ClubMemberRole.USER);
        log.info("saved Member: " + clubMember);
        clubMemberRepository.save(clubMember);

        return clubMember;
    }

}
