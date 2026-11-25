package com.example.irw_oreo

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SupabaseClient {
    private const val BASE_URL = "https://tgcynnxfuufptnpypdme.supabase.co/rest/v1/"
    private const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRnY3lubnhmdXVmcHRucHlwZG1lIiwicm9sZSI6ImFub24iLCJpYXQiOjE3OTU2MDA4MTQsImV4cCI6MjExMTE3NjgxNH0.BOp1VoqgIfVQ5bLvGfCNzzVjgvJ_DnqSxM7hAEqjIhA"

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("apikey", API_KEY)
            .addHeader("Authorization", "Bearer $API_KEY")
            .addHeader("Content-Type", "application/json")
            .addHeader("Prefer", "return=representation")
            .build()
        chain.proceed(request)
    }.build()

    val apiService: SupabaseApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SupabaseApiService::class.java)
    }
}