package com.yexample.club.security;

import com.yexample.club.security.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JWTTests {

    private JWTUtil jwtUtil;

    @BeforeEach
    public void testBefore(){
        System.out.println("test Before.................................");
        jwtUtil = new JWTUtil();
    }

    @Test
    public void testEncode() throws InterruptedException {
        String email = "user95@yexample.com";
        String str = jwtUtil.generateToken(email);
        System.out.println(str);

        Thread.sleep(5000);
        String resultEmail = jwtUtil.validateAndExtract(str);
        System.out.println(resultEmail);
    }

}
