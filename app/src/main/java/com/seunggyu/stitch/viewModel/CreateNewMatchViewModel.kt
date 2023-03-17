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

    private val _sportsType = MutableLiveData<String>()
    val sportsType: LiveData<String>
        get() = _sportsType

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String>
        get() = _imageUrl

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _detail = MutableLiveData<String>()
    val detail: LiveData<String>
        get() = _detail

    private val _startDate = MutableLiveData<String>()
    val startDate: LiveData<String>
        get() = _startDate

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

    private var _startAmpm = MutableLiveData<String>()
    val startAmpm: LiveData<String>
        get() = _startAmpm

    private var _startHour = ""
    val startHour: String
        get() = _startHour

    private var _startMinute = ""
    val startMinute: String
        get() = _startMinute

    private val _isAllWriten = MutableLiveData<Boolean>()
    val isAllWriten: LiveData<Boolean>
        get() = _isAllWriten

    private val _allWritenFlow = flow<Boolean> {
        val emptyType = type.value?.isEmpty()
        val emptySportsType = sportsType.value?.isEmpty()
        val emptyName = name.value?.isEmpty()
        val emptyStartDate = startDate.value?.isEmpty()
        val emptyStartTime = startTime.value?.isEmpty()
        val emptyDuration = duration.value?.isEmpty()
        val emptyLocation = location.value?.isEmpty()
        val emptyMaxCapacity = maxCapacity.value?.isEmpty()
        val emptyFee = fee.value?.isEmpty()

        if (emptyMaxCapacity != null) {
            emit(emptyType == false && emptySportsType == false
                    && emptyName == false && emptyStartDate == false && emptyStartTime == false
                    && emptyDuration == false && emptyLocation == false
                    && emptyMaxCapacity == false && emptyFee == false
            )
        }
    }

    private val _timeChangedFlow = flow<String> {
        _startTime.value =
            if(_startAmpm.value?.toInt() == 0) "${"%02d".format(startHour)}:${"%02d".format(_startMinute)}"
            else "${_startHour.toInt() + 12}:${"%02d".format(_startMinute)}"
        emit(_startTime.value as String)
    }
    val allWritenFlow: Flow<Boolean>
        get() = _allWritenFlow

    val timeChangedFlow: Flow<String>
        get() = _timeChangedFlow

    init {
        _currentPage.value = 1
        _startTime.value = ""
        _startDate.value = ""
        _duration.value = "30"
        _location.value = ""
        _maxCapacity.value = "2"
        _fee.value = ""
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

    fun setSportsType(sport: String) {
        _sportsType.value = sport
        Log.e("_sportsType",sport)
        nextPage()
    }

    fun setStartDate(date: String) {
        _startDate.value = date
    }

    fun setStartTime(ampm: Int, hour: String, minute: String) {
        _startAmpm.value = ampm.toString()
        _startHour = hour
        _startMinute = minute
        viewModelScope.launch {
            _startTime.value =
                if(_startAmpm.value == "0" || _startHour.toInt() == 12) {
                    if(_startAmpm.value == "0" && startHour.toInt() == 12){
                        "${"%02d".format(startHour.toInt()-12)}:${"%02d".format(_startMinute.toInt() * 10)}"
                        return@launch
                    }
                    "${"%02d".format(startHour.toInt())}:${"%02d".format(_startMinute.toInt() * 10)}"
                }
                else "${_startHour.toInt() + 12}:${"%02d".format(_startMinute.toInt() * 10)}"
        }
    }

//    fun addDuration() {
//
//    }
//
//    fun removeDuration() {
//
//    }
//
//    fun addParticipant() {
//
//    }
//
//    fun removeParticipant() {
//
//    }
}