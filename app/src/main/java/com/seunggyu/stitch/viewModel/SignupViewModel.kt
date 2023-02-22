package com.seunggyu.stitch.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {
    private val _currentPage = MutableLiveData<Int>()

    val currentPage: LiveData<Int>
        get() = _currentPage

    val items = listOf(
        "삶과 운동의 균형이 중요하다 생각해요",
        "새로운 사람들과 다양한 경험을 해보고 싶어요",
        "반복되는 일상에 특별함을 원해요",
        "끊임없이 배우고 성장하려고 해요",
        "혼자 하기 두려웠던 것들을 함께 해보고싶어요",
        "결과보다 시작이 더 중요하다고 생각해요",
        "몰랐던 나의 운동 취미를 발굴 해내고 싶어요",
        "경험을 통해 다양한 가치들을 얻는다고 생각해요",
        "내 삶을 내가 좋아하는 것으로 채울래요!",
        "관심 운동을 더 깊게 파고들고 싶어요",
    )

    init {
        _currentPage.value = 1
    }

    fun nextPage() {
        
    }
}