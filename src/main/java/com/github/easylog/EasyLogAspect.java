package com.github.easylog;

import com.github.easylog.entity.RecordData;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author: Hansel Ma
 * @date: 2022/3/27
 */
@Component
@Aspect
public class EasyLogAspect {

    /**
     * 切入点
     */
    @Pointcut("@annotation(EasyLog) || @within(EasyLog)")
    public void easyLogPointCut() {
    }

    /**
     * 通知(Advice)，在此进行日志处理
     *
     * @param point 连接点(JoinPoint)
     * @return 方法执行结果
     * @throws Throwable 异常
     */
    @Around("easyLogPointCut()")
    public Object easyLogAdvice(ProceedingJoinPoint point) throws Throwable {


        // 1.方法执行前的处理，相当于前置通知
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上面的注解
        EasyLog logAnno = method.getAnnotation(EasyLog.class);
        // 获取操作描述的属性值
        String operateType = logAnno.operationType();
        // 创建一个日志对象(准备记录日志)
        RecordData recordData = new RecordData();
        recordData.setOperateType(operateType);// 操作说明

        EasyLogExecutor easyLogExecutor = new EasyLogExecutor();

        // 整合了Struts，所有用这种方式获取session中属性(亲测有效)
//        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");//获取session中的user对象进而获取操作人名字
//        logtable.setOperateor(user.getUsername());// 设置操作人

        Object result = null;
        try {
            //让代理方法执行
            result = easyLogExecutor.execute(point, recordData);
            // 2.相当于后置通知(方法成功执行之后走这里)
//            logtable.setOperateresult("正常");// 设置操作结果
        } catch (Exception e) {
            // 3.相当于异常通知部分
//            logtable.setOperateresult("失败");// 设置操作结果
        } finally {
            // 4.相当于最终通知
//            logtable.setOperatedate(new Date());// 设置操作日期
//            logtableService.addLog(logtable);// 添加日志记录
        }
        return result;
    }
}
