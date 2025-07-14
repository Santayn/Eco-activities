package com.example.eco.api.dto.service

import com.example.eco.api.dto.auth.AuthRequestDto
import com.example.eco.api.dto.auth.AuthResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Интерфейс для методов API авторизации
interface AuthService {
    @POST("/api/auth/login")
    fun login(@Body credentials: AuthRequestDto): Call<AuthResponseDto>
}