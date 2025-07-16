package com.example.eco

import com.example.eco.api.ApiService
import com.example.eco.api.dto.event.Event
import javax.inject.Inject

class EventRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getEvents(): List<Event> {
        return apiService.getAllEvents().body() ?: emptyList()
    }
}