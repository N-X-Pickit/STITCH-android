package com.seunggyu.stitch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.data.NaverMapApi
import com.seunggyu.stitch.data.RetrofitApi
import com.seunggyu.stitch.data.model.Location
import com.seunggyu.stitch.data.model.response.HomeDataResponse
import com.seunggyu.stitch.data.model.response.NetworkResponse
import com.seunggyu.stitch.data.model.response.NewMatch
import com.seunggyu.stitch.data.model.response.RecommendedMatch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    // 추천하는 Teach 매치 데이터
    private var _recommendMatchDataSet = MutableLiveData<HomeDataResponse>()
    val recommendMatchDataSet: LiveData<HomeDataResponse>
        get() = _recommendMatchDataSet

    // 새롭게 열린 매치 데이터
    private var _newMatchDataSet = MutableLiveData<HomeDataResponse>()
    val newMatchDataSet: LiveData<HomeDataResponse>
        get() = _newMatchDataSet

    // 추천 Teach 매치 데이터 존재 여부
    private var _recommendMatchDataSetAvailable = MutableLiveData<Boolean>()
    val recommendMatchDataSetAvailable: LiveData<Boolean>
        get() = _recommendMatchDataSetAvailable

    // 새롭게 열린 매치 데이터 존재 여부
    private var _newMatchDataSetAvailable = MutableLiveData<Boolean>()
    val newMatchDataSetAvailable: LiveData<Boolean>
        get() = _newMatchDataSetAvailable

    init {
        getHomeData()
    }

    fun getHomeData() {
        val service = RetrofitApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getHomeData()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result>>>>>>>", result.toString())
                        if (it.recommendedMatches?.isNotEmpty() == true) {
                            _recommendMatchDataSet.value = it
                            setRecommendMatchAvailable(true)
                        } else{
                            setRecommendMatchAvailable(false)
                        }
                        if (it.newMatches!!.isNotEmpty()) {
                            _newMatchDataSet.value = it
                            setNewMatchAvailable(true)
                        } else {
                            setNewMatchAvailable(false)
                        }
                    }
                }
            }
        }
    }

    fun setRecommendMatchAvailable(boolean: Boolean) {
        _recommendMatchDataSetAvailable.value = boolean
    }
    fun setNewMatchAvailable(boolean: Boolean) {
        _newMatchDataSetAvailable.value = boolean
    }
}