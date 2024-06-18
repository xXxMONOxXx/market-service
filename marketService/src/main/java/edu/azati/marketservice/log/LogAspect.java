package edu.azati.marketservice.log;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Log4j2
@Aspect
@Component
public class LogAspect {

    @Around("@annotation(LogRequestExecutionTime)")
    public Object requestExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getMethod().getName();
        Instant startTime = Instant.now();
        Object result = proceedingJoinPoint.proceed();
        long elapsedTime = Duration.between(startTime, Instant.now()).toMillis();
        log.info("Class Name: {}, Method Name: {}, Request time: {}ms", className, methodName, elapsedTime);
        if (result instanceof ResponseEntity<?>) {
            log.info("Status: {}", ((ResponseEntity<?>) result).getStatusCode());
        } else {
            log.info("Result: {}", result);
        }
        return result;
    }
}
