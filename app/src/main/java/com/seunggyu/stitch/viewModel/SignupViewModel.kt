package com.seunggyu.stitch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    private val _currentPage = MutableLiveData<Int>()
    private val _progress = MutableLiveData<Int>(20)
    private val _nextButtonAvailable = MutableLiveData<Boolean>()
    private var _inputName = ""
    private val _inputHome = MutableLiveData<String>()
    private val _location = MutableLiveData<String>()
    private val _profileSelectedItem = MutableLiveData<Int>()
    private val _interestingSelectedItem = MutableLiveData<ArrayList<String>>()
    private var _interestingItems = ArrayList<String>()
    private var _loginId = ""
    val loginId: String
        get() = _loginId
    private var _loginNickName = ""
    val loginNickName: String
        get() = _loginNickName
    private var _loginImageUrl = ""
    val loginImageUrl: String
        get() = _loginImageUrl

    val currentPage: LiveData<Int>
        get() = _currentPage

    val progress: LiveData<Int>
        get() = _progress

    val nextButtonAvailable: LiveData<Boolean>
        get() = _nextButtonAvailable

    val inputName: String
        get() = _inputName

    val inputHome: LiveData<String>
        get() = _inputHome

    val location: LiveData<String>
        get() = _location

    val profileSelectedItem: LiveData<Int>
        get() = _profileSelectedItem

    val interestingSelectedItem: LiveData<ArrayList<String>>
        get() = _interestingSelectedItem

    val interestingItems: ArrayList<String>
        get() = _interestingItems

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
        _progress.value = 20
        _nextButtonAvailable.value = false
        _inputHome.value = ""
        _profileSelectedItem.value = -1
        _interestingSelectedItem.value = arrayListOf()
        _interestingItems = arrayListOf()
    }

    fun nextPage() {
//        if ((progress.value ?: 0) in 20..80 ) {
//            viewModelScope.launch {
//                repeat(20) {
//                    _progress.value = (_progress.value ?: 0) + 1
//                    delay(10)
//                }
//                _currentPage.value = (_currentPage.value ?: 0) + 1
//            }
//        }
        _currentPage.value = (_currentPage.value ?: 0) + 1
        disableButton()
    }

    fun prevPage() {
//        if ((progress.value ?: 0) in 40..100 ) {
//            viewModelScope.launch {
//                repeat(20) {
//                    _progress.value = (_progress.value ?: 0) - 1
//                    delay(10)
//                }
//                _currentPage.value = (_currentPage.value ?: 0) - 1
//            }
//        }
        _currentPage.value = (_currentPage.value ?: 0) - 1
    }

    fun ableButton() {
        _nextButtonAvailable.value = true
    }

    fun disableButton() {
        _nextButtonAvailable.value = false
    }

    fun setInputName(inputName: String) {
        _inputName = inputName
    }

    fun setInputHome(inputHome: String) {
        _inputHome.value = inputHome
    }

    fun setProfileSelectedItem(num: Int) {
        _profileSelectedItem.value = num
        ableButton()
    }

    fun addInterestingSelectedItem(item: String) {
        _interestingItems.add(item)
        _interestingSelectedItem.value = _interestingItems
        Log.e("_interestingItems.size", _interestingItems.size.toString())
        Log.e("_interestingSelectedItem.value!!.size", _interestingSelectedItem.value!!.size.toString())
        Log.e("_interestingSelectedItem.value!!", _interestingSelectedItem.value!!.toString())
    }

    fun deleteInterestingSelectedItem(item: String) {
        _interestingItems.remove(item)
        _interestingSelectedItem.value = _interestingItems
    }

    fun checkInteresting(item: String) : Boolean? {
        Log.e("_interestingSelectedItem.value?.contains($item)",_interestingSelectedItem.value?.contains(item).toString())

        return _interestingSelectedItem.value?.contains(item)
    }

    fun setLoginId(id: String) {
        _loginId = id
    }
    fun setLoginNickName(name: String) {
        _loginNickName = name
    }
    fun setLoginImageUrl(url: String) {
        _loginImageUrl = url
    }
}