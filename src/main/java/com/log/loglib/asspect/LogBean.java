package com.log.loglib.asspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Component
public class LogBean {
    public void log(JoinPoint joinPoint) {
        HttpServletRequest request = getServletRequest();
        if (null != request) {
            log.info("Entering in Method: {}, Class Name: {}, Arguments: {}, Target class: {}, Method Type: {}, Request Path info: {}",
                    joinPoint.getSignature().getName(),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getTarget().getClass().getName(),
                    request.getMethod(),
                    request.getServletPath());
        } else {
            log.info("Entering in Method: {}, Class Name: {}, Arguments: {}, Target class: {}",
                    joinPoint.getSignature().getName(),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getTarget().getClass().getName());
        }
    }

    public void logServRepo(JoinPoint joinPoint) {
        HttpServletRequest request = getServletRequest();
        if (null != request) {
            //TODO 17.07.2022 sarkhan69: maybe add a list of headers
            log.info("Entering in Method: {}, Class Name: {}, Arguments: {}, Target class: {}",
                    joinPoint.getSignature().getName(),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getTarget().getClass().getName());
        } else {
            log.info("Entering in Method: {}, Class Name: {}, Arguments: {}, Target class: {}",
                    joinPoint.getSignature().getName(),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getTarget().getClass().getName());
        }
    }

    public void log(JoinPoint joinPoint, Throwable exception) {
        log.error("Exception in {}.{}() with cause = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                exception.getCause() != null ? exception.getCause() : "NULL");
    }

    public void log(JoinPoint joinPoint, Object result) {
        log.info("Returning Value: {} from Method: {}, Class Name: {}, Arguments: {}, Target class: {}",
                getResult(result),
                joinPoint.getSignature().getName(),
                joinPoint.getSignature().getDeclaringTypeName(),
                Arrays.toString(joinPoint.getArgs()),
                joinPoint.getTarget().getClass().getName());
    }

    private String getResult(Object result) {
        return result == null ? "null" : result.toString();
    }

    private HttpServletRequest getServletRequest() {
        return RequestContextHolder.getRequestAttributes() != null ?
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() : null;
    }
}
