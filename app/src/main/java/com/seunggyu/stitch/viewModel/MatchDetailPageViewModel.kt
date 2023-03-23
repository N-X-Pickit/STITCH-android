import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seunggyu.stitch.GlobalApplication
import com.seunggyu.stitch.data.NaverMapApi
import com.seunggyu.stitch.data.RetrofitApi
import com.seunggyu.stitch.data.model.request.JoinMatchRequest
import com.seunggyu.stitch.data.model.response.JoinedMember
import com.seunggyu.stitch.data.model.response.Match
import com.seunggyu.stitch.data.model.response.SignupResponse
import kotlinx.coroutines.*


class MatchDetailPageViewModel : ViewModel() {
    // 이전 화면에서 전달받은 매치 아이디
    // 이 데이터로 서버로 매치 상세 정보 수집 요청
    private val _passedMatchId = MutableLiveData<String?>()
    val passedMatchId: LiveData<String?>
        get() = _passedMatchId

    // 호스트 Id
    private val _hostId = MutableLiveData<String?>()
    val hostId: LiveData<String?>
        get() = _hostId

    // 호스트 이름
    private val _hostName = MutableLiveData<String?>()
    val hostName: LiveData<String?>
        get() = _hostName

    // 호스트 프로필 이미지
    private val _hostImageUrl = MutableLiveData<String?>()
    val hostImageUrl: LiveData<String?>
        get() = _hostImageUrl

    // 참여 여부
    private val _isAlreadyMember = MutableLiveData<Boolean>(false)
    val isAlreadyMember: LiveData<Boolean>
        get() = _isAlreadyMember

    // 참여 여부
    private val _joinedMember = MutableLiveData<List<JoinedMember>>()
    val joinedMember: LiveData<List<JoinedMember>>
        get() = _joinedMember

    // 참여완료 여부
    private val _joinSuccess = MutableLiveData<Boolean>()
    val joinSuccess: LiveData<Boolean>
        get() = _joinSuccess
    /***************************** 매치 정보 수신 데이터 ******************************/

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

                        _matchData.value = it.match
                        _joinedMember.value = it.joinedMembers!!
                    }
                } else {
                    Log.e("TAG", response.code().toString())
                }
            }
        }
    }

    fun getHostInfo(id: String) {
        val service = RetrofitApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getUserData(id)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result>>>>>>>", result.toString())

                        _hostId.value = it.id
                        _hostName.value = it.name
                        _hostImageUrl.value = it.imageUrl
                    }
                } else {
                    Log.e("TAG", response.code().toString())
                }
            }
        }
    }

    fun joinMatch() {
        val service = RetrofitApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.joinMatch(GlobalApplication.prefs.getCurrentUser().id,
            JoinMatchRequest(matchData.value?.id.toString()))
            Log.e("userId", GlobalApplication.prefs.getCurrentUser().id)
            Log.e("Id.value", matchData.value?.id.toString())
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result>>>>>>>", result.toString())

                        _isAlreadyMember.value = true
                        _joinSuccess.value = true
                    }
                } else {
                    Log.e("TAG", response.code().toString())
                }
            }
        }
    }

    fun quitMatch() {
        val service = RetrofitApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.quitMatch(GlobalApplication.prefs.getCurrentUser().id,
                matchData.value?.id!!)
            Log.e("userId", GlobalApplication.prefs.getCurrentUser().id)
            Log.e("Id.value", matchData.value?.id.toString())

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result>>>>>>>", result.toString())

                        _isAlreadyMember.value = true
                        _joinSuccess.value = false
                    }
                } else {
                    Log.e("TAG", response.code().toString())
                }
            }
        }
    }

    fun setPassedMatchId(id: String) {
        _passedMatchId.value = id
    }

    fun setMatchData(match: Match) {
        _matchData.value = match
    }

    fun setIsAlreadyMember(boolean: Boolean) {
        _isAlreadyMember.value = boolean
    }

}
