package com.lvjq.blog.web.admin;

import com.lvjq.blog.pojo.User;
import com.lvjq.blog.service.UserService;
import com.lvjq.blog.util.EncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Controller
@Slf4j
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    private static EncryptUtils encryptUtils = new EncryptUtils();

    // 生成一个AES算法的密匙
    private SecretKey keySerect = encryptUtils.createSecretAESKey();

    //Redis客户端
    @Resource
    private RedisTemplate<String,String> redisTemplate;


    @GetMapping
    public String loginPage(){

        return "admin/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        log.warn("后台尝试登录");
        if(user != null){
            log.info("用户账号密码验证通过");
            String source = username + password;
            String md5 = encryptUtils.encryptToMD5(source);
            log.info("MD5进行加密");
            //存入缓存
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(md5,"token",2,TimeUnit.DAYS);
            log.info("MD5加密信息添加到Redis缓存中，两天后失效");
            HttpSession session = request.getSession();
            session.setAttribute("token",md5);
            session.setMaxInactiveInterval(3600*24*2);
            log.info("token信息加入到session中，两天后失效");
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            attributes.addFlashAttribute("message","用户名或者密码输入错误");
            log.info("用户登录失败");
            return "redirect:/admin";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request){

        log.info("用户注销登录");
        String md5 = request.getSession().getAttribute("token").toString();
        redisTemplate.delete(md5);
        session.removeAttribute("user");
        session.removeAttribute("token");
        log.info("用户注销登录成功");

        return "admin/login";

    }

}
