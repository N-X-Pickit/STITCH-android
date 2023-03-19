package com.seunggyu.stitch.viewModel

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seunggyu.stitch.R
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.data.RetrofitApi
import com.seunggyu.stitch.data.model.User
import com.seunggyu.stitch.data.model.request.SignupRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class SplashViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun getCurrentUser(id: String) {
        val service = RetrofitApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getUserData(id)

            try {
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        result?.let {
                            Log.e("result>>>>>>>", result.toString())
                            _user.value = it
                        }
                    }
                }
            } catch (e: IOException) {
                Log.e("io_error", e.message!!)
                e.printStackTrace()
            }
        }
    }
}