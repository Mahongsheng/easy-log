package com.github.easylog.recorder;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * Recorder执行器，包含异步与非异步的记录日志数据的方法
 *
 * @author Hansel Ma
 * @since 2022/3/31
 */
@Component
@EnableAsync
public class RecorderExecutor {

    /**
     * 异步记录日志数据
     *
     * @param recorder Recorder
     * @param data     日志数据
     */
    @Async("threadPoolForRecorder")
    public <T> void asyncExecute(Recorder<T> recorder, T data) {
        recorder.record(data);
    }

    /**
     * 同步记录日志数据
     *
     * @param recorder Recorder
     * @param data     日志数据
     */
    public <T> void execute(Recorder<T> recorder, T data) {
        recorder.record(data);
    }
}
