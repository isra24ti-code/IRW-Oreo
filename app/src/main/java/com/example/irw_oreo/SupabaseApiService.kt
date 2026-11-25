package com.example.irw_oreo

import retrofit2.http.*

interface SupabaseApiService {
    // --- 1. REGISTER USER ---
    @POST("users")
    suspend fun registerUser(@Body user: UserModel): List<UserModel>

    // --- 2. CRUD LAYANAN ---
    @GET("layanan")
    suspend fun getAllLayanan(@Query("select") select: String = "*"): List<LayananModel>

    @POST("layanan")
    suspend fun createLayanan(@Body data: LayananModel): List<LayananModel>

    @PATCH("layanan")
    suspend fun updateLayanan(
        @Query("id") idFilter: String,
        @Body data: LayananModel
    ): List<LayananModel>

    @DELETE("layanan")
    suspend fun deleteLayanan(@Query("id") idFilter: String)

    // --- 3. CRUD PESANAN ---
    @GET("pesanan")
    suspend fun getAllPesanan(@Query("select") select: String = "*"): List<PesananModel>

    @POST("pesanan")
    suspend fun createPesanan(@Body data: PesananModel): List<PesananModel>

    @PATCH("pesanan")
    suspend fun updatePesanan(
        @Query("id") idFilter: String,
        @Body data: PesananModel
    ): List<PesananModel>

    @DELETE("pesanan")
    suspend fun deletePesanan(@Query("id") idFilter: String)
}