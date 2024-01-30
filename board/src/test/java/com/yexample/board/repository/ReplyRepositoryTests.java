package com.yexample.board.repository;

import com.yexample.board.entity.Board;
import com.yexample.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;


@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertMembers(){
        replyRepository.saveAll(IntStream.rangeClosed(1, 300).mapToObj(i -> {
                    long bno = (long) (Math.random() * 100) + 1;
                    Board board = Board.builder().bno(bno).build();

                    return Reply.builder()
                            .text("댓글 " + i)
                            .board(board)
                            .replyer("Guest")
                            .build();
                }
        ).toList());
    }

    @Test
    public void readReply1(){
        replyRepository.findById(1L).ifPresent(reply -> {
            System.out.println(reply);
            System.out.println(reply.getBoard());
        });
    }

}
