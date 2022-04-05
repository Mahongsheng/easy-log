package com.github.easylog;

import com.github.easylog.recorder.DefaultLogRecorder;
import com.github.easylog.recorder.LogRecorder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EasyLog注解，可用于类或方法体上
 *
 * @author Hansel Ma
 * @since 2022/3/27
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyLog {

    /**
     * 记录类型
     */
    String operationType() default "undefined";

    /**
     * 是否只在发生异常时进行记录
     */
    boolean recordOnError() default false;

    /**
     * 是否在发生异常时追加异常堆栈信息
     */
    boolean stackTraceOnErr() default false;

    /**
     * 是否异步收集日志
     */
    boolean asyncMode() default true;

    /**
     * 客制化的记录器
     */
    Class<? extends LogRecorder> recorder() default DefaultLogRecorder.class;
}
