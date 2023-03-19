import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seunggyu.stitch.data.NaverMapApi
import com.seunggyu.stitch.data.NaverMapSearchApi
import com.seunggyu.stitch.data.RetrofitApi
import com.seunggyu.stitch.data.RetrofitService
import com.seunggyu.stitch.data.model.Location
import kotlinx.coroutines.*
import org.json.JSONArray


class MatchLocationMapViewModel : ViewModel() {
    private val _locationAddress = MutableLiveData<String>()
    private val _locationList = MutableLiveData<List<Location>?>()
    private val _addressList = MutableLiveData<List<String>?>()
    private val _addressNearList = MutableLiveData<List<String>?>()
    private val _addressNear = MutableLiveData<String>()
    private val _selectedAddrress = MutableLiveData<String>()
    private val _selectedLocation = MutableLiveData<Location>()
    private val _topAddress = MutableLiveData<String>()
    private var _latitude = ""
    private var _longitude = ""

    val locationAddress: LiveData<String>
        get() = _locationAddress
    val locationList: LiveData<List<Location>?>
        get() = _locationList
    val addressList: LiveData<List<String>?>
        get() = _addressList
    val addressNearList: LiveData<List<String>?>
        get() = _addressNearList
    val selectedAddress: LiveData<String>
        get() = _selectedAddrress
    val selectedLocation: LiveData<Location>
        get() = _selectedLocation

    var inputAddress = ""
    val latitude
        get() = _latitude
    val longitude
        get() = _longitude
    val addressNear: LiveData<String>
        get() = _addressNear
    val topAddress: LiveData<String>
        get() = _topAddress

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
                        } ?: run {

                        }


                    }
                } else {
                    Log.e("TAG", response.code().toString())
                }
            }
        }
    }

    fun reverseGeocoding(lat: String, lng: String) {
        val service = NaverMapApi.retrofitService
        Log.e("실행","viewModel.reverseGeocoding")
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.reverseBuildingGeocoding(
                "gnz3irqyja", "xty1BKFlN34aAJVUm66PB65Hyc2cXzPA1FLe7hs3",
                "coordsToaddr",
                "$lng,$lat",
            "json",
                "addr,admcode,roadaddr"
            )
            Log.e("reverseGeocoding","inprocess")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result>>>>>>>", result.toString())
                        val area1 = it.results?.get(0)?.region?.area1?.name.toString()
                        val area2 = it.results?.get(0)?.region?.area2?.name.toString()
                        val area3 = it.results?.get(0)?.region?.area3?.name.toString()
                        val addressNumber = it.results?.get(0)?.land?.number1.toString()
                        val addressSubNumber = it.results?.get(0)?.land?.number2.toString()
                        val buildingName = when {
                            it.results?.size == 3 -> it.results[2].land?.addition0?.value.toString()
                            it.results?.size == 2 -> it.results[1].land?.addition0?.value.toString()
                            else -> ""
                        }
                        val address = java.lang.StringBuilder()
                        address.apply {
                            append("$area1 $area2 $area3 $addressNumber")
                            if(addressSubNumber.isNotEmpty()) append("-$addressSubNumber")
                            if(buildingName.isNotEmpty() && buildingName != "null") append("\n$buildingName")
                        }
                        setTopAddress(address.toString())
                        Log.e("address============", address.toString())

                    }
                } else {
                    Log.e("TAG", response.code().toString())
                }
            }
        }
    }
    fun locationSearch() {
        val service = NaverMapSearchApi.retrofitService

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.searchLocation(
                "XRVBJ5nIc1l5IvgupcWa", "BqO4n_YXoK",
                inputAddress, "10","1", "random"
            )

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    result?.let {
                        Log.e("result>>>>>>>", result.toString())
                        val addressesList = it.items
                        val newList = mutableListOf<Location>()
                        addressesList?.let {
                            for (address in addressesList) {
                                val title = address?.title?.replace("<b>", "")
                                    ?.replace("</b>", "")
                                if (title != null) {
                                    newList.add(Location(title, address.mapx!!, address.mapy!!))
                                }
                            }
                            _locationList.value = newList
                            Log.e("locationList", _locationList.value.toString())
                        }
                    } ?: run {

                    }
                } else {
                    Log.e("TAG", response.code().toString())
                }
            }
        }
    }

    fun setTopAddress(address: String) {
        _topAddress.value = address
    }

    fun setLatitude(lat: String) {
        _latitude = lat
    }
    fun setLongitude(lng: String) {
        _longitude = lng
    }

    fun setSelectedAddress(address: String) {
        _selectedAddrress.value = address
    }
    fun setSelectedLocation(location: Location) {
        _selectedLocation.value = location
    }
}
