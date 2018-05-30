package com.renyu.hjlibrary.singleclick;

import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;

@Aspect
public class SingleClickAspectJ {

    public HashMap<Integer, Long> caches = new HashMap<>();

    @Pointcut("execution(@com.renyu.hjlibrary.singleclick.SingleClick * *(..))")
    public void executionSingleClick() {}

    @Around("executionSingleClick()")
    public Object checkSingleClick(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        SingleClick singleClick = methodSignature.getMethod().getAnnotation(SingleClick.class);
        long time = singleClick.interval();
        int[] values = singleClick.exceptViews();
        // 这里要求第一个参数必须是被点击的View
        if (args != null && args.length>=1 && args[0] instanceof View && ((View) args[0]).getId() != View.NO_ID) {
            for (int value : values) {
                // 如果当前点击的View符合不需要判断的条件，直接通过
                if (value == ((View) args[0]).getId()) {
                    return joinPoint.proceed();
                }
            }
            // 存在ID的情况，证明之前被点击过
            if (caches.containsKey(((View) args[0]).getId())) {
                // 超过点击限制时间，直接通过
                if (System.currentTimeMillis() - caches.get(((View) args[0]).getId()) > time) {
                    // 保存当前点击时间
                    caches.put(((View) args[0]).getId(), System.currentTimeMillis());
                    return joinPoint.proceed();
                }
                else {
                    return null;
                }
            }
            // 不存在ID的情况，直接通过
            else {
                // 保存当前点击时间
                caches.put(((View) args[0]).getId(), System.currentTimeMillis());
                return joinPoint.proceed();
            }
        }
        throw new Exception("配置格式有问题");
    }
}
