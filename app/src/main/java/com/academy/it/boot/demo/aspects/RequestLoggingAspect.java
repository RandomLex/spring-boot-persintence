package com.academy.it.boot.demo.aspects;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RequestLoggingAspect {

    @Pointcut("execution(* com.academy.it.boot.demo.controllers.*.*(..))")
    public void controllers() {

    }

    @Before("controllers()")
    public void before(JoinPoint joinPoint) {
        log.info("It's before: {}", joinPoint.getSignature().getName());
        log.info("-------------");
    }

    @After("controllers()")
    public void after(JoinPoint joinPoint) {
        log.info("It's after: {}", joinPoint.getSignature().getName());
//        log.info(joinPoint.getKind());
//        log.info(joinPoint.toLongString());
//        log.info(joinPoint.toShortString());
//        Arrays.stream(joinPoint.getArgs()).forEach(System.out::println);
//        log.info(joinPoint.getStaticPart().toLongString());
//        log.info(joinPoint.getThis().toString());
        log.info("-------------");
    }

    @Around("controllers()")
    @SneakyThrows
    public Object around(ProceedingJoinPoint jp) {
        log.info("It's around before: {}", jp.getSignature().getName());
        Object result = jp.proceed();
        log.info("It's around after: {}", jp.getSignature().getName());
        return result;
    }
}
