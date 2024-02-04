package com.yexample.board.service;

import com.yexample.board.dto.BoardDTO;
import com.yexample.board.dto.PageRequestDTO;
import com.yexample.board.dto.PageResultDTO;
import com.yexample.board.entity.Board;
import com.yexample.board.entity.Member;
import com.yexample.board.repository.BoardRepository;
import com.yexample.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        log.info("board DTO: " + dto);
        Board board = dtoToEntity(dto);
        boardRepository.save(board);
        return board.getBno();
    }

    @Override
    public BoardDTO get(Long bno) {
        Object[] entity = (Object[]) boardRepository.getBoardByBno(bno);
        return entityToDTO((Board) entity[0], (Member) entity[1], (Long) entity[2]);
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info("pageRequestDTO: " + pageRequestDTO);
         Function<Object[], BoardDTO> fn = entity -> entityToDTO((Board)entity[0], (Member)entity[1], (Long)entity[2]);

        // Page<Object[]> board = boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = boardRepository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending())
        );
        return new PageResultDTO<>(result, fn);
    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = boardRepository.getReferenceById(boardDTO.getBno());
        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());
        log.info("board :" + board);
        boardRepository.save(board);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        boardRepository.deleteById(bno);
    }

}
