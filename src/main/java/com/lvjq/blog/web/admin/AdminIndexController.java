package com.lvjq.blog.web.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminIndexController {

    @RequestMapping("/index")
    public String index(){
        log.info("管理员登录到后台首页");
        return "admin/index";
    }
}
