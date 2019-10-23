package com.lvjq.blog.web.admin;


import com.lvjq.blog.pojo.Bulletin;
import com.lvjq.blog.service.BulletinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BulletinController {

    @Autowired
    private BulletinService bulletinService;


    @GetMapping("/bulletin")
    public String bulletin(Model model){
        model.addAttribute("bulletins" , bulletinService.listBulletin());
        log.info("后台获取bulletins的数据信息");
        return "admin/bulletin";
    }

    @PostMapping("/bulletin/{id}/input")
    public String bulletin(@PathVariable Long id, Bulletin bulletin, RedirectAttributes attributes){

        bulletin.setId(id);
        Bulletin bulletin1 = bulletinService.updateBulletin(bulletin);

        if(bulletin1!=null){
            log.info("后台修改id为"+ id + "的bulletin数据成功");
            attributes.addFlashAttribute("message","修改成功");
        }else{
            log.info("后台修改id为"+ id + "的bulletin数据失败");
            attributes.addFlashAttribute("message","修改失败");
        }

        return "redirect:/admin/bulletin";

    }

    @PostMapping("/bulletin/input")
    public String input(Bulletin bulletin, RedirectAttributes attributes){

        Bulletin bulletinCheck = bulletinService.addBulletin(bulletin);
        if(bulletinCheck!=null){
            log.info("后台新增bulletin数据成功");
            attributes.addFlashAttribute("message","增加成功");
        }else{
            log.info("后台新增bulletin数据失败");
            attributes.addFlashAttribute("message","增加失败");
        }
        return "redirect:/admin/bulletin";

    }

    @GetMapping("/bulletin/input")
    public String input(Model model){
        log.info("后台准备新增bulletin数据");
        model.addAttribute("bulletin",new Bulletin());
        return "admin/bulletin-input";
    }

    @GetMapping("/bulletin/{id}/input")
    public String editBulletin(@PathVariable Long id, Model model){

        model.addAttribute("bulletin",bulletinService.getBulletinById(id));
        log.info("后台准备修改id为"+ id +"的bulletin数据");
        return "admin/bulletin-input";


    }

    @GetMapping("/bulletin/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        bulletinService.deleteBulletin(id);
        log.info("后台删除id为"+ id +"的bulletin数据");
        attributes.addFlashAttribute("message","删除成功");

        return "redirect:/admin/bulletin";

    }


}
