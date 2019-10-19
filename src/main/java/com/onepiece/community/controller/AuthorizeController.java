package com.onepiece.community.controller;

import com.onepiece.community.dto.AccessTokenDTO;
import com.onepiece.community.dto.GithubUserDTO;
import com.onepiece.community.mapper.UserMapper;
import com.onepiece.community.model.User;
import com.onepiece.community.provider.GithubPrivider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    GithubPrivider githubPrivider;

    @Value("${github.client_id}")
    private String clientId;
    @Value("${github.client_secret}")
    private String clientSecret;
    @Value("${github.redirect_url}")
    private String redirectUrl;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setState(state);
        accessTokenDTO.setCode(code);
        String accessToken = githubPrivider.getAccessToken(accessTokenDTO);
        GithubUserDTO githubUserDTO = githubPrivider.getUser(accessToken);
        if (githubUserDTO != null){
            request.getSession().setAttribute("user",githubUserDTO);
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setAccountId(githubUserDTO.getId());
            user.setName(githubUserDTO.getName());
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        } else{
            return "redirect:/";
        }
    }
}
