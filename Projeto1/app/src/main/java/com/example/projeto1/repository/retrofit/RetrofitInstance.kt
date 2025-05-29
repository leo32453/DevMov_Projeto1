package com.example.projeto1.repository.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private
    const val BASE_URL = "http://10.0.2.2:3000/"
    const val TO_TEST_BASE_URL = "http://localhost:3000/"

    val api : LoginApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(LoginApiInterface::class.java)
    }

    val testapi : LoginApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(TO_TEST_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(LoginApiInterface::class.java)
    }

    val ExchangeApi: ExchangeApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ExchangeApiInterface::class.java)
    }

    val TestExchangeApi : ExchangeApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(TO_TEST_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ExchangeApiInterface::class.java)
    }
}