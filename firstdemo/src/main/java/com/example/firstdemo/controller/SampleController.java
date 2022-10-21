package com.example.firstdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SampleController {

    @GetMapping("user/userinfo")
    public String userInfo(){

        return "/user/userinfo";
    }

    @GetMapping("/user/userdata")
    public String userData(Model model){

        // view 페이지로 데이터(Model) 전달
        model.addAttribute("username", "윤건수");

        return "/user/userinfo";
    }

    @GetMapping("/user/userid")
    public String userId(@RequestParam(value = "userid", required = false) String userid, Model model){
        /*
        * required 는 true가 디폴트 옵션, 파라미터가 없으면 에러가 발생한다.
        * false를 지정하면 파라미터가 없어도 에러가 발생하지 안흔ㄴ다.
        */

        model.addAttribute("userid", userid);

        return "/user/userinfo"; //userinfo.html
    }

    @GetMapping("/user/userparams")
    public String userParams(@RequestParam(value = "userid") String uid,
                             @RequestParam(value = "username") String uname,
                             @RequestParam(value = "useremail") String uemail,
                             @RequestParam(value = "userhp", required = false) String uhp,
                             Model model){

        model.addAttribute("userid", uid);
        model.addAttribute("username",  uname);
        model.addAttribute("useremail",  uemail);
        model.addAttribute("userhp",  uhp);

        return "/user/userinfo";
    }




}