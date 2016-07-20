package com.example.clevo.dagger2demo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Clevo on 2016/7/19.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ScopeDemo2 {
}
