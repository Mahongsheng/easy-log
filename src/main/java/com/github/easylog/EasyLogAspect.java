package com.github.easylog;

import com.github.easylog.EasyLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @PACKAGE_NAME: PACKAGE_NAME
 * @NAME: com.github.easylog.EasyLogAspect
 * @AUTHOR: Hansel Ma
 * @DATE: 2022/3/27
 * @PROJECT_NAME: easy-log
 */
@Component
@Aspect
public class EasyLogAspect {

    @Pointcut("@annotation(EasyLog) || @within(EasyLog)")
    public void aopLogPointCut() {
    }

    /**
     * 环绕通知记录日志通过注解匹配到需要增加日志功能的方法
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("aopLogPointCut()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // 1.方法执行前的处理，相当于前置通知
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上面的注解
        EasyLog logAnno = method.getAnnotation(EasyLog.class);
        // 获取操作描述的属性值
        String operateType = logAnno.operateType();
        // 创建一个日志对象(准备记录日志)
//        Logtable logtable = new Logtable();
//        logtable.setOperatetype(operateType);// 操作说明

        // 整合了Struts，所有用这种方式获取session中属性(亲测有效)
//        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("userinfo");//获取session中的user对象进而获取操作人名字
//        logtable.setOperateor(user.getUsername());// 设置操作人

        System.out.println(method.toString() + operateType);
        System.out.println("???");

        Object result = null;
        try {
            //让代理方法执行
            result = pjp.proceed();
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
