package com.github.easylog.recorder;

import com.github.easylog.entity.RecordData;

/**
 * 默认的Recorder
 *
 * @author Hansel Ma
 * @since 2022/3/31
 */
public class DefaultLogRecorder implements LogRecorder {

    @Override
    public void record(RecordData data) {
        System.out.println(data.getAppName());
        System.out.println("这是一个默认的日志记录器");
    }
}
