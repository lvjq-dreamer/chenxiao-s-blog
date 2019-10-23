package com.lvjq.blog.web.admin;

import com.lvjq.blog.pojo.User;
import com.lvjq.blog.service.IMailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@Controller
@Slf4j
@RequestMapping("admin")
public class MailController {

    private static final String SUCC_MAIL = "邮件发送成功！";
    private static final String FAIL_MAIL = "邮件发送失败！";

    // 图片路径
   // private static final String IMG_PATH = "XXX";
    // 发送对象
    //private static final String MAIL_TO = "folgerjun@gmail.com";
    private static String MAIL_TO ;

    @Autowired
    private IMailServiceImpl mailService;
    @Autowired
    private TemplateEngine templateEngine;

    //这里引入Redis客户端
    @Resource
    private RedisTemplate<String,String> redisTemplate;

//    @RequestMapping("Email")
//    public String index(){
//        try {
//            mailService.sendSimpleMail(MAIL_TO,"这是一封普通的邮件","这是一封普通的SpringBoot测试邮件");
//        }catch (Exception ex){
//            ex.printStackTrace();
//            return FAIL_MAIL;
//        }
//        return SUCC_MAIL;
//    }


//    @GetMapping("htmlEmail")
//    public String htmlEmail(){  //默认为tymeleaf模板引擎
//        
//        try {
//            mailService.sendHtmlMail(MAIL_TO,"check your user's information of chenxiao's blog","");
//        }catch (Exception ex){
//            ex.printStackTrace();
//            return FAIL_MAIL;
//        }
//        return SUCC_MAIL;
//    }

//    @RequestMapping("/attachmentsMail")
//    public String attachmentsMail(){
//        try {
//            mailService.sendAttachmentsMail(MAIL_TO, "这是一封带附件的邮件", "邮件中有附件，请注意查收！", IMG_PATH);
//        }catch (Exception ex){
//            ex.printStackTrace();
//            return FAIL_MAIL;
//        }
//        return SUCC_MAIL;
//    }
//
//    @RequestMapping("/resourceMail")
//    public String resourceMail(){
//        try {
//            String rscId = "DoubleFJ";
//            String content = "<html><body>这是有图片的邮件<br/><img src=\'cid:" + rscId + "\' ></body></html>";
//            mailService.sendResourceMail(MAIL_TO, "这邮件中含有图片", content, IMG_PATH, rscId);
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//            return FAIL_MAIL;
//        }
//        return SUCC_MAIL;
//    }
//
    @RequestMapping("/templateMail")
    public String templateMail(HttpServletRequest request){

        log.info("更改个人信息，邮件验证启动");

        String userInfoToken = UUID.randomUUID().toString();

        //存入缓存
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(userInfoToken,"userInfoToken",20, TimeUnit.MINUTES);//10分钟后失效

        User user = (User) request.getSession().getAttribute("user");

        CompletableFuture.runAsync(()->{
            //执行逻辑,无返回值
            //异步线程
            try {
                Context context = new Context();
                context.setVariable("token",userInfoToken);
                String emailContent = templateEngine.process("admin/mailTemplate", context);
                MAIL_TO = user.getEmail();
                log.info("邮箱地址： "+MAIL_TO);
                mailService.sendHtmlMail(MAIL_TO, "博客通知", emailContent);
                log.info("邮件发送");
            }catch (Exception ex){
                ex.printStackTrace();
                log.info("邮件发送异常");
            }
        });
        return "admin/index";
    }
}
