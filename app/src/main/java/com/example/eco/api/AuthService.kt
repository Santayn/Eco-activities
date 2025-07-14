package com.example.eco.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Интерфейс для методов API авторизации
interface AuthService {
    @POST("/api/auth/login")
    fun login(@Body credentials: LoginRequest): Call<LoginResponse>
}