package com.github.easylog;

import com.github.easylog.entity.RecordData;

import java.util.Date;

/**
 * @author: Hansel Ma
 * @date: 2022/3/31
 */
public class RecordDataPool {

    private static final ThreadLocal<RecordData> RECORD_DATA = new ThreadLocal<>();

    private static final ThreadLocal<StringBuilder> CONTENT_BUILDER = new ThreadLocal<>();

    protected static void setRecordData(RecordData recordData) {
        if (CONTENT_BUILDER.get() != null) {
            recordData.setContent(CONTENT_BUILDER.get().toString());
        }
        RECORD_DATA.set(recordData);
    }

    protected static RecordData getRecordData() {
        if (RECORD_DATA.get() == null) {
            RecordData recordData = new RecordData();
            recordData.setLogDate(new Date());
            if (CONTENT_BUILDER.get() == null) {
                CONTENT_BUILDER.set(new StringBuilder());
            }
            RECORD_DATA.set(recordData);
        }
        return RECORD_DATA.get();
    }

    public static void removeRecordData() {
        RECORD_DATA.remove();
        CONTENT_BUILDER.remove();
    }

    public static void step(String step) {
        StringBuilder sb = CONTENT_BUILDER.get();
        if (sb != null) {
            sb.append(step).append("\n");
            CONTENT_BUILDER.set(sb);
        }
    }
}
