package com.seunggyu.stitch

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.kakao.sdk.common.KakaoSdk
import com.seunggyu.stitch.data.model.response.EventResponse
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class GlobalApplication : Application() {
    companion object {
        var appContext : Context? = null
        var bannerData : List<EventResponse>? = null
    }
    override fun onCreate() {
        super.onCreate()
        appContext = this
        KakaoSdk.init(this,getString(R.string.kakao_app_key))

        initBannerData()
    }

    private fun initBannerData() {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(
            remoteConfigSettings {
                // minimumFetchIntervalInSeconds는 클라이언트에서
                // 원격 구성 데이터를 가져오는 데 사용되는 최소 시간 간격을 지정
                // 테스트할 때는 0으로 설정, 배포 후엔 시간 조절 권장 : 3600초
                minimumFetchIntervalInSeconds = 0
            }
        )

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    bannerData = parseEventsJson(remoteConfig.getString("events"))
                    Log.e("fetch", "completed")
                }
            }
    }

    @Throws(JSONException::class)
    private fun parseEventsJson(json: String): List<EventResponse>? {
        val jsonArray = JSONArray(json)
        val urls = ArrayList<EventResponse>()
        for (index in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(index)
            if (jsonObject != null) {
                val data = jsonArray[index] as JSONObject
                val imageUrl = data.get("imageUrl").toString()
                val title = data.get("title").toString()

                urls.add(EventResponse(imageUrl, title))
            }
        }
        return urls
    }
}