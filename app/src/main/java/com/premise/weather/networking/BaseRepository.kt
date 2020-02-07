package com.premise.weather.networking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.premise.weather.livedata.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseRepository<API_TYPE, API_PARAM, RESPONSE_TYPE>(private val serviceClass: Class<API_TYPE>) {

    private val _data: MutableLiveData<Result<RESPONSE_TYPE>> = MutableLiveData()
    val data: LiveData<Result<RESPONSE_TYPE>> = _data

    fun fetch(param: API_PARAM) {
        _data.value = Result.loading()
        apiCall(RetrofitService.createService(serviceClass), param).enqueue(object : Callback<RESPONSE_TYPE> {
            override fun onResponse(call: Call<RESPONSE_TYPE>, response: Response<RESPONSE_TYPE>) {
                _data.value = if (response.isSuccessful) Result.success(response.body()) else Result.error()

            }

            override fun onFailure(call: Call<RESPONSE_TYPE>, t: Throwable) {
                _data.value = Result.error()
            }
        })
    }

    abstract fun apiCall(api: API_TYPE, param: API_PARAM): Call<RESPONSE_TYPE>
}