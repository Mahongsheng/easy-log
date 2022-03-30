package com.github.easylog.recorder;

/**
 * 记录器，用于对日志进行客制化记录
 *
 * @author: Hansel Ma
 * @date: 2022/3/30
 */
public interface Recorder<T> {

    void record(T data);
}