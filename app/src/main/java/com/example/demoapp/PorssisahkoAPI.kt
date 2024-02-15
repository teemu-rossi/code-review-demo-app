package com.example.demoapp

import retrofit2.http.GET
import retrofit2.http.Query

data class HourPriceResponse(
    val price: Double
)

interface PorssisahkoAPI {
    @GET("/v1/price.json")
    suspend fun getHourPrice(
        @Query("date")
        date: String,
        @Query("hour")
        hour: Int,
    ): HourPriceResponse
}
