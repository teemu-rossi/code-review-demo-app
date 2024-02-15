@file:OptIn(DelicateCoroutinesApi::class)

package com.example.demoapp

import androidx.lifecycle.ViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DetailViewModel : ViewModel() {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.porssisahko.net/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val formattedTime = MutableStateFlow("")
    private val price = MutableStateFlow<Double?>(null)

    val priceText: Flow<String>
        get() = price.map { "${formattedTime.value}\n\n$it c/kWh" }

    fun load(date: LocalDate, hour: Int) {
        formattedTime.value = "Sähkön hinta $date klo $hour:00"
        GlobalScope.launch {
            price.value = retrofit.create(PorssisahkoAPI::class.java)
                .getHourPrice(date.format(DateTimeFormatter.ISO_DATE), hour).price
        }
    }
}
