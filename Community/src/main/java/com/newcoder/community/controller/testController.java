package com.newcoder.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;

/**
 * @author yt
 * date 2022-06-29
 */
@Controller
@RequestMapping("/test")
public class testController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }
}
