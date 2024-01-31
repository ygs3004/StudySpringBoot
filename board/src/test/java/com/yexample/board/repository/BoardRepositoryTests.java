package com.yexample.board.repository;

import com.yexample.board.entity.Board;
import com.yexample.board.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
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
    @Transactional
    public void testRead1(){
        boardRepository.findById(100L).ifPresent(board -> {
            System.out.println(board);
            System.out.println(board.getWriter());
        });
    }

    @Test
    public void testGetReadWithWriter(){
        Object row = boardRepository.getBoardWithWriter(100L);
        Object[] result = (Object[])row;

        System.out.println("===============================");
        System.out.println(row.toString());
        System.out.println(Arrays.toString(result));
    }

    @Test
    public void testGetBoardWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(99L);
        result.forEach(replyArr -> {
            System.out.println(Arrays.toString(replyArr));
        });
    }

}
