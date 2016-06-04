package com.example.annotation;

/**
 * Created by RG on 2016/6/2.
 */
public interface InjectInterface<T> {
    void inject(Finder finder, T target);
}
