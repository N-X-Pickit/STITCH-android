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
    private val _currentPage = MutableLiveData<Int>()

    val currentPage: LiveData<Int>
        get() = _currentPage

    init {
        _currentPage.value = 1
    }


}