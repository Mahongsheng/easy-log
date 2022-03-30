package com.github.easylog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 日志记录实体类
 *
 * @author: Hansel Ma
 * @date: 2022/3/30
 */
@NoArgsConstructor
@Setter
@Getter
public class RecordData {
    /**
     * 线程RecordData对象
     */
    private static final ThreadLocal<RecordData> LOG_DATA = new ThreadLocal<>();
    /**
     * 线程StringBuilder对象 主要用于追加字段到最终的content
     */
    private static final ThreadLocal<StringBuilder> CONTENT_BUILDER = new ThreadLocal<>();

    private String operateType;
    /**
     * 应用名
     */
    private String appName;
    /**
     * 主机
     */
    private String host;
    /**
     * 端口号
     */
    private Integer port;
    /**
     * 请求Ip
     */
    private String clientIp;
    /**
     * 请求地址
     */
    private String reqUrl;
    /**
     * http请求method
     */
    private String httpMethod;
    /**
     * 请求头部信息(可选择记录)
     */
    private Object headers;
    /**
     * 操作标签
     */
    private String tag;
    /**
     * 方法内容
     */
    private String content;
    /**
     * 操作方法
     */
    private String method;
    /**
     * 参数
     */
    private Object args;
    /**
     * 响应体
     */
    private Object respBody;
    /**
     * 操作日期(调用日期)
     */
    private Date logDate;
    /**
     * 业务处理耗时
     */
    private long costTime;
    /**
     * 线程名
     */
    private String threadName = Thread.currentThread().getName();
    /**
     * 线程Id
     */
    private long threadId = Thread.currentThread().getId();
    /**
     * 执行状态 成功(true)/异常(false)  默认失败false
     */
    private boolean success = false;
}
