package life.majiang.community1.controller;

import life.majiang.community1.dto.AccessTokenDTO;
import life.majiang.community1.dto.GitHubUser;

import life.majiang.community1.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
       accessTokenDTO.setCode(code);
       accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClien_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
//        String accessToken= gitHubProvider.getAccessToken(accessTokenDTO);
//        GitHubUser user= gitHubProvider.getUser(accessToken);
        String accssToken= githubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user= githubProvider.getUser(accssToken);
        System.out.println(user.getName());
        return "index";

    }

}
