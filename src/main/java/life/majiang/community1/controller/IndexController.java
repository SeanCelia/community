package life.majiang.community1.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import life.majiang.community1.mapper.UserMapper;
import life.majiang.community1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller

public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies= request.getCookies();
        for(Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
               String token=cookie.getValue();
               User user=userMapper.findByToken(token);
               if(user!=null){
                   request.getSession().setAttribute("user",user);
               }
               break;
            }
        }

        return "index";
    }

}

