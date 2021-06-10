package com.renyu.route.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 标记在类上
@Target({ElementType.TYPE})
// 注解的生命周期
@Retention(RetentionPolicy.CLASS)
public @interface Destination {
    /**
     * 当前页面url
     *
     * @return
     */
    String url() default "";

    /**
     * 对当前页面的描述
     *
     * @return
     */
    String description() default "";
}
