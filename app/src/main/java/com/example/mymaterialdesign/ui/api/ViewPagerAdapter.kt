package com.example.mymaterialdesign.ui.api

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mymaterialdesign.R
import kotlinx.android.synthetic.main.item_page.view.container
import kotlinx.android.synthetic.main.item_page.view.*
import kotlinx.android.synthetic.main.main_activity.view.*


private const val EARTH_FRAGMENT = 0
private const val MARS_FRAGMENT = 1
private const val WEATHER_FRAGMENT = 2

class ViewPagerAdapter : RecyclerView.Adapter<PagerVH>() {


    private val fragments =
        arrayOf(R.layout.fragment_earth, R.layout.fragment_mars, R.layout.fragment_weather)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false))

    override fun getItemCount(): Int = fragments.size

    override fun onBindViewHolder(holder: PagerVH, position: Int): Unit = holder.itemView.run {

        LayoutInflater.from(context).inflate(fragments[position], container, true)
    }

}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)


