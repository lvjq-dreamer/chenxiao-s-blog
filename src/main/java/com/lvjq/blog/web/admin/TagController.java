package com.lvjq.blog.web.admin;

import com.lvjq.blog.pojo.Tag;
import com.lvjq.blog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10,sort={"id"}, direction = Sort.Direction.DESC)
                               Pageable pageable, Model model){

        model.addAttribute("page",tagService.listTag(pageable));
        log.info("后台数据请求tag");
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){

        model.addAttribute("tag",new Tag());
        log.info("准备新增tag标签");
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes){

        tagService.deleteTag(id);
        log.info("后台删除id为"+ id +"的tag标签");
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";

    }

    @GetMapping("/tags/{id}/input")
    public String input(@PathVariable Long id, Model model){

        model.addAttribute("tag",tagService.getTag(id));
        log.info("后台获取id为" + id + "的tag标签进行修改");
        return "admin/tags-input";

    }


    @PostMapping("/tags")
    public String editpost(Tag tag, RedirectAttributes attributes){

        Tag tagCheck = tagService.getTagByName(tag.getName());

        if(tagCheck!=null){
            log.info("名称为 " + tag.getName() + " 的tag标签已经存在,新增失败");
            attributes.addFlashAttribute("message","当前标签已经存在!");
        }else{
            tagService.saveTag(tag);
            log.info("新增名称为 " + tag.getName() + " 的tag标签");
            attributes.addFlashAttribute("message","哎呦, 增加成功!");
        }

        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String post(Tag tag, RedirectAttributes attributes){

        Tag tagCheck = tagService.getTagByName(tag.getName());

        if(tagCheck!=null){
            log.info("名称为 " + tag.getName() + " 的tag标签已经存在，修改失败");
            attributes.addFlashAttribute("message","当前标签已经存在!");
        }else{
            tagService.updateTag(tag.getId(),tag);
            log.info("修改标签名称为 " + tag.getName() + " 的标签");
            attributes.addFlashAttribute("message","哎呦,修改成功!");
        }

        return "redirect:/admin/tags";
    }
}
