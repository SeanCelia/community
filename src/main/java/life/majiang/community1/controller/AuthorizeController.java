package life.majiang.community1.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import life.majiang.community1.dto.AccessTokenDTO;
import life.majiang.community1.dto.GitHubUser;
import life.majiang.community1.mapper.UserMapper;
import life.majiang.community1.model.User;
import life.majiang.community1.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.UUID;



@Controller
public class AuthorizeController {


    @Autowired
    GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClien_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
//        String accessToken= gitHubProvider.getAccessToken(accessTokenDTO);
//        GitHubUser user= gitHubProvider.getUser(accessToken);
        String accssToken= githubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser= githubProvider.getUser(accssToken);
        if(gitHubUser!=null){
            User user=new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            //登陆成功，写入session和cookie
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 60 * 24 * 30 * 6);
            cookie.setPath("/");
            response.addCookie(cookie);

            request.getSession().setAttribute("user",gitHubUser);
            return "redirect:/";
        }else{
            return "redirect:/";
        }

    }

}
