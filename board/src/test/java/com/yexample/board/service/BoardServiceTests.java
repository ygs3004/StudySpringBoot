package com.yexample.board.service;

import com.yexample.board.dto.BoardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    public BoardService boardService;

    @Test
    public void boardRegisterTest() {

        BoardDTO dto = BoardDTO.builder()
                .title("Title Test")
                .content("Content Test")
                .writerEmail("user1@aaa.com")
                .build();

        Long bno = boardService.register(dto);
    }

}
