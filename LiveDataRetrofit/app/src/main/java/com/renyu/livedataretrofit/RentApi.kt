package com.renyu.livedataretrofit

import androidx.lifecycle.LiveData
import retrofit2.http.GET

interface RentApi {
    @GET("zsbapi/broker/get-city-list")
    fun getCityList(): LiveData<RentResponseList<CityResponse>>
}