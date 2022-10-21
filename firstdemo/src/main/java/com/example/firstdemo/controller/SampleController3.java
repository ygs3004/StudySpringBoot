package com.example.firstdemo.controller;

import com.example.dto.UserDTO;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
*  스프링 부트 객체 전달 (userDTO 객체 사용)
*/
@Controller
public class SampleController3 {

    /*
    @PostMapping("/SampleController3")
    //public String userForm(
            //@RequestParam String userName,
            //@RequestParam String userId,
            //@RequestParam String userPhone){      @RequestParam 생략가능
    public String userForm(String userName, String userId, String userPhone){
        System.out.println("=====================================");
        System.out.println("userName : "+userName);
        System.out.println("userId : "+userId);
        System.out.println("userPhone : "+userPhone);
        System.out.println("=====================================");

        return "/post/SampleController3View";
    }
    */

    @PostMapping("/SampleController3")
    public String userForm(UserDTO userDTO, Model model){

        // userDTO 객체를 통해서 출력
        System.out.println("=====================================");
        System.out.println("userName : "+userDTO.getUserName());
        System.out.println("userId : "+userDTO.getUserId());
        System.out.println("userPhone : "+userDTO.getUserPhone());
        System.out.println("=====================================");

        //model.addAttribute("userDTO", userDTO);
        model.addAttribute("heading", "Spring Boot");

        return "/post/SampleController3View";
    }

}
