package com.wh.wenda.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import java.util.Date;

@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    //    第一个*返回值
//    第二个*类名
//    第三个*方法名
//    ..表示参数
    @Before("execution(* com.wh.wenda.controller.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder stringBuilder=new StringBuilder();
        for (Object arg:joinPoint.getArgs()){
            stringBuilder.append("arg: "+arg.toString()+" <|>");
        }
        logger.info("after method"+new Date()+" "+stringBuilder.toString());

    }

    @After("execution(* com.wh.wenda.controller.*.*(..))")
    public void afterMethid() {
        logger.info("after mathod"+ new Date());
    }
}
