package com.example.clevo.dagger2demo.interfaces;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Clevo on 2016/7/12.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface ForModel1 {

    /** The name. */
    String value() default "";
}

