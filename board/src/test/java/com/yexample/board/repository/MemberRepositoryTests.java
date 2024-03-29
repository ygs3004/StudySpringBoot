package com.yexample.board.repository;

import com.yexample.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers(){
        memberRepository.saveAll(IntStream.rangeClosed(1, 100).mapToObj(i ->
                Member.builder()
                        .email("user" + i + "@aaa.com")
                        .password("1111")
                        .name("USER"+ i)
                        .build()
        ).toList());
    }

}
