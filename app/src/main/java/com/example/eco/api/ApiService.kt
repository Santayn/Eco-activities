package com.example.eco.api

import com.example.eco.api.dto.event.Event
import com.example.eco.api.dto.event.EventTypeDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/events")
    suspend fun getAllEvents(): Response<List<Event>>

    @GET("api/event-types")
    suspend fun getAllEventTypes(): Response<List<EventTypeDTO>>

    @GET("api/event-types/{id}")
    suspend fun getEventTypeById(@Path("id") id: Int): Response<EventTypeDTO>
}