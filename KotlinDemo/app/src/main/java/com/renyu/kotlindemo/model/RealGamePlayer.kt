package com.renyu.kotlindemo.model

import com.renyu.kotlindemo.impl.IGamePlayer

class RealGamePlayer : IGamePlayer {
    override fun rank() {
        println("RealGamePlayer  rank")
    }

    override fun upgrade() {
        println("RealGamePlayer  upgrade")
    }
}