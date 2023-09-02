package com.common.compose14

import LogUtils
import android.app.Application
import android.content.Context
import com.biubiu.eventbus.EventBusInitializer

class App:Application() {
    companion object {
        var  _context:Application? = null
        fun getContext(): Context {
            return _context!!
        }

    }


    override fun onCreate() {
        super.onCreate()
        _context = this
        LogUtils.init()
        EventBusInitializer.init(this)
    }
}