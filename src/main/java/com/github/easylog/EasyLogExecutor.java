package com.github.easylog;

import com.github.easylog.entity.RecordData;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author: Hansel Ma
 * @date: 2022/3/30
 */
public class EasyLogExecutor {

    public Object execute(ProceedingJoinPoint point, RecordData recordData) throws Throwable {
        try {
            Object result = point.proceed();
            System.out.println("hhhh");
            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    }
}
