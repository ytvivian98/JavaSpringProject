package com.newcoder.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yt
 * date 2022-07-05
 */
@Controller
public class LoginController {

    @RequestMapping(path = "/register",method = RequestMethod.GET)
    public String getRegistPage(){

        return "/site/register";
    }

}
