package com.yexample.board.service;

import com.yexample.board.dto.BoardDTO;
import com.yexample.board.dto.PageRequestDTO;
import com.yexample.board.dto.PageResultDTO;
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

    @Test
    public void boardListTest() {
        PageRequestDTO pageResultDTO = new PageRequestDTO();
        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageResultDTO);
        result.getDtoList().forEach(System.out::println);
    }

}
