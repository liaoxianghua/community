package com.onepiece.community.controller;

import com.onepiece.community.mapper.UserMapper;
import com.onepiece.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        for (Cookie str:cookies) {
            if ("token".equals(str.getName())){
                String token = str.getValue();
                User user = userMapper.findByToken(token);
                if (user != null){
                    request.getSession().setAttribute("user",user);
                    break;
                }
            }
        }
        return "index";
    }

}
