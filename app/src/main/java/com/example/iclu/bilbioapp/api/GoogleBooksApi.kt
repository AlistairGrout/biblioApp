package com.example.iclu.bilbioapp.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GoogleBooksApi {
    private val googleBooksApiService: GoogleBooksApiService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/books/v1")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        googleBooksApiService = retrofit.create(GoogleBooksApiService::class.java)
    }

    fun getBooks(request: String): Call<GoogleBooksResult> {
        return googleBooksApiService.getBooks(request)
    }
}