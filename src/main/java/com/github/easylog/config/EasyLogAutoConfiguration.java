package com.github.easylog.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


/**
 * 定义配置类
 *
 * @author Hansel Ma
 * @since 2022/3/31
 */
@ComponentScan("com.github.easylog")
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class EasyLogAutoConfiguration {
}
