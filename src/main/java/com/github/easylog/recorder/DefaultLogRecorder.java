package com.github.easylog.recorder;

import com.github.easylog.entity.RecordData;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认的Recorder
 *
 * @author Hansel Ma
 * @since 2022/3/31
 */
@Slf4j
public class DefaultLogRecorder implements LogRecorder {

    @Override
    public void record(RecordData data) {
        log.info(data.toString());
    }
}
