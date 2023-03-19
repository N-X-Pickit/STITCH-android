package com.seunggyu.stitch.ui.fragment.newmatch

import MatchLocationMapViewModel
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.seunggyu.stitch.R
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.adapter.GpsResultRecyclerViewAdapter
import com.seunggyu.stitch.adapter.LocationResultRecyclerViewAdapter
import com.seunggyu.stitch.databinding.DialogBottomsheetMatchDetailLocationCollapseBinding
import com.seunggyu.stitch.databinding.DialogBottomsheetMatchDetailLocationExpandBinding
import kr.co.prnd.persistbottomsheetfragment.PersistBottomSheetFragment
import kotlin.math.exp


class AddressSearchBottomFragment :
    PersistBottomSheetFragment<DialogBottomsheetMatchDetailLocationCollapseBinding,
            DialogBottomsheetMatchDetailLocationExpandBinding>(
        R.layout.dialog_bottomsheet_match_detail_location_collapse,
        R.layout.dialog_bottomsheet_match_detail_location_expand
    ) {
    private val viewModel: MatchLocationMapViewModel by activityViewModels()

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = kotlinx.coroutines.Runnable {
        viewModel.geocoding()
    }
    private val runnableLocation = kotlinx.coroutines.Runnable {
        viewModel.locationSearch()
    }
    private val gpsRecyclerViewAdapter: GpsResultRecyclerViewAdapter by lazy {
        GpsResultRecyclerViewAdapter(expandBinding.rvGpsResult){viewModel.setSelectedAddress(it)}
    }
    private val locationResultRecyclerViewAdapter: LocationResultRecyclerViewAdapter by lazy {
        LocationResultRecyclerViewAdapter(expandBinding.rvGpsResult){
            viewModel.setSelectedLocation(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObserver()

        collapseBinding.etAddressSearch.setOnClickListener {
            expand()
            expandBinding.etAddressSearch.isFocusable = true
            showKeyboard()
            expandBinding.etAddressSearch.requestFocus()
        }

        expandBinding.root.setOnClickListener {
            hideKeyBoard()
        }

        expandBinding.ivMatchAddressClear.setOnClickListener {
            expandBinding.etAddressSearch.setText("")
            collapseBinding.etAddressSearch.text = ""
        }

        expandBinding.etAddressSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val afterText = s.toString()

                if (afterText.isNotEmpty()) {
                    expandBinding.ivMatchAddressClear.isVisible = true

                    if (afterText.length > 1){
                        viewModel.inputAddress = afterText
                        if((afterText.last() == '동' || afterText.last() == '시' ||
                                afterText.last() == '읍' || afterText.last() == '면')) {
                            handler.removeCallbacks(runnable)
                            handler.postDelayed(runnable, 500)

                            Log.e("onTextChanged", afterText)
                        }
                        else {
                            handler.removeCallbacks(runnableLocation)
                            handler.postDelayed(runnableLocation, 500)

                            Log.e("onTextChanged::Location", afterText)
                        }
                    }
                } else {
                    expandBinding.ivMatchAddressClear.visibility = View.INVISIBLE

                    expandBinding.clAddressResult.visibility = View.INVISIBLE
                    expandBinding.clAddressNull.visibility = View.INVISIBLE
                }
            }
        })

    }

    private fun initObserver() {

        // 선택한 주소 반영
        viewModel.selectedAddress.observe(requireActivity()) {
            Log.e("selectedAddress",it.toString())
            collapseBinding.etAddressSearch.text = it.toString()
            collapse()
        }

        // 주소 조회 리스트
        viewModel.addressList.observe(requireActivity()) {
            it?.let {
                if (it.isNotEmpty()) {
                    Log.e("isDetailAddress", "true")
                    gpsRecyclerViewAdapter.submitList(it)
                    Log.e("it.size", it.size.toString())
                    expandBinding.clAddressResult.isVisible = true
                    expandBinding.clAddressNull.visibility = View.INVISIBLE
                    expandBinding.tvAddressResult.text = getString(
                        R.string.address_result_name,
                        expandBinding.etAddressSearch.text
                    )
                    expandBinding.rvGpsResult.isVisible = true
                    expandBinding.rvLocationResult.visibility = View.INVISIBLE
                } else {
                    Log.e("clAddressResult", "#1")
                    expandBinding.clAddressResult.visibility = View.INVISIBLE
                    expandBinding.clAddressNull.isVisible = true
                }
            } ?: run {
                SnackBarCustom.make(
                    expandBinding.clAddressParent,
                    getString(R.string.snackbar_network_error)
                ).show()
            }
        }

        // 장소 검색 결과 리스트
        viewModel.locationList.observe(requireActivity()) {
            it?.let {
                if (it.isNotEmpty()) {
                    locationResultRecyclerViewAdapter.submitList(it)
                    Log.e("it.size", it.size.toString())
                    expandBinding.clAddressResult.isVisible = true
                    expandBinding.clAddressNull.visibility = View.INVISIBLE
                    expandBinding.tvAddressResult.text = getString(
                        R.string.address_result_name,
                        expandBinding.etAddressSearch.text
                    )
                    expandBinding.rvGpsResult.visibility = View.INVISIBLE
                    expandBinding.rvLocationResult.isVisible = true
                } else {
                    Log.e("clAddressResult", "#1")
                    expandBinding.clAddressResult.visibility = View.INVISIBLE
                    expandBinding.clAddressNull.isVisible = true
                }
            } ?: run {
                SnackBarCustom.make(
                    expandBinding.clAddressParent,
                    getString(R.string.snackbar_network_error)
                ).show()
            }
        }
    }

    private fun initRecyclerView() {
        gpsRecyclerViewAdapter
        expandBinding.rvGpsResult.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = gpsRecyclerViewAdapter
            Log.e("RecyclerView init", "successed")
        }
        expandBinding.rvLocationResult.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = locationResultRecyclerViewAdapter
            Log.e("locationResultRecyclerView init", "successed")
        }
    }

    private fun showKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
    private fun hideKeyBoard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }
    companion object {

        private val TAG = AddressSearchBottomFragment::class.simpleName

        fun show(
            fragmentManager: FragmentManager,
            @IdRes containerViewId: Int,
        ): AddressSearchBottomFragment =
            fragmentManager.findFragmentByTag(TAG) as? AddressSearchBottomFragment
                ?: AddressSearchBottomFragment().apply {
                    fragmentManager.beginTransaction()
                        .replace(containerViewId, this, TAG)
                        .commitAllowingStateLoss()
                }
    }

}