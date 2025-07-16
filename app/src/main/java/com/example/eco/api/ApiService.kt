import android.util.Log
import com.example.eco.api.dto.event.EventRequestDTO
import com.example.eco.api.dto.event.EventResponseMediumDTO
import com.example.eco.api.dto.event.PaginatedResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("api/events/")
    suspend fun getAllEvents(): Response<List<EventResponseMediumDTO>>
    // Получить событие по ID
    @GET("api/events/{eventId}")
    suspend fun getEventById(
        @Path("eventId") eventId: Int
    ): Response<EventResponseMediumDTO>

    // Обновить статус "проведено" (обновляет только поле conducted)
    @POST("api/events/{id}/conduct")
    suspend fun updateConductStatus(
        @Path("id") id: Int,
        @Query("conducted") conducted: Boolean
    ): Response<Void>

    // Обновить событие полностью по ID
    @POST("api/events/{eventID}")
    suspend fun updateEvent(
        @Path("eventID") eventID: Int,
        @Body event: EventRequestDTO
    ): Response<EventResponseMediumDTO>

    // Создать событие с превью (multipart/form-data)
    @Multipart
    @POST("api/events")
    suspend fun createEvent(
        @Part("event") event: RequestBody, // JSON представление события в виде строки
        @Part preview: MultipartBody.Part? = null // изображение превью, можно опустить (null)
    ): Response<EventResponseMediumDTO>

    // Удалить событие
    @DELETE("api/events/{eventId}")
    suspend fun deleteEvent(
        @Path("eventId") eventId: Int
    ): Response<Void>

    // Пример: поиск событий по фильтрам (если требуется)
    @GET("api/events/search")
    suspend fun searchEvents(
        @Query("keyword") keyword: String? = null,
        @Query("eventTypeId") eventTypeId: Int? = null,
        @Query("startDateFrom") startDateFrom: String? = null,
        @Query("startDateTo") startDateTo: String? = null,
        @Query("sortBy") sortBy: String = "startTime",
        @Query("sortOrder") sortOrder: String = "DESC",
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Response<PaginatedResponse<EventResponseMediumDTO>>

    // Поиск событий, в которых участвует конкретный пользователь
    @GET("api/events/user/search")
    suspend fun searchUserEvents(
        @Query("userIdForEventFilter") userIdForEventFilter: Int,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10,
        @Query("filter") filter: String? = null // или заменить на более конкретные параметры
    ): Response<PaginatedResponse<EventResponseMediumDTO>>
}