package com.example.mymaterialdesign.ui.main

import android.annotation.SuppressLint

import android.os.Build

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mymaterialdesign.BuildConfig
import com.example.mymaterialdesign.ui.picture.PODRetrofitImpl
import com.example.mymaterialdesign.ui.picture.PODServerResponseData
import com.example.mymaterialdesign.ui.picture.PictureOfTheDayData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) :
    ViewModel() {

    fun getData(): LiveData<PictureOfTheDayData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    var isYesterdayBefore: Boolean = false
    var isYesterday: Boolean = false
    var isToday: Boolean = true


    @SuppressLint("SimpleDateFormat")
    private fun sendServerRequest() {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY

        val date: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var current = LocalDateTime.now()

            if (isYesterday) {
                current = current.minusDays(1)
            } else if (isYesterdayBefore) {
                current = current.minusDays(2)
            }
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            current.format(formatter)

        } else {
            val dateNow = Date()
            if (isYesterday) {
                dateNow.time = dateNow.time - 86400000
            }
            if (isYesterdayBefore) {
                dateNow.time = dateNow.time - 172800000
            }
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            formatter.format(dateNow)
        }

// Немного запарился с датой, вывел в лог :))
        Log.d("dateSend", date)

        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey, date).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            PictureOfTheDayData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                }
            })
        }
    }
}