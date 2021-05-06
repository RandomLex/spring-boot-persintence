package com.academy.it.boot.demo.aspects;

import com.academy.it.boot.demo.repositories.EntityManagerHelper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class JpaTransactionAspect {
    private final EntityManagerHelper helper;

    @SneakyThrows
    @Around("@annotation(JpaTransaction)")
    public Object transaction(ProceedingJoinPoint jp) {
        EntityManager em = helper.getEntityManager();
        EntityTransaction trx = em.getTransaction();
        trx.begin();

        Object result = jp.proceed();

        trx.commit();
        em.close();
        log.info("With transaction");
        return result;
    }
}
