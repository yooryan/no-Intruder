package com.github.yooryan.nointruder;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 对拦截方法打印调用耗时
 * @author linyunrui
 */
public class RespondInterceptor {
    @RuntimeType
    public static Object intercept(@Origin Method method,
                                   @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {

            return callable.call();
        }finally {
            long end = System.currentTimeMillis();
            System.out.println("Response Time : " + (end - start));
        }
    }
}
