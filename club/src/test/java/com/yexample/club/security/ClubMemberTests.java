package com.yexample.club.security;

import com.yexample.club.entity.ClubMember;
import com.yexample.club.repository.ClubMemberRepository;
import com.yexample.club.entity.ClubMemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberTests {

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies() {

        // 1 ~ 80 -> USER
        // 81 ~ 90 -> USER, MANAGER
        // 91 ~ 100 -> USER, MANAGER, ADMIN

        IntStream.rangeClosed(1, 100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user" + i + "@yexample.com")
                    .name("사용자" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            clubMember.addMemberRole(ClubMemberRole.USER);
            if (i > 80) clubMember.addMemberRole(ClubMemberRole.MANAGER);
            if (i > 90) clubMember.addMemberRole(ClubMemberRole.ADMIN);

            clubMemberRepository.save(clubMember);
        });

    }

    @Test
    public void testRead() {
        Optional<ClubMember> result = clubMemberRepository.findByEmail("user95@yexample.com", false);

        result.ifPresent(System.out::print);
    }


}
