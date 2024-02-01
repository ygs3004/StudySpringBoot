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

    default Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder().email(dto.getWriterEmail()).build();

        return Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
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
