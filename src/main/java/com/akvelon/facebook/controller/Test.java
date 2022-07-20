package com.akvelon.facebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Test {

    @GetMapping("/upload-avatar")
    public String upLoadPage( ){
        return "upload-post-page";
    }

    @GetMapping("/file")
    public String file(){
        return "file";
    }
}
