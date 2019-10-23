package com.lvjq.blog.web.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("admin")
public class PersonalController {

    @GetMapping("personal")
    public String jump() {
        log.info("进入个人信息修改的确认页面");
        return "admin/personal";
    }
}
