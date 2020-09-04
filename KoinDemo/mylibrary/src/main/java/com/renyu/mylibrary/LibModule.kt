package com.renyu.mylibrary

import org.koin.dsl.module

/**
 * Created by Administrator on 2020/9/4.
 */
object LibModule {
    val lib = module {
        factory {
            LibBean()
        }
    }
}