package com.seunggyu.stitch.ui

import MatchLocationMapViewModel
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.seunggyu.stitch.R
import com.seunggyu.stitch.Util.Constants.RESULT_LOCATION
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.databinding.ActivityMatchLocationBinding
import com.seunggyu.stitch.ui.fragment.newmatch.AddressSearchBottomFragment
import kotlinx.coroutines.Runnable


class MatchLocationMapActivity : FragmentActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMatchLocationBinding
    private val viewModel: MatchLocationMapViewModel by viewModels()

    private var addressSearchBottomFragment: AddressSearchBottomFragment? = null

    private var lat =""
    private var lng =""

    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    private lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match_location)

        mLocationRequest =  LocationRequest.create().apply {

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        }

        uiInit(savedInstanceState)
        init()
        initObserver()
    }

    override fun onMapReady(_naverMap: NaverMap) {
        naverMap = _naverMap

        //배경 지도 선택
        naverMap.mapType = NaverMap.MapType.Basic
        // 회전 불가 설정
        naverMap.uiSettings.isRotateGesturesEnabled = false
        // 기울이기 불가 설정
        naverMap.uiSettings.isTiltGesturesEnabled = false
        // sdk 제공 위치 버튼 제거
        naverMap.uiSettings.isLocationButtonEnabled = false
        // 네이버 로고 마진 수정
        naverMap.uiSettings.setLogoMargin(100, 0, 0, 400)

        //위치 및 각도 조정
        val cameraPosition = CameraPosition(
            LatLng(37.638016, 126.671183),  // 위치 지정
            17.0
        ) // 줌 레벨
        naverMap.cameraPosition = cameraPosition
//        val marker = Marker()
//        marker.position = LatLng(MenuActivity.storeLatitude, MenuActivity.storeLongitude)
//        marker.captionText = MenuActivity.restaurantNameForInfo
//        marker.map = naverMap
        naverMap.addOnCameraChangeListener { reason, animated ->
            Log.i("NaverMap", "카메라 변경 - reason: $reason, animated: $animated")

            // 주소 텍스트 세팅 및 확인 버튼 비활성화
            binding.tvMatchLocationAddress.run {
                text = "위치 이동 중"
                setTextColor(getColor(R.color.primary))
            }
            binding.btnMatchLocationComplete.run {
                setTextColor(getColor(R.color.gray_11))
                isEnabled = false
            }
            binding.cvMatchLocationGuide.isVisible = false

        }
        // 카메라의 움직임 종료에 대한 이벤트 리스너 인터페이스.
        naverMap.addOnCameraIdleListener {

            getAddress(
                naverMap.cameraPosition.target.latitude,
                naverMap.cameraPosition.target.longitude
            )
            binding.cvMatchLocationGuide.isVisible = true

            Log.e("카메라 이동 완료 좌표" , "\nLatitude : ${viewModel.latitude}\nLongitude : ${viewModel.longitude}")
        }

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
    }

    private fun uiInit(savedInstanceState: Bundle?) {
        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)

        addressSearchBottomFragment = AddressSearchBottomFragment.show(supportFragmentManager, R.id.fl_location_bottom_sheet)
    }

    private fun init() {
        with(binding) {
            btnMatchLocationComplete.setOnClickListener {
                val intent = Intent()
                intent.putExtra("address", viewModel.address)
                intent.putExtra("latitude", viewModel.latitude)
                intent.putExtra("longitude", viewModel.longitude)
                setResult(RESULT_LOCATION, intent)
                finish()
            }

            btnMatchLocationCurrentGps.setOnClickListener {
                // 위치 관리자 객체 참조하기
                val lm = getSystemService(LOCATION_SERVICE) as LocationManager

                if (ContextCompat.checkSelfPermission(
                        applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this@MatchLocationMapActivity,
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
                        moveCamera(lat.toDouble(), lng.toDouble())

                    }

                    // 위치정보를 원하는 시간, 거리마다 갱신해준다.
                    lm.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 1000, 1f
                    ) { location ->
                        lng = location.longitude.toString()
                        lat = location.latitude.toString()
                    }
                }
            }

            btnMatchLocationBack.setOnClickListener {
                finish()
            }
        }

    }

    private fun moveCamera(lat: Double, lng: Double) {
        // 카메라 현재위치로 이동
        val cameraUpdate = CameraUpdate.scrollTo(
            LatLng(
                lat,
                lng
            )
        ).animate(CameraAnimation.Easing, 1000)
        naverMap.moveCamera(cameraUpdate)
    }

    private fun initObserver() {
        // 리사이클러뷰에서 선택한 동네
        viewModel.selectedLocation.observe(this) {
            moveCamera(it.latitude.toDouble(), it.longitude.toDouble())
        }

        // 상단에 표시되는 주소가 변경되면 동작
        viewModel.topAddress.observe(this) {
            // 좌표 -> 주소 변환 텍스트 세팅, 버튼 활성화
            binding.tvMatchLocationAddress.run {
                text = it
                setTextColor(getColor(R.color.white))
            }
            binding.btnMatchLocationComplete.run {
                setTextColor(getColor(R.color.primary))
                isEnabled = true
            }
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

    // 좌표 -> 주소 변환
    private fun getAddress(lat: Double, lng: Double) {
        viewModel.reverseGeocoding(lat.toString(), lng.toString())
    }

    override fun onBackPressed() {
        if (addressSearchBottomFragment?.handleBackKeyEvent() == true) {
            // no-op
        } else {
            super.onBackPressed()
        }

    }
    fun Int.dpToPx(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }
}