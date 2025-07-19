package com.example.eco.api.service

import com.example.eco.api.dto.user.UserCollapseDTO
import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    @GET("/api/users/me")
    fun getCurrentUserProfile(): Call<UserCollapseDTO>
}