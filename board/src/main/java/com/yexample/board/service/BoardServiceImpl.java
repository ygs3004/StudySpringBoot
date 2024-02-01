package com.yexample.board.service;

import com.yexample.board.dto.BoardDTO;
import com.yexample.board.entity.Board;
import com.yexample.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public Long register(BoardDTO dto) {
        log.info("board DTO: " + dto);
        Board board = dtoToEntity(dto);
        boardRepository.save(board);
        return board.getBno();
    }
}
