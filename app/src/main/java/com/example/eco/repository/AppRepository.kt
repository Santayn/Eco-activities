package com.example.eco.repository

import com.example.eco.api.ApiService
import com.example.eco.model.EventFilterDTO
import com.example.eco.model.EventFilterForUserDTO
import com.example.eco.model.EventRequestDTO
import com.example.eco.model.UserRegistrationRequestDto

class AppRepository(private val api: ApiService) {

    suspend fun fetchEventById(id: Int) = api.getEventById(id)
    suspend fun createEvent(event: EventRequestDTO) = api.createEvent(event)
    suspend fun updateEvent(id: Int, event: EventRequestDTO) = api.updateEvent(id, event)
    suspend fun deleteEvent(id: Int) = api.deleteEvent(id)
    suspend fun updateConducted(id: Int, conducted: Boolean) = api.updateConducted(id, conducted)
    suspend fun searchEvents(filter: EventFilterDTO) = api.searchEvents(filter)
    suspend fun searchEventsByUser(filter: EventFilterForUserDTO) = api.searchEventsByUser(filter)

    suspend fun getAllUsers() = api.getAllUsers()
    suspend fun getUserById(id: Int) = api.getUserById(id)
    suspend fun registerUser(user: UserRegistrationRequestDto) = api.registerUser(user)
    suspend fun updateUser(id: Int, user: UserRegistrationRequestDto) = api.updateUser(id, user)
    suspend fun deleteUser(id: Int) = api.deleteUser(id)
}