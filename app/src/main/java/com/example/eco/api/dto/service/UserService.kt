package com.example.eco.api.dto.service

import com.example.eco.api.dto.user.UserCollapseDTO
import com.example.eco.api.dto.user.UserMediumDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("/api/users/{userId}")
    fun getUserProfile(@Path("userId") userId: Int): Call<UserCollapseDTO>
}