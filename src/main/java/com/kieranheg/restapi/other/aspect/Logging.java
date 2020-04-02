package com.kieranheg.restapi.other.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class Logging {
    private static final String PREFIX = "######## ";
    
    @Before("execution(* com.kieranheg.restapi.*.controller.*.*(..))")
    public void beforeControllerRequest(JoinPoint joinPoint){
        log.info(PREFIX + "Executing " + joinPoint.getSignature().getName() + " method in the " + joinPoint.getSignature().getDeclaringType().getSimpleName()) ;
    }
    
    @AfterReturning("execution(* com.kieranheg.restapi.*.controller.*.*(..))")
    public void afterControllerExecution(JoinPoint joinPoint){
        log.info(PREFIX + "Finished executing " + joinPoint.getSignature().getName() + " method in the " + joinPoint.getSignature().getDeclaringType().getSimpleName());
    }
    
    @Before("execution(* com.kieranheg.restapi.other.exception.RestExceptionHandler.*(..))")
    public void beforeRestExceptionHandler(JoinPoint joinPoint){
        Exception ex = (Exception) joinPoint.getArgs()[0];
        log.error(PREFIX + "The following exception was caught by " + joinPoint.getSignature().getName() +
                "() in RestExceptionHandler \n" + ex.getMessage(), ex);
    }
}
