package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityControllerTest {


    @GetMapping("public")
    public String publicPage(){
        return "public";
    }

    @GetMapping("authenticated")
    public String authenticatedPage(){
        return "authenticated";
    }
}
