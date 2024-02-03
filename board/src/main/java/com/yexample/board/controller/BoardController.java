package com.yexample.board.controller;

import com.yexample.board.dto.BoardDTO;
import com.yexample.board.dto.PageRequestDTO;
import com.yexample.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list............" + pageRequestDTO);
        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("list............");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO boardDTO, RedirectAttributes redirectAttributes) {
        log.info("list............" + boardDTO);
        Long bno = boardService.register(boardDTO);
        log.info("BNO: " + bno);
        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(@ModelAttribute("requestDTO") PageRequestDTO requestDTO, Long bno, Model model) {
        log.info("bno: " + bno);
        BoardDTO boardDTO = boardService.get(bno);
        log.info(boardDTO);
        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {
        log.info("bno: " + bno);
        boardService.removeWithReplies(bno);
        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(
            BoardDTO boardDTO,
            @ModelAttribute("requestDTO") PageRequestDTO requestDTO,
            RedirectAttributes redirectAttributes) {
        log.info("post modify............................");
        log.info("boardDTO: " + boardDTO);

        boardService.modify(boardDTO);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/board/read";
    }
}
