package com.example.eco.api.dto.service

import com.example.eco.api.dto.registration.UserRegistrationRequestDto
import com.example.eco.api.dto.registration.UserRegistrationResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationService {
    @POST("/api/users/registration")
    fun registerUser(@Body user: UserRegistrationRequestDto): Call<UserRegistrationResponseDto>
}