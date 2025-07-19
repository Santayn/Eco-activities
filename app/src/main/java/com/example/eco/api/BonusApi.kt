
import com.example.eco.api.dto.bonus.BonusHistoryDto
import com.example.eco.api.dto.bonus.BonusTypeDTO
import com.example.eco.api.dto.bonus.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface BonusApi {
    @GET("/api/user-bonus-history/user/{id}")
    fun getBonusHistory(@Path("id") userId: Int): Call<List<BonusHistoryDto>>

    @GET("/api/bonus-types/search")
    fun searchBonusTypes(
        @Query("name") name: String?,
        @Query("description") description: String?,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @QueryMap(encoded = true) filter: Map<String, String>
    ): Call<SearchResult<BonusTypeDTO>>
}