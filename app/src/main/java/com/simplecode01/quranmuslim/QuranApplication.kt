package com.simplecode01.quranmuslim

import android.app.Application
import android.os.Bundle
import com.lzx.starrysky.StarrySky
import com.lzx.starrysky.notification.INotification
import com.lzx.starrysky.notification.NotificationConfig

open class QuranApplication: Application() {

    override fun onCreate() {

        super.onCreate()
        StarrySky.init(this@QuranApplication).apply{
            val notificationConfig = NotificationConfig.create {
                targetClass { "com.simplecode01.quranmuslim.NotificationReceiver" }
                targetClassBundle {
                    val bundle = Bundle()
                    bundle.putString("title", "我是点击通知栏转跳带的参数")
                    bundle.putString("targetClass", "com.lzx.musiclib.home.PlayDetailActivity")
                    //参数自带当前音频播放信息，不用自己传
                    return@targetClassBundle bundle
                }
                pendingIntentMode { NotificationConfig.MODE_BROADCAST }
            }

            setIsOpenNotification(true)
            setNotificationSwitch(true)
            setNotificationType(INotification.SYSTEM_NOTIFICATION)
            setNotificationConfig(notificationConfig)
            apply()
        }

    }
}
