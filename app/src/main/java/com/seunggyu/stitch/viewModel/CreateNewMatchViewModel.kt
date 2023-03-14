package com.seunggyu.stitch.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CreateNewMatchViewModel : ViewModel() {
    private val _currentPage = MutableLiveData<Int>(1)
    val currentPage: LiveData<Int>
        get() = _currentPage

    private val _type = MutableLiveData<String>()
    val type: LiveData<String>
        get() = _type

    private val _eventType = MutableLiveData<String>()
    val eventType: LiveData<String>
        get() = _eventType

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String>
        get() = _imageUrl

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _detail = MutableLiveData<String>()
    val deatil: LiveData<String>
        get() = _detail

    private val _startTime = MutableLiveData<String>()
    val startTime: LiveData<String>
        get() = _startTime

    private val _duration = MutableLiveData<String>()
    val duration: LiveData<String>
        get() = _duration

    private val _location = MutableLiveData<String>()
    val location: LiveData<String>
        get() = _location

    private val _maxCapacity = MutableLiveData<String>()
    val maxCapacity: LiveData<String>
        get() = _maxCapacity

    private val _fee = MutableLiveData<String>()
    val fee: LiveData<String>
        get() = _fee

    private val _isAllWriten = MutableLiveData<Boolean>()
    val isAllWriten: LiveData<Boolean>
        get() = _isAllWriten

    private val _allWritenFlow = flow<Boolean> {
        val emptyType = type.value?.isEmpty()
        val emptyEventType = eventType.value?.isEmpty()
        val emptyName = name.value?.isEmpty()
        val emptyStartTime = startTime.value?.isEmpty()
        val emptyDuration = duration.value?.isEmpty()
        val emptyLocation = location.value?.isEmpty()
        val emptyMaxCapacity = maxCapacity.value?.isEmpty()
        val emptyFee = fee.value?.isEmpty()

        emit(emptyType == false && emptyEventType == false
                && emptyName == false && emptyStartTime == false
                && emptyDuration == false && emptyLocation == false
                && emptyMaxCapacity == false && emptyFee == false
        )
    }
    val allWritenFlow: Flow<Boolean>
        get() = _allWritenFlow

    init {
        _currentPage.value = 1
    }

    fun nextPage() {
        _currentPage.value = _currentPage.value!! + 1
    }

    fun setCurrentPage(page: Int) {
        _currentPage.value = page
    }

    fun checkAllWritenFlow() {
        viewModelScope.launch {
            allWritenFlow.collectLatest {
                _isAllWriten.value = it
            }
        }
    }

    fun addDuration() {

    }

    fun removeDuration() {

    }

    fun addParticipant() {

    }

    fun removeParticipant() {

    }
}