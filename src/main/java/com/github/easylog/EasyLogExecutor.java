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
 * EasyLog执行器
 *
 * @author Hansel Ma
 * @since 2022/3/30
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

    /**
     * 获取当前服务的名字
     *
     * @param applicationContext 服务上下文
     * @return 服务名字
     */
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

    /**
     * 选择日志收集器
     */
    private LogRecorder selectLogRecorder(Class<? extends LogRecorder> clz) {

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

    /**
     * 执行连接点并进行日志记录
     *
     * @param point   连接点
     * @param easyLog EasyLog注解
     * @return 连接点执行结果
     * @throws Throwable 异常
     */
    public Object execute(ProceedingJoinPoint point, EasyLog easyLog) throws Throwable {
        Object result;
        boolean isSuccess = false;
        EasyLogData.removeRecordData();
        RecordData data = EasyLogData.getRecordData();
        try {
            // 执行切点
            result = point.proceed();
            isSuccess = true;
            return result;
        } catch (Throwable throwable) {
            // 抛出异常，进行异常记录
            if (easyLog.stackTraceOnErr()) {
                try (StringWriter sw = new StringWriter(); PrintWriter writer = new PrintWriter(sw, true)) {
                    throwable.printStackTrace(writer);
                    EasyLogData.step("Fail : \n" + sw);
                }
            }
            throw throwable;
        } finally {
            // 判断是否只在抛出异常时执行以及切点是否执行成功
            if (!easyLog.recordOnError() || !data.isSuccess()) {
                data.setAppName(appName);
                data.setCostTime(System.currentTimeMillis() - data.getLogDate().getTime());
                data.setOperateType(easyLog.operationType());
                data.setSuccess(isSuccess);

                MethodSignature signature = (MethodSignature) point.getSignature();
                data.setMethod(signature.getDeclaringTypeName() + "#" + signature.getName());

                EasyLogData.setRecordData(data);
                if (easyLog.asyncMode()) { // 异步或同步执行
                    recorderExecutor.asyncExecute(selectLogRecorder(easyLog.recorder()), EasyLogData.getRecordData());
                } else {
                    recorderExecutor.execute(selectLogRecorder(easyLog.recorder()), EasyLogData.getRecordData());
                }
            }
        }
    }
}
