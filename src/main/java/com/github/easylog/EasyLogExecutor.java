package com.github.easylog;

import com.github.easylog.entity.RecordData;
import com.github.easylog.recorder.DefaultLogRecorder;
import com.github.easylog.recorder.LogRecorder;
import com.github.easylog.recorder.RecorderExecutor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author: Hansel Ma
 * @date: 2022/3/30
 */
@Component
public class EasyLogExecutor {
    private final ApplicationContext applicationContext;
    private final RecorderExecutor recorderExecutor;
    private final String appName;

    public EasyLogExecutor(@Autowired RecorderExecutor recorderExecutor, @Autowired ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.recorderExecutor = recorderExecutor;
        this.appName = getAppName(applicationContext);
    }

    public String getAppName(ApplicationContext applicationContext) {
        Environment environment = applicationContext.getEnvironment();
        String name = environment.getProperty("spring.application.name");
        if (name != null) {
            return name;
        }
        if (applicationContext.getId() != null) {
            return applicationContext.getId();
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if ("main".equals(stackTraceElement.getMethodName())) {
                return stackTraceElement.getFileName();
            }
        }
        return applicationContext.getApplicationName();
    }

    private LogRecorder selectLogCollector(Class<? extends LogRecorder> clz) {
        if (clz == DefaultLogRecorder.class || clz == null) {
            return new DefaultLogRecorder();
        } else {
            LogRecorder recorder;
            try {
                recorder = applicationContext.getBean(clz);
            } catch (Exception e) {
                recorder = BeanUtils.instantiateClass(clz);
            }
            return recorder;
        }
    }

    public Object execute(ProceedingJoinPoint point, EasyLog easyLog) throws Throwable {
        Object result = null;
        boolean isSuccess = false;
        RecordDataPool.removeRecordData();
        RecordData data = RecordDataPool.getRecordData();
        try {
            result = point.proceed();
            isSuccess = true;
            return result;
        } catch (Throwable throwable) {
            if (easyLog.stackTraceOnErr()) {
                try (StringWriter sw = new StringWriter(); PrintWriter writer = new PrintWriter(sw, true)) {
                    throwable.printStackTrace(writer);
                    RecordDataPool.step("Fail : \n" + sw);
                }
            }
            throw throwable;
        } finally {
            if (!easyLog.recordOnError() || !data.isSuccess()) {
                data.setAppName(appName);
                data.setCostTime(System.currentTimeMillis() - data.getLogDate().getTime());
                MethodSignature signature = (MethodSignature) point.getSignature();
                data.setTag(easyLog.operationType());
                data.setMethod(signature.getDeclaringTypeName() + "#" + signature.getName());
                data.setSuccess(isSuccess);
                if (easyLog.asyncMode()) {
//                    recorderExecutor.asyncExecute(selectLogCollector(aopLog.getCollector()), LogData.getCurrent());
                } else {
                    recorderExecutor.execute(selectLogCollector(easyLog.recorder()), RecordDataPool.getRecordData());
                }
            }
        }
    }
}
