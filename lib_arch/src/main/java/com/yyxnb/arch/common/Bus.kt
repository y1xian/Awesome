package com.yyxnb.arch.common

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.yyxnb.arch.common.ArchConfig.MSG_EVENT

/**
 * 方便MsgEvent的使用
 */
object Bus {
    fun post(msgEvent: MsgEvent) {
        LiveEventBus.get(MSG_EVENT, MsgEvent::class.java).post(msgEvent)
    }

    fun post(msgEvent: MsgEvent, delay: Long) {
        LiveEventBus.get(MSG_EVENT, MsgEvent::class.java).postDelay(msgEvent, delay)
    }

    fun broadcast(msgEvent: MsgEvent) {
        LiveEventBus.get(MSG_EVENT, MsgEvent::class.java).broadcast(msgEvent)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<MsgEvent>) {
        LiveEventBus.get(MSG_EVENT, MsgEvent::class.java).observe(owner, observer)
    }

    fun observeSticky(owner: LifecycleOwner, observer: Observer<MsgEvent>) {
        LiveEventBus.get(MSG_EVENT, MsgEvent::class.java).observeSticky(owner, observer)
    }
}