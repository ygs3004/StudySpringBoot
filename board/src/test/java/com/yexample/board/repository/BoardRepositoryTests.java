package com.yexample.board.repository;

import com.yexample.board.entity.Board;
import com.yexample.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard(){
        boardRepository.saveAll(IntStream.rangeClosed(1, 100).mapToObj(i ->{
            Member member = Member.builder().email("user"+ i + "@aaa.com").build();

            return Board.builder()
                    .title("Title...." + i)
                    .content("Content...." + i)
                    .writer(member)
                    .build();
                }
        ).toList());
    }

    @Test
    public void testRead1(){

        boardRepository.findById(100L).ifPresent(board -> {
            System.out.println(board);
            System.out.println(board.getWriter());
        });
    }

}
