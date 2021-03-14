package com.example.doat

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext(): GlobalApplication {
        checkNotNull(instance)
        return instance!!
    }

    companion object {
        var instance: GlobalApplication? =null
    }
}