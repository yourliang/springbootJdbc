package com.itcast.aspecj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class TestAspect {

    @Pointcut("execution(* com.itcast.service.TestAspectServiceImpl.test())")
    public void Pointcut() {
    }

    //前置通知
    @Before("Pointcut()")
    public void beforeMethod(JoinPoint joinPoint){
        System.out.println("before");
    }


    //前置通知
    @After("Pointcut()")
    public void afterMethod(JoinPoint joinPoint){
        System.out.println("after");
    }


    //@Around：环绕通知
    @Around("Pointcut()")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {
        int a = 3;
        Object object = pjp.proceed();
        System.out.println(5 - 3);
        return object;
    }
}