package com.log.loglib.asspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAspect {
    private final LogBean logBean;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {
    }

    /**
     * Pointcut that matches all methods except @NoLogging.
     */
    @Pointcut("@annotation(com.log.loglib.annotations.NoLogging)")
    public void methodAnnotatedPointCut() {
    }

    /**
     * Pointcut that matches all repositories and services.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)")
    public void springBeanPointcut() {
    }

    @Pointcut("controllerPointcut() && !methodAnnotatedPointCut()")
    public void allMethodInControllerWithoutNoLogging() {
    }

    @Pointcut("springBeanPointcut() && !methodAnnotatedPointCut()")
    public void allMethodInServiceAndRepoWithoutNoLogging() {
    }

    @Before("allMethodInControllerWithoutNoLogging()")
    public void logBefore(JoinPoint joinPoint) {
        logBean.log(joinPoint);
    }

    @Before("allMethodInServiceAndRepoWithoutNoLogging()")
    public void logServiceRepo(JoinPoint joinPoint) {
        logBean.logServRepo(joinPoint);
    }

    @AfterThrowing(pointcut = "allMethodInServiceAndRepoWithoutNoLogging() || allMethodInControllerWithoutNoLogging()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logBean.log(joinPoint, exception);
    }

    @AfterReturning(pointcut = "allMethodInServiceAndRepoWithoutNoLogging() || allMethodInControllerWithoutNoLogging()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logBean.log(joinPoint, result);
    }
}
