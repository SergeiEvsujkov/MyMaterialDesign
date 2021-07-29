package com.example.mymaterialdesign.ui.main

import android.annotation.SuppressLint
import android.app.PendingIntent.getActivity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.mymaterialdesign.MainActivity
import com.example.mymaterialdesign.R
import com.example.mymaterialdesign.RecyclerActivity

import kotlinx.android.synthetic.main.activity_animations.*
import kotlinx.android.synthetic.main.activity_recycler_item.view.*
import kotlin.coroutines.coroutineContext

class RecyclerActivityAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: MutableList<Pair<Data, Boolean>>,
    private var context: Context
) :
    RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_ZODIAC -> RecyclerViewHolder(
                inflater.inflate(R.layout.activity_recycler_item, parent, false) as View
            )
            else -> HeaderViewHolder(
                inflater.inflate(R.layout.header_recycler, parent, false) as View
            )
        }


    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_ZODIAC
        }
    }

    @SuppressLint("ShowToast")
    fun appendItem() {
        if (data.size < 13) {
            data.add(generateItem())
            notifyItemInserted(itemCount - 1)
        } else {
            Toast.makeText(context, "Знаков зодиака больше нет!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateItem(): Pair<Data, Boolean> {
        var count = 0

        for (i in 0 until data.size) {
            if (data[i].first.someText == "Овен") {
                count = 1
            }

        }
        if (count == 0) {
            return Pair(Data("Овен", "21 марта –\n20 апреля", R.drawable.ram), false)
        }

        count = 0

        for (i in 0 until data.size) {
            if (data[i].first.someText == "Телец") {
                count = 1
            }

        }
        if (count == 0) {
            return Pair(Data("Телец", "21 апреля –\n20 мая", R.drawable.bull), false)
        }
        count = 0

        for (i in 0 until data.size) {
            if (data[i].first.someText == "Близнецы") {
                count = 1
            }

        }
        if (count == 0) {
            return Pair(Data("Близнецы", "22 мая –\n21 июня", R.drawable.twins), false)
        }
        count = 0

        for (i in 1 until data.size) {
            if (data[i].first.someText == "Рак") {
                count = 1
            }

        }
        if (count == 0) {
            return Pair(Data("Рак", "22 июня –\n22 июля", R.drawable.crab), false)
        }
        count = 0

        for (i in 1 until data.size) {
            if (data[i].first.someText == "Лев") {
                count = 1
            }

        }
        if (count == 0) {
            return Pair(Data("Лев", "23 июля –\n21 августа", R.drawable.lion), false)
        }
        count = 0

        for (i in 1 until data.size) {
            if (data[i].first.someText == "Дева") {
                count = 1
            }

        }
        if (count == 0) {
            return Pair(Data("Дева", "22 августа –\n23 сентября", R.drawable.maiden), false)
        }
        count = 0

        for (i in 1 until data.size) {
            if (data[i].first.someText == "Весы") {
                count = 1
            }

        }
        if (count == 0) {
            return Pair(Data("Весы", "24 сентября –\n23 октября", R.drawable.scales), false)
        }
        count = 0

        for (i in 1 until data.size) {
            if (data[i].first.someText == "Скорпион") {
                count = 1
            }

        }
        if (count == 0) {
            return Pair(Data("Скорпион", "24 октября –\n22 ноября", R.drawable.scorpion), false)
        }
        count = 0

        for (i in 1 until data.size) {
            if (data[i].first.someText == "Стрелец") {
                count = 1
            }

        }
        if (count == 0) {
            return Pair(Data("Стрелец", "23 ноября –\n22 декабря", R.drawable.archer), false)
        }
        count = 0

        for (i in 1 until data.size) {
            if (data[i].first.someText == "Козерог") {
                count = 1
            }

        }
        if (count == 0) {
            return Pair(Data("Козерог", "23 декабря –\n20 января", R.drawable.goat), false)
        }
        count = 0

        for (i in 1 until data.size) {
            if (data[i].first.someText == "Водолей") {
                count = 1
            }

        }
        if (count == 0) {
            return Pair(Data("Водолей", "21 января –\n19 февраля", R.drawable.water), false)
        }

        return Pair(Data("Рыбы", "20 февраля –\n20 марта", R.drawable.fishes), false)

    }


    inner class RecyclerViewHolder(view: View) : BaseViewHolder(view) {

        override fun bind(data: Pair<Data, Boolean>) {

            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.nameRecycler.text = data.first.someText
                itemView.descriptionTextView.text = data.first.someDescription
                data.first.iconRecycler?.let { itemView.recyclerImageView.setImageResource(it) }
                itemView.wikiImageView.setOnClickListener { onListItemClickListener.onItemClick(data.first) }

                itemView.addItemImageView.setOnClickListener { addItem() }

                itemView.removeItemImageView.setOnClickListener { removeItem() }

                itemView.moveItemDown.setOnClickListener { moveDown() }
                itemView.moveItemUp.setOnClickListener { moveUp() }

                itemView.descriptionTextView.visibility =
                    if (data.second) View.VISIBLE else View.GONE
                itemView.nameRecycler.setOnClickListener { toggleText() }
            }
        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun moveUp() {
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown() {
            layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                data.removeAt(currentPosition).apply {
                    data.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        @SuppressLint("ShowToast", "RestrictedApi")
        private fun addItem() {
            if (data.size < 13) {
                data.add(layoutPosition, generateItem())
                notifyItemInserted(layoutPosition)
            } else {
                Toast.makeText(context, "Знаков зодиака больше нет!", Toast.LENGTH_SHORT).show()
            }
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {

        override fun bind(data: Pair<Data, Boolean>) {
            itemView.setOnClickListener { onListItemClickListener.onItemClick(data.first) }
        }
    }


    interface OnListItemClickListener {
        fun onItemClick(data: Data)
    }

    companion object {
        private const val TYPE_ZODIAC = 0
        private const val TYPE_HEADER = 1
    }


}

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: Pair<Data, Boolean>)
}