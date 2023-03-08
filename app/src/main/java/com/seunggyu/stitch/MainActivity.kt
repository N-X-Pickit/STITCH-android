package com.seunggyu.stitch

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.seunggyu.stitch.Util.SnackBarCustom
import com.seunggyu.stitch.databinding.ActivityMainBinding
import com.seunggyu.stitch.ui.fragment.category.CategoryFragment
import com.seunggyu.stitch.ui.fragment.home.HomeFragment
import com.seunggyu.stitch.ui.fragment.mymatch.MymatchFragment
import com.seunggyu.stitch.ui.fragment.mymenu.MymenuFragment
import com.seunggyu.stitch.viewModel.MainViewModel

class MainActivity : BasicActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()
    }

    fun init() {
        val originDecorView = window.decorView.systemUiVisibility
        with(binding) {
            navigationView.run {
                setOnItemSelectedListener {
                    when (it.itemId) {
                        R.id.action_home -> {
                            window.apply {
                                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                                statusBarColor = Color.TRANSPARENT
                            }
                            supportFragmentManager
                                .beginTransaction()
                                .replace(binding.frameLayout.id, HomeFragment())
                                .commitAllowingStateLoss()
                            Log.e("NaviFrag", "actionHome")
                        }
                        R.id.action_category -> {
                            window.apply {
                                decorView.systemUiVisibility = originDecorView
                                clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                            }
                            supportFragmentManager
                                .beginTransaction()
                                .replace(binding.frameLayout.id, CategoryFragment())
                                .commitAllowingStateLoss()
                            Log.e("NaviFrag", "actionCategory")
                        }
                        R.id.action_mymatch -> {
                            window.apply {
                                decorView.systemUiVisibility = originDecorView
                                clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                            }
                            supportFragmentManager
                                .beginTransaction()
                                .replace(binding.frameLayout.id, MymatchFragment())
                                .commitAllowingStateLoss()
                            Log.e("NaviFrag", "actionMymatch")
                        }
                        R.id.action_mymenu -> {
                            window.apply {
                                decorView.systemUiVisibility = originDecorView
                                clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                            }
                            supportFragmentManager
                                .beginTransaction()
                                .replace(binding.frameLayout.id, MymenuFragment())
                                .commitAllowingStateLoss()
                            Log.e("NaviFrag", "actionMymenu")
                        }
                    }
                    true
                }
                selectedItemId = R.id.action_home
            }
        }
    }
}