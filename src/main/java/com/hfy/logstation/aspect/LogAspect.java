package com.hfy.logstation.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(public * com.hfy.logstation.service.impl.LogServiceImpl.get(..))")
    public void searchLog(){}

    @Around("searchLog()")
    public void beforeExecution(ProceedingJoinPoint pdj) {

    }
}
