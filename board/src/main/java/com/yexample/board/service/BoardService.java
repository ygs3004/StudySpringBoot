package com.yexample.board.service;

import com.yexample.board.dto.BoardDTO;
import com.yexample.board.dto.PageRequestDTO;
import com.yexample.board.dto.PageResultDTO;
import com.yexample.board.entity.Board;
import com.yexample.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    BoardDTO get(Long bno);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    void removeWithReplies(Long bno);

    void modify(BoardDTO boardDTO);

    default Board dtoToEntity(BoardDTO boardDTO) {
        Member member = Member.builder().email(boardDTO.getWriterEmail()).build();

        return Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(member)
                .build();
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount){
        return BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();
    }

}
