package com.seunggyu.stitch.ui

import AddressSearchViewModel
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.seunggyu.stitch.R
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.adapter.GpsResultRecyclerViewAdapter
import com.seunggyu.stitch.databinding.ActivityAddressBinding
import kotlinx.coroutines.*


class AddressSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding
    private val viewModel: AddressSearchViewModel by viewModels()

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = Runnable {
        viewModel.geocoding()
    }

    private val gpsRecyclerViewAdapter: GpsResultRecyclerViewAdapter by lazy {
        GpsResultRecyclerViewAdapter(binding.rvGpsResult){viewModel.setSelectedAddress(it)}
    }

    private var lat =""
    private var lng =""

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    private lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_address)

        mLocationRequest =  LocationRequest.create().apply {

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        }

        uiInit()
        init()
        initRecyclerView()
        initObserver()
    }

    private fun uiInit() {
    }

    private fun init() {
        with(binding) {
            ivAddressClear.setOnClickListener {
                etAddressSearch.setText("")
            }

            btnGps.setOnClickListener {
                etAddressSearch.setText("")
                // 위치 관리자 객체 참조하기
                val lm = getSystemService(LOCATION_SERVICE) as LocationManager

                if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this@AddressSearchActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        0
                    )
                } else {
                    // 가장최근 위치정보 가져오기
                    val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    lat = location?.latitude.toString()
                    lng = location?.longitude.toString()
                    if (location != null) {
                        Log.e("latlng","${location.latitude} ${location.longitude}")
                        viewModel.reverseGeocoding(lng, lat)
                    }

                    // 위치정보를 원하는 시간, 거리마다 갱신해준다.
                    lm.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 1000, 1f
                    ) { location ->
                        lng = location.longitude.toString()
                        lat = location.latitude.toString()
                    }
                }
//                Log.e("sdk 버전", Build.VERSION.SDK_INT.toString())
//                checkLocationPermission {
//                    val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//                    val userNowLocation: Location? = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
//                    viewModel.reverseGeocoding(userNowLocation?.latitude.toString(), userNowLocation?.longitude.toString())
//                    Log.e("latlan", "${userNowLocation?.longitude}, ${userNowLocation?.latitude}")
//                }
            }

            btnAddressBack.setOnClickListener {
                finish()
            }

            etAddressSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val afterText = s.toString()

                    if (afterText.isNotEmpty()) {
                        binding.ivAddressClear.isVisible = true

                        if (afterText.length > 1 && afterText.last() != '도' && afterText.last() != '시') {
                            Log.e("load", "시x, 도x")
                            handler.removeCallbacks(runnable)
                            handler.postDelayed(runnable, 500)
                            viewModel.inputAddress = afterText
                            Log.e("onTextChanged", afterText)
                        } else {
                            Log.e("clAddressResult", "#5")
                            binding.clAddressResult.isVisible = false
                            binding.clAddressNull.isVisible = true
                        }
                    } else {
                        binding.ivAddressClear.isVisible = false

                        Log.e("clAddressResult", "#4")
                        binding.clAddressResult.isVisible = false
                        binding.clAddressNull.isVisible = false
                    }
                }
            })

            btnAddressResearch.setOnClickListener {
                etAddressSearch.setText("")
                Log.e("clAddressResult", "#3")
                binding.clAddressNull.isVisible = false
                binding.clAddressResult.isVisible = false

                // 키보드 올리기
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(binding.etAddressSearch, 0)
            }
        }

    }

    private fun initObserver() {
        // 리사이클러뷰에서 선택한 동네
        viewModel.selectedAddress.observe(this) {
            Log.e("selectedAddress",it.toString())
            val intent = Intent()
            intent.putExtra("key", it)
            setResult(RESULT_OK, intent)
            finish()
        }

        // 주소 조회 리스트
        viewModel.addressList.observe(this) {
            it?.let {
                if (it.isNotEmpty()) {
                    Log.e("isDetailAddress", "true")
                    gpsRecyclerViewAdapter.submitList(it)
                    Log.e("it.size", it.size.toString())
                    binding.clAddressResult.isVisible = true
                    binding.clAddressNull.isVisible = false
                    binding.tvAddressResult.text = getString(
                        R.string.address_result_name,
                        binding.etAddressSearch.text
                    )
                } else {
                    Log.e("clAddressResult", "#1")
                    binding.clAddressResult.isVisible = false
                    binding.clAddressNull.isVisible = true
                }
            } ?: run {
                SnackBarCustom.make(
                    binding.clAddressParent,
                    getString(R.string.snackbar_network_error)
                )
                    .show()
            }
        }
        // 근처 동네 리스트
        viewModel.addressNearList.observe(this) {
            binding.clAddressResult.isVisible = true
            binding.clAddressNull.isVisible = false
            gpsRecyclerViewAdapter.submitList(it)
        }

        viewModel.addressNear.observe(this) {
            Log.e("addresnear", "OBSERVED")
            it?.let {
                binding.clAddressResult.isVisible = true
                binding.clAddressNull.isVisible = false
                binding.tvAddressResult.text =
                    getString(R.string.address_result_near, viewModel.addressNear.value.toString())
            } ?: run {
                SnackBarCustom.make(
                    binding.clAddressParent,
                    getString(R.string.snackbar_network_error)
                )
                    .show()
            }
        }
    }

    private fun initRecyclerView() {
        gpsRecyclerViewAdapter
        binding.rvGpsResult.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = gpsRecyclerViewAdapter
            Log.e("RecyclerView init", "successed")
        }
    }
    
    override fun onBackPressed() {
        finish()
    }

    private fun checkLocationPermission(onSucces: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onSucces()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ),
                1
            )
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.size > 0) {

            // 권한 허가
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        0
                    )
                }
                val lm = getSystemService(LOCATION_SERVICE) as LocationManager
                val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                lng = location!!.longitude.toString()
                lat = location.latitude.toString()
            } else {
                SnackBarCustom.make(binding.root, "권한이 거부되어 해당 기능을 실행할 수 없습니다.").show()
            }
        } else {
            Toast.makeText(this, "오류", Toast.LENGTH_SHORT).show()
        }
    }

}