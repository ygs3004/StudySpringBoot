package com.example.firstdemo.controller;

import com.example.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/*
* 스프링 부트 객체 전달 Form과 View를 template 폴더내에서 처리
*/

@Controller
public class SampleController4 {

    @GetMapping("/SampleController4Form")
    public String  mainPage(){

        return "/post/SampleController4Form";
    }

    @PostMapping("/SampleController4View")
    public String userForm(UserDTO userDTO, Model model){

        //userDTO 객체를 통해 출력
        System.out.println("=================================");
        System.out.println("userName : "+userDTO.getUserName());
        System.out.println("userId : "+userDTO.getUserId());
        System.out.println("userPhone : "+userDTO.getUserPhone());
        System.out.println("=================================");

        model.addAttribute("heading", "Spring Boot");
        return "/post/SampleController4View";
    }

}
