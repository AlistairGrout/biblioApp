package com.example.iclu.bilbioapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApiService {
    @GET("/volumes/")
    fun getBooks(
            @Query("q") request: String,
            @Query("key") key: String = ""
    ): Call<GoogleBooksResult>
}