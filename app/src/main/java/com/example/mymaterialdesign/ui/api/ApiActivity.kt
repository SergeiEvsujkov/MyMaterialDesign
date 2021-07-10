package com.example.mymaterialdesign.ui.api

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import com.example.mymaterialdesign.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_api.*

class ApiActivity : AppCompatActivity() {
    private val icons =
        arrayOf(R.layout.fragment_earth, R.layout.fragment_mars, R.layout.fragment_weather)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)
        view_pager.adapter = ViewPagerAdapter()
        TabLayoutMediator(
            tab_layout, view_pager
        ) { tab, position ->

        }.attach()
        view_pager.setCurrentItem(0, true)
        tab_layout.getTabAt(0)?.setIcon(R.drawable.ic_earth)
        tab_layout.getTabAt(1)?.setIcon(R.drawable.ic_mars)
        tab_layout.getTabAt(2)?.setIcon(R.drawable.ic_system)


    }



}