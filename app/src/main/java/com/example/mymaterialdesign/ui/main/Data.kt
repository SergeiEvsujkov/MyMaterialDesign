package com.example.mymaterialdesign.ui.main

data class Data(val someText: String = "Text", val someDescription: String? = "Description", val iconRecycler: Int?)

interface OnListItemClickListener {
    fun onItemClick(data: Data)
}