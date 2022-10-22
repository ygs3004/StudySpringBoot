package com.example.firstdemo.controller;

import com.example.dto.BookDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/*
* Form과 View 그리고 thymeleaf로 뷰페이지 만들기
* BookDTO 이용
*/

@Controller
public class SampleController5 {

    @GetMapping("/SampleController5Form")
    public String insertBook(Model model){


        model.addAttribute("bookDTO", new BookDTO());

        return "/post/SampleController5Form";
    }

    @PostMapping("/SampleController5View")
    public String view(BookDTO bookDTO){

        return "/post/SampleController5View";
    }
}
