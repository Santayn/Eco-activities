package com.example.eco.api

import com.example.eco.model.EventFilterDTO
import com.example.eco.model.EventFilterForUserDTO
import com.example.eco.model.EventRequestDTO
import com.example.eco.model.EventResponseMediumDTO
import com.example.eco.model.UserRegistrationRequestDto
import com.example.eco.model.UserRegistrationResponseDto
import retrofit2.http.*

interface ApiService {

    // 🔹 Events endpoints
    @GET("events/{id}")
    suspend fun getEventById(@Path("id") id: Int): EventResponseMediumDTO

    @POST("events")
    suspend fun createEvent(@Body event: EventRequestDTO): EventResponseMediumDTO

    @PUT("events/{id}")
    suspend fun updateEvent(@Path("id") id: Int, @Body event: EventRequestDTO): EventResponseMediumDTO

    @DELETE("events/{id}")
    suspend fun deleteEvent(@Path("id") id: Int)

    @PUT("events/{id}/conducted")
    suspend fun updateConducted(@Path("id") id: Int, @Query("conducted") conducted: Boolean)

    @POST("events/search")
    suspend fun searchEvents(@Body filter: EventFilterDTO): List<EventResponseMediumDTO>

    @POST("events/user/search")
    suspend fun searchEventsByUser(@Body filter: EventFilterForUserDTO): List<EventResponseMediumDTO>

    // 🔹 Users endpoints
    @GET("users")
    suspend fun getAllUsers(): List<UserRegistrationResponseDto>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserRegistrationResponseDto

    @POST("users/register")
    suspend fun registerUser(@Body user: UserRegistrationRequestDto): UserRegistrationResponseDto

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: UserRegistrationRequestDto): UserRegistrationResponseDto

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int)
}