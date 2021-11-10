package com.example.demo.controller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.provider.GithubProvider;
import com.example.demo.provider.User.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String clientUri;


    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code, @RequestParam(name="state") String state,
                           HttpServletRequest request , HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(clientUri);
        accessTokenDTO.setState(state);
        String accessToken =  githubProvider.getAccessToken(accessTokenDTO);
        System.out.println(accessToken);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        ///System.out.println(githubUser);
        if(githubUser != null && githubUser.getId() != null){
            User user = new User();
            user.setAccountId(clientId);
            user.setAccountId(String.valueOf(githubUser.getId()));
            String token = UUID.randomUUID().toString();
            user.setToken( token);
            user.setName(githubUser.getLogin());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userMapper.insert(user);
            response.addCookie(new Cookie("token", token));
            //登陆成功 写cookie和session
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/"; //重定向跳转
        } else{
            //登录失败
            return "redirect:/";
        }
    }
}
