package com.lvjq.blog.web.admin;

import com.lvjq.blog.pojo.Friend;
import com.lvjq.blog.service.FriendService;
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
public class FriendsController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/friends")
    public String friends(Model model){

        model.addAttribute("friends",friendService.listFriends());
        log.info("后台请求友链相关信息");
        return "admin/friends";

    }


    @PostMapping("/friends/{id}/input")
    public String friend(@PathVariable Long id, Friend friend, RedirectAttributes attributes){

        friend.setId(id);
        Friend friendCheck = friendService.updateFriend(friend);

        if(friendCheck != null) {
            log.info("后台成功修改 id为"+ id + "的友链信息");
            attributes.addFlashAttribute("message","修改成功");
        } else {
            log.info("后台修改 id为"+ id + "的友链信息失败");
            attributes.addFlashAttribute("message","修改失败");
        }

        return "redirect:/admin/friends";

    }

    @PostMapping("/friends/input")
    public String input(Friend friend, RedirectAttributes attributes){

        Friend friendCheck = friendService.saveFriend(friend);

        if(friendCheck != null){
            log.info("后台新增友链,链接为"+friend.getWebsite());
            attributes.addFlashAttribute("message","增加成功");
        }else{
            log.info("后台新增友链失败");
            attributes.addFlashAttribute("message","增加失败");
        }
        return "redirect:/admin/friends";

    }

    @GetMapping("/friends/input/{flag}")
    public String input(@PathVariable String flag, Model model){
        Friend friend = new Friend();
        if("朋友".equals(flag)){
            friend.setFlag("朋友");
            friend.setAvatar("请添加");
            friend.setOccupation("请添加");
        }else{
            friend.setFlag(flag);
        }
        model.addAttribute("friend",friend);
        return "admin/friends-input";
    }

    @GetMapping("/friends/{id}/input")
    public String editBulletin(@PathVariable Long id, Model model){

        model.addAttribute("friend",friendService.getFriend(id));

        return "admin/friends-input";


    }

    @GetMapping("/friends/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        friendService.deleteFriend(id);

        log.info("后台删除id为" + id + "的友链信息");
        attributes.addFlashAttribute("message","删除成功");

        return "redirect:/admin/friends";

    }
}
