package com.yexample.mreview.repository;

import com.yexample.mreview.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMember(){

        IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder()
                    .email("y" + i + "@naver.com")
                    .pw("1111")
                    .nickname("reviewer" + i)
                    .build();

            memberRepository.save(member);
        });

    }

    @Test
    @Commit
    @Transactional
    public void testDeleteMember() {

        Long mid = 2L;

        Member member = Member.builder().mid(mid).build();

        // 연관관계의 Delete 가 N+1 형태로 일어남 -> @Query 이용하여 DELETE 문 직접 작성이 좋음
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }

}
