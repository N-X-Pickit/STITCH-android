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
    private val _locationAddress = MutableLiveData<String>()
    private val _addressList = MutableLiveData<List<String>?>()
    private val _addressNearList = MutableLiveData<List<String>?>()
    private val _addressNear = MutableLiveData<String>()
    private val _selectedAddrress = MutableLiveData<String>()

    val locationAddress: LiveData<String>
        get() = _locationAddress
    val addressList: LiveData<List<String>?>
        get() = _addressList
    val addressNearList: LiveData<List<String>?>
        get() = _addressNearList
    val selectedAddress: LiveData<String>
        get() = _selectedAddrress
    var inputAddress = ""
    val addressNear: LiveData<String>
        get() = _addressNear

    fun geocoding() {
        val service = NaverMapApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.geocoding(
                "gnz3irqyja", "xty1BKFlN34aAJVUm66PB65Hyc2cXzPA1FLe7hs3",
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
                            Log.e("asdasd", _addressList.value.toString())
                        }


                    }
                } else {
                    Log.e("TAG", response.code().toString())
                }
            }
        }
    }

    private fun getNearAddress(_address: String) {
        val service = RetrofitApi.retrofitService
        Log.e("getNearAddress", "1")
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("getNearAddress", "1.25")
            Log.e("_address", _address)
            val response = service.getNearAddress(_address)
            Log.e("getNearAddress", "1.5")

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.e("getNearAddress", "2")

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

    fun reverseGeocoding(lat: String, lng: String) {
        val service = NaverMapApi.retrofitService
        Log.e("실행","ㅁㄴㅇㅁㄴㅇ")
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.reverseGeocoding(
                "gnz3irqyja", "xty1BKFlN34aAJVUm66PB65Hyc2cXzPA1FLe7hs3",
                "coordsToaddr",
                "$lat,$lng",
            "json",
                "legalcode,admcode"
            )
            Log.e("reverseGeocoding","inprocess")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result>>>>>>>", result.toString())
                        val area1 = it.results?.get(0)?.region?.area1?.name.toString()
                        val area2 = it.results?.get(0)?.region?.area2?.name.toString()
                        Log.e("area1 + area2 ", "$area1 $area2")
                        getNearAddress("$area1 $area2")
                    }
                } else {
                    Log.e("TAG", response.code().toString())
                }
            }
        }
    }

    fun setSelectedAddress(address: String) {
        _selectedAddrress.value = address
    }
}
