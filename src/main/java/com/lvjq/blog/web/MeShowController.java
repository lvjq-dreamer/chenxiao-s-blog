package com.lvjq.blog.web;

import com.lvjq.blog.pojo.TransWord;
import com.lvjq.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MeShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/me")
    public String me(Model model){
        model.addAttribute("blog",blogService.getAndConvert(2L));
        TransWord transWord = new TransWord();
        transWord.setWord("");
        transWord.setResult("");
        model.addAttribute("transWord",transWord);

        return "me";
    }
}
