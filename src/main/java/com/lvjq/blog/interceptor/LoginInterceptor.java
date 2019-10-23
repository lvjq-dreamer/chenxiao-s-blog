package com.lvjq.blog.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    //Redis客户端
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String md5 = (String)request.getSession().getAttribute("token");

        boolean haskey = redisTemplate.hasKey(md5);
        log.info("用户切换网页");
        if(haskey) {
            log.warn("用户身份验证成功");
            return true;
        } else {
            response.sendRedirect("/admin");
            log.warn("用户身份验证失败");
            return false;
        }

    }
}
