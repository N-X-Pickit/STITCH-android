package com.seunggyu.stitch.viewModel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.storage.StorageReference
import com.seunggyu.stitch.GlobalApplication
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.data.NaverMapApi
import com.seunggyu.stitch.data.RetrofitApi
import com.seunggyu.stitch.data.RetrofitService
import com.seunggyu.stitch.data.model.Location
import com.seunggyu.stitch.data.model.request.CreateNewMatchRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateNewMatchViewModel : ViewModel() {
    private lateinit var createNewMatchRequest: CreateNewMatchRequest

    private val _currentPage = MutableLiveData<Int>(1)
    val currentPage: LiveData<Int>
        get() = _currentPage

    private val _isTeach = MutableLiveData<Boolean>()
    val isTeach: LiveData<Boolean>
        get() = _isTeach

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

    private val _fee = MutableLiveData<Int>()
    val fee: LiveData<Int>
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

    private val _latitude = MutableLiveData<String>()
    val latitude: LiveData<String>
        get() = _latitude

    private val _longitude = MutableLiveData<String>()
    val longitude: LiveData<String>
        get() = _longitude

    private val _uploadImage = MutableLiveData<Bitmap>()
    val uploadImage: LiveData<Bitmap>
        get() = _uploadImage

    private val _createSuccessListener = MutableLiveData<Boolean>(false)
    val createSuccessListener: LiveData<Boolean>
        get() = _createSuccessListener

    private val _createFailedListener = MutableLiveData<Boolean>(false)
    val createFailedListener: LiveData<Boolean>
        get() = _createFailedListener

    private var _numberDuration = 0
    val numberDuration: Int
        get() = _numberDuration

    private var _teach = false
    val teach: Boolean
        get() = _teach


    private val _allWritenFlow = flow<Boolean> {
        val emptySportsType = sportsType.value?.isEmpty()
        val emptyName = name.value?.isEmpty()
        val emptyStartDate = startDate.value?.isEmpty()
        val emptyStartTime = startTime.value?.isEmpty()
        val emptyDuration = duration.value?.isEmpty()
        val emptyLocation = location.value?.isEmpty()
        val emptyMaxCapacity = maxCapacity.value?.isEmpty()

        emit(
            emptySportsType == false
                    && emptyName == false && emptyStartDate == false && emptyStartTime == false
                    && emptyDuration == false && emptyLocation == false
                    && emptyMaxCapacity == false
        )

    }
    val allWritenFlow: Flow<Boolean>
        get() = _allWritenFlow

    private val durationList = arrayOf(
        "30분",
        "1시간",
        "1시간 30분",
        "2시간",
        "2시간 30분",
        "3시간",
        "3시간 30분",
        "4시간",
        "4시간 30분",
        "5시간"
    )
    private var durationPointer = 0

    init {
        _currentPage.value = 1
        _startTime.value = ""
        _startDate.value = ""
        _duration.value = durationList[durationPointer]
        _location.value = ""
        _maxCapacity.value = "2"
        _fee.value = 0
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
                Log.e("checkAllWritenFlow", "_isAllWrite = $it")
                Log.e("====================", "===============")
                Log.e("emptySportsType", sportsType.value?.isEmpty().toString())
                Log.e("emptyName", name.value?.isEmpty().toString())
                Log.e("emptyStartDate", startDate.value?.isEmpty().toString())
                Log.e("emptyStartTIme", startTime.value?.isEmpty().toString())
                Log.e("emptyDuration", duration.value?.isEmpty().toString())
                Log.e("emptyLocation", location.value?.isEmpty().toString())
                Log.e("emptyMaxCapacity", maxCapacity.value?.isEmpty().toString())
            }
        }
    }

    fun setSportsType(sport: String) {
        _sportsType.value = sport
        Log.e("_sportsType", sport)
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
                if (_startAmpm.value == "0" || _startHour.toInt() == 12) {
                    if (_startAmpm.value == "0" && startHour.toInt() == 12) {
                        "${"%02d".format(startHour.toInt() - 12)}:${"%02d".format(_startMinute.toInt() * 10)}"
                        return@launch
                    }
                    "${"%02d".format(startHour.toInt())}:${"%02d".format(_startMinute.toInt() * 10)}"
                } else "${_startHour.toInt() + 12}:${"%02d".format(_startMinute.toInt() * 10)}"
        }
    }

    fun setFee(fee: Int) {
        _fee.value = fee
    }

    fun setTeach(isTeach: Boolean) {
        _isTeach.value = isTeach
    }


    fun setName(name: String) {
        _name.value = name
    }

    fun setDetail(description: String) {
        _detail.value = description
    }

    fun setDuration(duration: String) {
        _duration.value = duration
    }

    fun addDuration() {
        durationPointer++
        setDuration(durationList[durationPointer])
    }

    fun removeDuration() {
        durationPointer--
        setDuration(durationList[durationPointer])
    }

    fun addParticipant() {
        _maxCapacity.value = "${_maxCapacity.value?.toInt()?.plus(1)}"
    }

    fun removeParticipant() {
        _maxCapacity.value = "${_maxCapacity.value?.toInt()?.minus(1)}"

    }

    fun setLocation(location: String) {
        _location.value = location
    }

    fun setLatitude(latitude: String) {
        _latitude.value = latitude
    }

    fun setLongitude(longitude: String) {
        _longitude.value = longitude
    }

    fun setUploadImage(image: Bitmap) {
        _uploadImage.value = image
    }

    fun setImageUrl(url: String) {
        _imageUrl.value = url
    }

    fun setCreateSuccessListener(success: Boolean) {
        _createSuccessListener.value = success
    }

    fun setCreateFailedListener(failed: Boolean) {
        _createFailedListener.value = failed
    }

    fun createNewMatch() {
        val service = RetrofitApi.retrofitService
        val matchId = System.currentTimeMillis().toString()
        createNewMatchRequest = CreateNewMatchRequest(
            id = matchId,
            imageUrl = imageUrl.value,
            detail = detail.value,
            duration = numberDuration,
            eventType = sportsType.value,
            fee = fee.value?.toInt(),
            hostId = GlobalApplication.prefs.getString("userId"),
            location = location.value!!.split("\n")[0],
            maxCapacity = maxCapacity.value?.toInt(),//
            name = name.value,
            latitude = latitude.value,
            longitude = longitude.value,
            numOfMembsers = 1,
            startTime = "${startDate.value} ${startTime.value}",
            isTeach = isTeach.value,
        )


        CoroutineScope(Dispatchers.IO).launch {
            val response = service.createMatch(createNewMatchRequest)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result>>>>>>>", result.toString())
                        setCreateSuccessListener(true)
                        }
                    }
                else {
                    Log.e("TAG", response.code().toString())
                    setCreateFailedListener(true)
                }
            }
        }
    }

    fun setNumberDuration(duration: Int) {
        _numberDuration = duration
    }
}