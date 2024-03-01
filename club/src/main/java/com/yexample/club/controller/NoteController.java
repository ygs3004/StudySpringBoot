package com.yexample.club.controller;

import com.yexample.club.dto.NoteDTO;
import com.yexample.club.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping()
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO) {
        log.info("=================================================");
        log.info(noteDTO);
        Long num = noteService.register(noteDTO);
        return new ResponseEntity<>(num, HttpStatus.OK);
    }

}
