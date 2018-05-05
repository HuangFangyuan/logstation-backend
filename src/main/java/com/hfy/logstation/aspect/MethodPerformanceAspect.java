package com.hfy.logstation.aspect;

import com.hfy.logstation.util.PerformanceMonitor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodPerformanceAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodPerformanceAspect.class);

    @Pointcut("execution(public * com.hfy.logstation.service.impl.*.*(..))")
    public void methodPerformancePoint(){}

    @Before("methodPerformancePoint()")
    public void timerStart(JoinPoint joinPoint) {
        PerformanceMonitor.begin(joinPoint.getSignature().toString());
    }

    @After("methodPerformancePoint()")
    public void timerEnd(JoinPoint joinPoint) {
        PerformanceMonitor.end();
    }
}
