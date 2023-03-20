package com.seunggyu.stitch

import android.app.Application
import android.content.Context
import android.util.Log
import android.util.TypedValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.jakewharton.threetenabp.AndroidThreeTen
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk
import com.seunggyu.stitch.Util.MySharedPreference
import com.seunggyu.stitch.data.model.response.EventResponse
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class GlobalApplication : Application() {
    companion object {
        var appContext : Context? = null
        var bannerData : List<EventResponse>? = null
        lateinit var prefs: MySharedPreference
    }
    override fun onCreate() {
        super.onCreate()
        appContext = this
        prefs = MySharedPreference(applicationContext)

        KakaoSdk.init(this,getString(R.string.kakao_app_key))
        AndroidThreeTen.init(this)
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(getString(R.string.naver_client_id))

    }

    fun dpToPx(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp,
            resources.displayMetrics
        ).toInt()
    }

}