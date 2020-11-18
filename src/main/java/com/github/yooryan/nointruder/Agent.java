package com.github.yooryan.nointruder;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * @author linyunrui
 */
public class Agent {

    public static void premain(String agentArgs, Instrumentation inst){
        System.out.println("======agent invade======");
        AgentBuilder.Transformer transformer = (builder, typeDescription, classLoader, module) ->
                  builder
                        //拦截@RespondTime注解方法
                        .method(ElementMatchers.isAnnotatedWith(RespondTime.class))
                        .intercept(MethodDelegation.to(RespondInterceptor.class));
        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
            }
            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {
            }
            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {
            }
            @Override
            public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {
            }
            @Override
            public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
            }
        };
        new AgentBuilder.Default()
                //所有类
                .type(ElementMatchers.any())
                .transform(transformer)
                .with(listener)
                .installOn(inst);
    }
}
