package com.lvjq.blog.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception{

        //将异常信息输入到日志里面
        log.error("Request URL:{},Exception:{}",request.getRequestURI(),e);

        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURI());
        mv.addObject("exception",e);
        mv.setViewName("error/error");//会去找到需要返回的位置

        return mv;
    }

}