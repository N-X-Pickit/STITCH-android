package com.seunggyu.stitch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seunggyu.stitch.data.model.response.NetworkResponse

class MainViewModel : ViewModel() {

    // 추천하는 Teach 매치 데이터
    private var _recommendMatchDataSet = MutableLiveData<ArrayList<NetworkResponse>>()
    val recommendMatchDataSet: LiveData<ArrayList<NetworkResponse>>
        get() = _recommendMatchDataSet

    // 새롭게 열린 매치 데이터
    private var _newMatchDataSet = MutableLiveData<ArrayList<NetworkResponse>>()
    val newMatchDataSet: LiveData<ArrayList<NetworkResponse>>
        get() = _newMatchDataSet

    /** 테스트를 위한 임시 데이터 set **/
    private var rDataSet = ArrayList<NetworkResponse>()
    private var nDataSet = ArrayList<NetworkResponse>()

    init {
        for (i in 0..4) {
            rDataSet.add(
                NetworkResponse(
                    // name -> 개설자 닉네임
                    // email -> 개설자 프로필 이미지 url
                    // imageUrl -> 이미지 url
                    // location -> 매치 장소
                    // fee -> 최대인원
                    // duration -> 참여인원
                    // contents -> 매치 제목
                    // hostId -> 매치 시작일
                    // startTime -> 매치 시간
                    // sports[] -> 매치 종목
                    "123",
                    null,
                    "닉네임입니다아아아아",
                    "https://firebasestorage.googleapis.com/v0/b/stitch-1677850382202.appspot.com/o/Ellipse%20184.png?alt=media&token=3bf4fade-f12a-4625-8b82-304495072dc1",
                    "https://firebasestorage.googleapis.com/v0/b/stitch-1677850382202.appspot.com/o/Rectangle%203023.png?alt=media&token=7ad2b202-435c-4048-8313-0624e2130a6c",
                    "성동구",
                    "02.0$i(월)",
                    4 + i,
                    "이번주 토요일에 테니스 내기 한번?~~~~~~~~~~~",
                    "오후 3:00",
                    3 + i,
                    listOf("테니스")
                )
            )
        }

        for(i in 0..4) {
            nDataSet.add(
                NetworkResponse(
                    // imageUrl -> 이미지 url
                    // location -> 매치 장소
                    // fee -> 최대인원
                    // duration -> 참여인원
                    // contents -> 매치 제목
                    // hostId -> 매치 시작일
                    // startTime -> 매치 시간
                    // sports[] -> 매치 종목
                    "123",
                    null,
                    null,
                    null,
                    "https://firebasestorage.googleapis.com/v0/b/stitch-1677850382202.appspot.com/o/Rectangle%203023.png?alt=media&token=7ad2b202-435c-4048-8313-0624e2130a6c",
                    "성동구",
                    "02.0$i(월)",
                    4+i,
                    "이번주 토요일에 테니스 내기 한번?~~~~~~~~~~~",
                    "오후 3:00",
                    3+i,
                    listOf("테니스")
                )
            )
        }

        _recommendMatchDataSet.value = rDataSet
        _newMatchDataSet.value = nDataSet
    }


}