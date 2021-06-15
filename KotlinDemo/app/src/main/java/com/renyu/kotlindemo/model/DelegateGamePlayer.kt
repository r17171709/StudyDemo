package com.renyu.kotlindemo.model

import com.renyu.kotlindemo.impl.IGamePlayer

class DelegateGamePlayer(private val player: RealGamePlayer) : IGamePlayer by player {
}