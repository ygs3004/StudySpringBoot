package com.yexample.board.service;

import com.yexample.board.dto.BoardDTO;
import com.yexample.board.dto.PageRequestDTO;
import com.yexample.board.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    public void boardGetTest() {
        BoardDTO boardDTO = boardService.get(99L);
        System.out.println(boardDTO);
    }

    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("제목 변경")
                .content("내용 변경")
                .build();

        boardService.modify(boardDTO);
    }

    @Test
    public void testRemove() {
        boardService.removeWithReplies(1L);
    }

}
