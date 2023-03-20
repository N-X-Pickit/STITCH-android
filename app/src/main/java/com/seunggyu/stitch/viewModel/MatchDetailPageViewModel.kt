import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seunggyu.stitch.data.NaverMapApi
import com.seunggyu.stitch.data.RetrofitApi
import com.seunggyu.stitch.data.model.response.Match
import kotlinx.coroutines.*


class MatchDetailPageViewModel : ViewModel() {
    // 이전 화면에서 전달받은 매치 아이디
    // 이 데이터로 서버로 매치 상세 정보 수집 요청
    private val _passedMatchId = MutableLiveData<String?>()
    val passedMatchId: LiveData<String?>
        get() = _passedMatchId

    /***************************** 매치 정보 수신 데이터 ******************************/
//    // 아이디
//    private val _matchId = MutableLiveData<String?>()
//    val matchId: LiveData<String?>
//        get() = _matchId
//    // 타입
//    private val _matchType = MutableLiveData<String?>()
//    val matchType: LiveData<String?>
//        get() = _matchType
//    // 주소
//    private val _location = MutableLiveData<String?>()
//    val location: LiveData<String?>
//        get() = _location
//    // 이미지 url
//    private val _imageUrl = MutableLiveData<String?>()
//    val imageUrl: LiveData<String?>
//        get() = _imageUrl
//    // 제목
//    private val _name = MutableLiveData<String?>()
//    val name: LiveData<String?>
//        get() = _name
//    // 상세 내용
//    private val _detail = MutableLiveData<String?>()
//    val detail: LiveData<String?>
//        get() = _detail
//    // 시작 시간 YYYY-MM-DD HH:mm
//    private val _startTime = MutableLiveData<String?>()
//    val startTime: LiveData<String?>
//        get() = _startTime
//    // 진행 시간 (분단위)
//    private val _duration = MutableLiveData<Int?>()
//    val duration: LiveData<Int?>
//        get() = _duration
//    // 종목
//    private val _eventType = MutableLiveData<String?>()
//    val eventType: LiveData<String?>
//        get() = _eventType
//    // 호스트 id
//    private val _hostId = MutableLiveData<String?>()
//    val hostId: LiveData<String?>
//        get() = _hostId
//    // 참가정원
//    private val _maxCapacity = MutableLiveData<Int?>()
//    val maxCapacity: LiveData<Int?>
//        get() = _maxCapacity
//    // 참가비
//    private val _fee = MutableLiveData<Int?>()
//    val fee: LiveData<Int?>
//        get() = _fee
//    // latitude
//    private val _latitude = MutableLiveData<String?>()
//    val latitude: LiveData<String?>
//        get() = _latitude
//    // longitude
//    private val _longitude = MutableLiveData<String?>()
//    val longitude: LiveData<String?>
//        get() = _longitude
//    // Teach여부
//    private val _teach = MutableLiveData<Boolean?>()
//    val teach: LiveData<Boolean?>
//        get() = _teach
//    // 현재인원
//    private val _numOfMembers = MutableLiveData<Int?>()
//    val numOfMembers: LiveData<Int?>
//        get() = _numOfMembers

    private val _matchData = MutableLiveData<Match?>()
    val matchData: LiveData<Match?>
            get() = _matchData
    /****************************************************************************/


    fun getMatchDataFromServer() {
        val service = RetrofitApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getMatchDetail(
                _passedMatchId.value
            )

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result>>>>>>>", result.toString())
//                        _matchId.value = it.match?.id
//                        _matchType.value = it.match?.type
//                        _location.value = it.match?.location
//                        _imageUrl.value = it.match?.imageUrl
//                        _name.value = it.match?.name
//                        _detail.value = it.match?.detail
//                        _startTime.value = it.match?.startTime
//                        _duration.value = it.match?.duration
//                        _eventType.value = it.match?.eventType
//                        _hostId.value = it.match?.hostId
//                        _maxCapacity.value = it.match?.maxCapacity
//                        _fee.value = it.match?.fee
//                        _latitude.value = it.match?.latitude
//                        _longitude.value = it.match?.longitude
//                        _teach.value = it.match?.teach
//                        _numOfMembers.value = it.match?.numOfMembers

                        _matchData.value = it.match
                    }
                } else {
                    Log.e("TAG", response.code().toString())
                }
            }
        }
    }

}
