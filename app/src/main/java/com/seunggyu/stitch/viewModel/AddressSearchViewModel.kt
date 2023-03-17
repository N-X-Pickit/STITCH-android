import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seunggyu.stitch.data.NaverMapApi
import com.seunggyu.stitch.data.RetrofitApi
import com.seunggyu.stitch.data.RetrofitService
import kotlinx.coroutines.*
import org.json.JSONArray


class AddressSearchViewModel : ViewModel() {
    private val _address = MutableLiveData<String>()
    private val _addressList = MutableLiveData<List<String>?>()
    private val _addressNearList = MutableLiveData<List<String>?>()
    private val _addressNear = MutableLiveData<String>()

    val address: LiveData<String>
        get() = _address
    val addressList: LiveData<List<String>?>
        get() = _addressList
    val addressNearList: LiveData<List<String>?>
        get() = _addressNearList
    var inputAddress = ""
    val addressNear: LiveData<String>
        get() = _addressNear

    fun geocoding() {
        val service = NaverMapApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.geocoding("gnz3irqyja", "xty1BKFlN34aAJVUm66PB65Hyc2cXzPA1FLe7hs3",
                inputAddress
            )

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result>>>>>>>", result.toString())
                        val addressesList = it.addresses
                        val newList = mutableListOf<String>()
                        addressesList?.let {
                            for (address in addressesList) {
                                val jibunAddress = address?.jibunAddress
                                if (jibunAddress != null) {
                                    newList.add(jibunAddress)
                                }
                            }
                            _addressList.value = newList
                            Log.e("asdasd",_addressList.value.toString())
                        }


                    }
                } else {
                    Log.e("TAG", response.code().toString())
                }
            }
        }
    }

    fun getNearAddress(_address: String) {
        val service = RetrofitApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getNearAddress(_address)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result>>>>>>>", result.toString())
                        _addressNearList.value = it.value
                        _addressNear.value = _address
                    }
                } else {
                    Log.e("TAG", response.code().toString())
                }
            }
        }
    }
}
