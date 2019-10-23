package com.lvjq.blog.web;

import com.lvjq.blog.pojo.IPAddress;
import com.lvjq.blog.pojo.Message;
import com.lvjq.blog.pojo.TransWord;
import com.lvjq.blog.service.*;
import com.lvjq.blog.util.IpUtil;
import com.lvjq.blog.util.LogIP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BulletinService bulletinService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private IPAddressService ipAddressService;

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0" , value = "page") Integer page, Model model, HttpServletRequest request){

        Pageable pageable1 = new PageRequest(page,8, Sort.Direction.DESC,"createTime");
        Pageable pageable2 = new PageRequest(0,4, Sort.Direction.DESC,"createTime");

        String IP = IpUtil.getIpAddr(request);
        log.info("ip "+ IP);
        IPAddress ipAddress = new IPAddress();
        if(ipAddressService.getOne(IP) ==null){
            ipAddress = ipAddressService.addOne(ipAddress);
        }else{
            ipAddress = ipAddressService.getOne(IP);
            ipAddress.setViewTime(new Date());
            ipAddress.setViews(ipAddress.getViews()+1);
            ipAddress = ipAddressService.updateOne(ipAddress);
        }
        Long views = 0L;
        //工厂方法获取ValueOperations<String, Long>类型的数据  不要想着注入
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey("viewCount");
        if(hasKey) {
            operations.increment("viewCount",1);
            views = operations.get("viewCount").longValue();
            log.warn("views: " + views);
        } else {
            List<IPAddress> ipAddresses = ipAddressService.getAll();
            views = LogIP.viewsCount(ipAddresses); //加redis 如果redis没有 则这样搞
            operations.increment("viewCount",1);
        }

        model.addAttribute("page",blogService.listBlog(pageable1));
        model.addAttribute("commentsPage",commentService.listCommentsPage(pageable2));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("typesCount",typeService.listType().size());
        model.addAttribute("tagsCount",tagService.listTag().size());
        model.addAttribute("commentsCount",commentService.listComment().size());
        model.addAttribute("bulletins",bulletinService.listBulletin());
        model.addAttribute("messages",messageService.listMessages());
        model.addAttribute("recommends",blogService.listRecommendBlogTop(3));
        model.addAttribute("views",views);
        return "index";
    }

    @PostMapping("/messages")
    public String message(Message message, RedirectAttributes attributes){
        message.setCreateTime(new Date());
        Message message1 = messageService.saveMessage(message);
        if(message1!=null){
            attributes.addFlashAttribute("message","谢谢你的声音，已成功提交，等待博主听到。");
        }else{
            attributes.addFlashAttribute("message","发生错误，博主未能听到，请稍后重试.");
        }
        return "redirect:/";
    }

    @GetMapping("/blog")
    public String blog(){
        return "blog";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){

        model.addAttribute("blog",blogService.getAndConvert(id));
        TransWord transWord = new TransWord();
        transWord.setWord("");
        transWord.setResult("");
        model.addAttribute("transWord",transWord);
        return "blog";
    }
}
