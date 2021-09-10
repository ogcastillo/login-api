package com.ozzy.loginapi.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Aspect
@Component
public class LoginAspect {
    
    private final Logger logger = LogManager.getLogger();
    
    @Pointcut("within(com.ozzy.loginapi..*)")
    public void logAll(){};
    
    @Before("logAll()")
    public void logMethodStart(JoinPoint jp){
        String methodSig = extractMethodSignature(jp);
        String argStr = Arrays.toString(jp.getArgs());
        logger.info("{} invoke at {}", methodSig, LocalDate.now());
        logger.info("Input arguments {}", argStr);
    }
    
    @AfterReturning(pointcut = "logAll()", returning = "returnedObj")
    public void logMethodReturn(JoinPoint jp, Object returnedObj){
        String methodSig = extractMethodSignature(jp);
        logger.info("{} successfully returned at {}", methodSig, LocalDate.now());
        logger.info("Object returned {}", returnedObj);
    }
    
    @AfterThrowing(pointcut = "logAll()", throwing = "e")
    public void logMethodException(JoinPoint jp, Throwable e){
        String methodSig = extractMethodSignature(jp);
        logger.warn("{} was throw in method {} at {} with message {}", e.getClass().getSimpleName(), methodSig, LocalDate.now(), e.getMessage());
    }
    
    private String extractMethodSignature(JoinPoint jp){
        return jp.getTarget().getClass().toString() + "." + jp.getSignature().getName();
    }
    
}
