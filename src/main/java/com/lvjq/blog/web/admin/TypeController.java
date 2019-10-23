package com.lvjq.blog.web.admin;

import com.lvjq.blog.pojo.Type;
import com.lvjq.blog.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model){

        model.addAttribute("page",typeService.listType(pageable));
        log.info("后台请求 type 分类数据");
        return "admin/types";

    }

    @GetMapping("/types/input")
    public String input(Model model){

        model.addAttribute("type",new Type());
        log.info("准备新增 type 分类");
        return "admin/types-input";
    }

    @RequestMapping("/types")
    public String post(Type type, RedirectAttributes attributes){

        Type typeCheck = typeService.getTypeByName(type.getName());

        if(typeCheck != null){
            log.info("名称为 " + type.getName() + " 的type类别已经存在,新增失败");
            attributes.addFlashAttribute("message","错误:已存在同名的分类");
        }else{
            typeService.saveType(type);
            log.info("新增名称为 " + type.getName() + " 的type类别");
            attributes.addFlashAttribute("message","添加成功.");
        }

        return "redirect:/admin/types";
    }


    @GetMapping("/types/{id}/input")
    public String input(@PathVariable Long id, Model model){

        model.addAttribute("type",typeService.getType(id));
        log.info("后台获取id为" + id + "的type类别进行修改");
        return "admin/types-input";
    }

    @PostMapping("/types/{id}")
    public String editPost(Type type, RedirectAttributes attributes){

        Type typeCheck = typeService.getTypeByName(type.getName());

        if(typeCheck!=null){
            log.info("名称为 " + type.getName() + " 的type类别已经存在，修改失败");
            attributes.addFlashAttribute("message","错误:已存在同名的分类");
        }else {
            typeService.updateType(type.getId(),type);
            log.info("修改标签名称为 " + type.getName() + " 的类别");
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    //删除分类
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        log.info("后台删除id为"+ id +"的type类别");
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
