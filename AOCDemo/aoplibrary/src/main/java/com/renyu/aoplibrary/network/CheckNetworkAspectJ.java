package com.renyu.aoplibrary.network;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class CheckNetworkAspectJ {

    /**
     * 任意类中返回类型为String、名字后缀为Hi、参数数量任意的方法
     */
//    @Pointcut("execution(String *Hi(..))")
//    public void executionSayHi() {}
//
//    @Around("executionSayHi()")
//    public Object hi(ProceedingJoinPoint joinPoint) throws Throwable {
//        Log.d("CheckNetworkAspectJ", "execution(String *Hi(..)) start");
//        Object obj = joinPoint.proceed();
//        Log.d("CheckNetworkAspectJ", "execution(String *Hi(..)) end");
//        return obj;
//    }

    /**
     * 任意类中返回类型不限定、名字后缀为Hi、参数数量任意的方法
     */
//    @Pointcut("execution(* *Hi(..))")
//    public void executionSayHi() {}
//
//    @Around("executionSayHi()")
//    public Object hi(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("execution(* *Hi(..))");
//        return joinPoint.proceed();
//    }

    /**
     * 任意类中返回类型不限定，名字前缀为hi，参数数量任意的方法
     */
    @Pointcut("execution(* hi*(..))")
    public void executionSayHi() {}

    @Around("executionSayHi()")
    public Object hi(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("execution(* hi*(..))");
        return joinPoint.proceed();
    }

    /**
     * com.renyu.aocdemo包下任意类中返回类型不限定、参数数量任意的方法
     */
//    @Pointcut("execution(* com.renyu.aocdemo.*.* (..))")
//    public void executionSayHi() {}
//
//    @Around("executionSayHi()")
//    public Object hi(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("execution(* com.renyu.aocdemo.*.* (..))");
//        return joinPoint.proceed();
//    }

    /**
     * com.renyu.aocdemo.Main2Activity类中返回类型不限定、参数数量任意的方法
     */
//    @Pointcut("execution(* com.renyu.aocdemo.MainActivity.* (..))")
//    public void executionSayHi() {}
//
//    @Around("executionSayHi()")
//    public Object hi(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("execution(* com.renyu.aocdemo.MainActivity.* (..))");
//        return joinPoint.proceed();
//    }

    /**
     * com.renyu.aocdemo包及其子包下的任意类中返回类型不限定、参数数量任意的方法
     */
//    @Pointcut("execution(* com.renyu.aocdemo..*.* (..))")
//    public void executionSayHi() {}
//
//    @Around("executionSayHi()")
//    public Object hi(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("execution(* com.renyu.aocdemo..*.* (..))");
//        return joinPoint.proceed();
//    }

    /**
     * 指定一个方法，com.renyu.aocdemo.Main2Activity类中hisay方法
     */
//    @Pointcut("execution(* com.renyu.aocdemo.MainActivity.hisay(..))")
//    public void executionSayHi() {}
//
//    @Around("executionSayHi()")
//    public Object hi(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("execution(* com.renyu.aocdemo.MainActivity.hisay(..))");
//        return joinPoint.proceed();
//    }

    /**
     * 指定Activity类及其子类的所有方法
     */
//    @Pointcut("execution(* *..Activity+.*(..))")
//    public void executionSayHi() {}
//
//    @Around("executionSayHi()")
//    public Object hi(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("execution(* *..Activity+.*(..))");
//        return joinPoint.proceed();
//    }

//    @Before("executionSayHi3()")
//    public void checkAll(JoinPoint joinPoint) throws Throwable  {
//        System.out.println("executionSayHi3");
//    }

//    @Around("executionSayHi()||executionSayHi2()")
//    public Object hi(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("hi");
//        return joinPoint.proceed();
//    }

    /**
     * 在MainActivity类中调用sayHi()方法的时候，才切入代码
     */
//    @Pointcut("call(* com.renyu.aocdemo.MainActivity.sayHi(..))")
//    public void executionSayHi() {}
////    @Pointcut("withincode(* com.renyu.aocdemo.MainActivity.abc(..))")
////    public void executionAbc() {}
//    @Pointcut("within(com.renyu.aocdemo.MainActivity)")
//    public void executionAbc() {}
//
//    @Around("executionSayHi() && executionAbc()")
//    public Object hi(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("executionSayHi() && executionAbc()");
//        return joinPoint.proceed();
//    }

    /**
     * 修改变量值
     */
//    @Pointcut("get(String com.renyu.aocdemo.model.UserModel.name)")
//    public void changeModelValue() {}
//
//    @Around("changeModelValue()")
//    public String changeModelValue(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("get(String com.renyu.aocdemo.model.UserModel.name)");
//        return "change";
//    }

    @Pointcut("execution(@com.renyu.aoplibrary.network.CheckNetworkAnnotation * *(..))")
    public void executionCheckNet() {}

    @Around("executionCheckNet()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("checkNet");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // class名
        methodSignature.getDeclaringType().getSimpleName();
        // 方法名
        methodSignature.getName();
        // 当前方法
        methodSignature.getMethod();
        // 参数名
        methodSignature.getParameterNames();
        // 参数类型
        methodSignature.getParameterTypes();
        // 方法传入的参数值
        joinPoint.getArgs();

        CheckNetworkAnnotation checkNetworkAnnotation = methodSignature.getMethod().getAnnotation(CheckNetworkAnnotation.class);
        String permission = checkNetworkAnnotation.permission();
        if (permission.equals("network")) {
            Context context = getContext(joinPoint.getThis());
            if (context != null) {
                if (NetworkUtils.isConnected()) {
                    ToastUtils.showShort("网络连接正常");
                }
                else {
                    ToastUtils.showShort("网络连接断开");
                }
            }
        }
        return joinPoint.proceed();
    }

    public Context getContext(Object object) {
        if (object instanceof Activity) {
            return (Activity) object;
        } else if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            return fragment.getActivity();
        } else if (object instanceof View) {
            View view = (View) object;
            return view.getContext();
        }
        return null;
    }
}
