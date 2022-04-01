package com.github.easylog.recorder;

/**
 * 记录器，用于对日志进行客制化记录
 *
 * @author: Hansel Ma
 * @date: 2022/3/30
 */
@FunctionalInterface
public interface Recorder<T> {

    /**
     * 记录日志数据
     *
     * @param data 日志数据
     */
    void record(T data);
}
