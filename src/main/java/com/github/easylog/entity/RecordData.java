package com.github.easylog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 日志记录实体类
 *
 * @author Hansel Ma
 * @date 2022/3/30
 */
@NoArgsConstructor
@Setter
@Getter
public class RecordData {
    /**
     * 操作类型
     */
    private String operateType;
    /**
     * 应用名
     */
    private String appName;
    /**
     * 方法内容
     */
    private String content;
    /**
     * 操作方法
     */
    private String method;
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
