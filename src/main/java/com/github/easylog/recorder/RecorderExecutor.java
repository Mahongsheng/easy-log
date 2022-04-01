package com.github.easylog.recorder;

import org.springframework.stereotype.Component;

/**
 * @author: Hansel Ma
 * @date: 2022/3/31
 */
@Component
public class RecorderExecutor {
    public <T> void execute(Recorder<T> collector, T data) {
        collector.record(data);
    }
}
