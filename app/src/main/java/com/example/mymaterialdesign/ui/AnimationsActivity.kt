package com.example.mymaterialdesign.ui

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*

import com.example.mymaterialdesign.R
import kotlinx.android.synthetic.main.activity_animations.*
import kotlinx.android.synthetic.main.activity_animations_explode.*

class AnimationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animations_explode)
        recycler_view.adapter = Adapter()
    }

    private fun explode(clickedView: View) {
        val viewRect = Rect()
        clickedView.getGlobalVisibleRect(viewRect)
        val explode = Explode()
        explode.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }
        explode.excludeTarget(clickedView, true)
        val set = TransitionSet()
            .addTransition(explode)
            .addTransition(Fade().addTarget(clickedView))
            .addListener(object : TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    transition.removeListener(this)
                    onBackPressed()
                }
            })
        TransitionManager.beginDelayedTransition(recycler_view, set)
        recycler_view.adapter = null
    }

    inner class Adapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.activity_animations_explode_recycle_view_item,
                    parent,
                    false
                ) as View
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                explode(it)
            }
        }

        override fun getItemCount(): Int {
            return 32
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}