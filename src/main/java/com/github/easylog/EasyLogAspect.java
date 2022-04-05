package com.github.easylog;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * EasyLog切面
 *
 * @author Hansel Ma
 * @since 2022/3/27
 */
@Component
@Aspect
public class EasyLogAspect {

    @Autowired
    private EasyLogExecutor easyLogExecutor;

    /**
     * 切点，拦截标注在类上的注解和标注在方法体上的注解
     */
    @Pointcut("@within(EasyLog) || @annotation(EasyLog)")
    public void easyLogPointCut() {
    }

    /**
     * 通知，在此进行日志处理
     *
     * @param point 连接点(JoinPoint)
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    @Around("easyLogPointCut()")
    public Object easyLogAdvice(ProceedingJoinPoint point) throws Throwable {
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上面的注解
        EasyLog easyLog = method.getAnnotation(EasyLog.class);
        if (easyLog == null) { // 注解并不在方法上，而是在类上
            easyLog = point.getTarget().getClass().getAnnotation(EasyLog.class);
        }
        return easyLogExecutor.execute(point, easyLog);
    }
}
