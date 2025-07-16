// File: EventRepository.kt

import android.util.Log
import com.example.eco.api.dto.event.EventResponseMediumDTO
import com.example.eco.api.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventRepository(private val apiService: ApiService) {

    suspend fun getAllEvents(): List<EventResponseMediumDTO> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getAllEvents()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("EventRepository", "Ошибка ответа: ${response.code()} - ${response.errorBody()?.string()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("EventRepository", "Ошибка сети: ${e.message}", e)
            emptyList()
        }
    }
}