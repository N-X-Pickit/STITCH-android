package com.seunggyu.stitch.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.seunggyu.stitch.GlobalApplication
import com.seunggyu.stitch.MainActivity
import com.seunggyu.stitch.R
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.data.model.response.EventResponse
import com.seunggyu.stitch.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class SplashActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    private fun init() {

        // background 영역을 statusbar까지 확장
        this.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                setDecorFitsSystemWindows(false)
            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
        }

        lifecycleScope.launch(Dispatchers.Main) {
            delay(2000)
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
                            GlobalApplication.bannerData = parseEventsJson(remoteConfig.getString("events"))
                            Log.e("fetch", "completed")
                            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                            startActivity(intent)

                            finish()
                        }
                    }.addOnFailureListener {
                        SnackBarCustom(binding.root, getString(R.string.snackbar_network_error))
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