package com.yexample.club.security.service;

import com.yexample.club.entity.ClubMember;
import com.yexample.club.repository.ClubMemberRepository;
import com.yexample.club.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {

    private final ClubMemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("ClubUserDetailsService loadUserByUsername: " + username);

        Optional<ClubMember> result = clubMemberRepository.findByEmail(username, false);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("Check Email or Social");
        }

        ClubMember clubMember = result.get();
        log.info("=================== ClubUserDetailsService clubMember =====================");
        log.info(clubMember);

        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList())
        );

        clubAuthMemberDTO.setName(clubMember.getName());

        return clubAuthMemberDTO;
    }
}
