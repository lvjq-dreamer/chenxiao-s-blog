package com.lvjq.blog.web.admin;


import com.lvjq.blog.pojo.User;
import com.lvjq.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;


@Controller
@RequestMapping("alert")
@Slf4j
public class PersonInfoAlert {

    @Autowired
    private UserService userService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("check")
    public String checkInfo(@RequestParam("token") String token, Model model) {
        if (token.length() == 0) {
            model.addAttribute("errorMessage","抱歉，您没有权限");
            log.warn("存在‘个人信息修改’的异常认证");
            return "alert/error";
        }
        boolean haskey = redisTemplate.hasKey(token);

        if(haskey) {
            User user = userService.getUser(1L);
            model.addAttribute("user",user);
            model.addAttribute("token",token);
            log.info("验证通过，跳转个人信息修改页面");
            return "alert/changeUserInfo";
        } else {
            model.addAttribute("errorMessage","抱歉，验证时间超时");
            return "alert/error";
        }

    }

    @PostMapping("change")
    public String alertInfo(@RequestParam("avatar") String avatar,
                            @RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam("nickname") String nickname,
                            Model model
    ) {
        User user = userService.getUser(1L);
        user.setPassword(password);
        user.setUsername(username);
        user.setAvatar(avatar);
        user.setNickname(nickname);
        userService.save(user);
        log.info("个人信息更新成功");
        model.addAttribute("messageInfo","更新成功~");
        return "alert/success";
    }

}
