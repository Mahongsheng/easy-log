package com.github.easylog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
}
