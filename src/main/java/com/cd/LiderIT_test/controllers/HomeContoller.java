package com.cd.LiderIT_test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HomeContoller {

    @GetMapping
    public @ResponseBody Map<String,String> getStartPage(){
        return new HashMap<String,String>(){{
            put("message","You get start page!");
        }};
    }
}
