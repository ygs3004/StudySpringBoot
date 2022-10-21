package com.example.firstdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SampleController2 {

    @PostMapping ("/post")
    //public String demoPost(@RequestBody String req, Model model){
    public String demoPost(
            @RequestParam String membername,
            @RequestParam String memberid,
            @RequestParam String memberemail,
            Model model){

        System.out.println("post 전달 성공");
        // System.out.println(req); // @RequestBody 는 전달된 테이터를 일괄로 묶어서 저장
        // model.addAttribute("req", req);

        model.addAttribute("membername", membername);
        model.addAttribute("memberid", memberid);
        model.addAttribute("memberemail",memberemail);

        return "/post/post";
    }

}
