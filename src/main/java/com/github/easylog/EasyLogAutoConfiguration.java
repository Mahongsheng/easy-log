package com.github.easylog;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author EalenXie create on 2021/1/4 11:19
 */
@ComponentScan
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class EasyLogAutoConfiguration {

}
