package com.yexample.club.service;

import com.yexample.club.entity.ClubMember;
import com.yexample.club.entity.Note;
import com.yexample.club.dto.NoteDTO;

import java.util.List;

public interface NoteService {

    Long register(NoteDTO noteDTO);

    NoteDTO get(Long num);

    void modify(NoteDTO noteDTO);

    void remove(Long num);

    List<NoteDTO> getAllWithWriter(String writerEmail);

    default Note dtoToEntity(NoteDTO noteDTO) {
        return Note.builder()
                .num(noteDTO.getNum())
                .title(noteDTO.getTitle())
                .content(noteDTO.getTitle())
                .writer(ClubMember.builder().email(noteDTO.getWriterEmail()).build())
                .build();
    }

    default NoteDTO entityToDTO(Note note) {
        return NoteDTO.builder()
                .num(note.getNum())
                .title(note.getTitle())
                .content(note.getContent())
                .writerEmail(note.getWriter().getEmail())
                .regData(note.getRegDate())
                .modData(note.getModDate())
                .build();
    }

}
