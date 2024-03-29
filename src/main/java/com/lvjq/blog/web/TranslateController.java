package com.lvjq.blog.web;

import com.lvjq.blog.pojo.TransWord;
import com.lvjq.blog.util.TranslateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TranslateController {

    @PostMapping("/translate")
    public String translate(TransWord transWord, Model model){

        String word = transWord.getWord();
        transWord.setResult(TranslateUtil.translate(word));

        model.addAttribute("transWord",transWord);

        return "blog :: translation";
    }

}
