package com.example.mymaterialdesign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mymaterialdesign.ui.main.Data
import com.example.mymaterialdesign.ui.main.RecyclerActivityAdapter
import kotlinx.android.synthetic.main.activity_recycler.*

class RecyclerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        val data = arrayListOf(
            Pair(Data("Овен", "21 марта –\n20 апреля", R.drawable.ram), false),
            Pair(Data("Телец", "21 апреля –\n20 мая", R.drawable.bull), false),
            Pair(Data("Близнецы", "22 мая –\n21 июня", R.drawable.twins), false),
            Pair(Data("Рак", "22 июня –\n22 июля", R.drawable.crab), false),
            Pair(Data("Лев", "23 июля –\n21 августа", R.drawable.lion), false),
            Pair(Data("Дева", "22 августа –\n23 сентября", R.drawable.maiden), false),
            Pair(Data("Весы", "24 сентября –\n23 октября", R.drawable.scales), false),
            Pair(Data("Скорпион", "24 октября –\n22 ноября", R.drawable.scorpion), false),
            Pair(Data("Стрелец", "23 ноября –\n22 декабря", R.drawable.archer), false),
            Pair(Data("Козерог", "23 декабря –\n20 января", R.drawable.goat), false),
            Pair(Data("Водолей", "21 января –\n19 февраля", R.drawable.water), false),
            Pair(Data("Рыбы", "20 февраля –\n20 марта", R.drawable.fishes), false)
        )

        data.add(0, Pair(Data("Header", null, null), false))

        val adapter = RecyclerActivityAdapter(
            object : RecyclerActivityAdapter.OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(this@RecyclerActivity, data.someText, Toast.LENGTH_SHORT).show()
                }
            },
            data,
            this
        )

        recyclerView.adapter = adapter
        recyclerActivityFAB.setOnClickListener { adapter.appendItem() }

    }
}