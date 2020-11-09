package com.renyu.logdemo

import android.os.*
import java.io.BufferedReader
import java.io.InputStreamReader

object LogcatExecutor {
    private var handler: LogcatHandler

    private var callback: CallBack? = null

    interface CallBack {
        fun onLogOutput(log: String)
    }

    init {
        val handlerThread = HandlerThread("logcat")
        handlerThread.start()
        handler = LogcatHandler(handlerThread.looper)
    }

    class LogcatHandler(looper: Looper) : Handler(looper) {
        companion object {
            const val MSG_START = 2
            const val MSG_CLEAR = 3
        }

        fun startOutputThread() {
            if (hasMessages(MSG_START)) {
                removeMessages(MSG_START)
            }
            sendEmptyMessage(MSG_START)
        }

        fun stopOutputThread() {
            removeMessages(MSG_START)
        }

        fun startClearThread() {
            sendEmptyMessage(MSG_CLEAR)
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                MSG_START -> {
                    execOutputCommand()
                    sendEmptyMessageDelayed(MSG_START, 2000)
                }
                MSG_CLEAR -> {
                    execClearCommand()
                }
            }
        }

        /**
         * 执行日志输出命令行
         */
        private fun execOutputCommand() {
            val command = "logcat -d --pid=${Process.myPid()}"
            val process = Runtime.getRuntime().exec(command)
            val log = StringBuilder()
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            var line: String? = bufferedReader.readLine()
            while (line != null) {
                log.append(line)
                log.append("\n\n")

                line = bufferedReader.readLine()
            }
            if (log.isNotEmpty()) {
                callback?.onLogOutput(String(log))
            }
        }

        /**
         * 清除日志
         */
        private fun execClearCommand() {
            val command = "logcat -c"
            Runtime.getRuntime().exec(command)
        }
    }

    fun startOutput(callback: CallBack) {
        this.callback = callback
        handler.startOutputThread()
    }

    fun stopOutput() {
        this.callback = null
        handler.stopOutputThread()
    }

    fun clear() {
        handler.startClearThread()
    }
}