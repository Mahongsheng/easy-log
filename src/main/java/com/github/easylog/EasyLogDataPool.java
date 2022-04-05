package com.github.easylog;

import com.github.easylog.entity.RecordData;

import java.util.Date;

/**
 * EasyLog日志数据池，每个线程各拥有自己的RecordData和StringBuilder
 *
 * @author Hansel Ma
 * @since 2022/3/31
 */
public class EasyLogDataPool {

    /**
     * 日志数据
     */
    private static final ThreadLocal<RecordData> RECORD_DATA = new ThreadLocal<>();

    /**
     * 日志信息字符串
     */
    private static final ThreadLocal<StringBuilder> CONTENT_BUILDER = new ThreadLocal<>();

    /**
     * 为当前线程设置日志数据
     *
     * @param recordData 日志数据
     */
    protected static void setRecordData(RecordData recordData) {
        if (CONTENT_BUILDER.get() != null) {
            recordData.setContent(CONTENT_BUILDER.get().toString());
        }
        RECORD_DATA.set(recordData);
    }

    /**
     * 获得当前线程的日志数据，如果为空则创建一个新的返回
     *
     * @return 日志数据
     */
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

    /**
     * 删除当前线程的日志数据
     */
    public static void removeRecordData() {
        RECORD_DATA.remove();
        CONTENT_BUILDER.remove();
    }

    /**
     * 记录一条日志信息
     *
     * @param step 日志信息
     */
    public static void step(String step) {
        StringBuilder sb = CONTENT_BUILDER.get();
        if (sb != null) {
            sb.append(step).append("\n");
            CONTENT_BUILDER.set(sb);
        }
    }
}
