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

class LoginViewModel : ViewModel() {
    private var _loginEmail = MutableLiveData<String>()
//    private var _googleToken = MutableLiveData<String>()
//    private var _googleTokenAuth = MutableLiveData<String>()
    private var _loginNickName = MutableLiveData<String>()
    private var _loginImageUrl = MutableLiveData<String>()
    private var _loginId = MutableLiveData<String>()

    val loginEmail: LiveData<String>
        get() = _loginEmail

    val loginNickName: LiveData<String>
        get() = _loginNickName

    val loginImageUrl: LiveData<String>
        get() = _loginImageUrl

    val loginId: LiveData<String>
        get() = _loginId

    init {
        _loginEmail.value = ""
        _loginId.value = ""
        _loginImageUrl.value = ""
        _loginNickName.value = ""
    }

    fun setLoginEmail(email: String) {
        _loginEmail.value = email
    }
    fun setLoginId(id: String) {
        _loginId.value = id
    }
    fun setLoginImageUrl(imageUrl: String) {
        _loginImageUrl.value = imageUrl
    }
    fun setLoginNickName(name: String) {
        _loginNickName.value = name
    }
}