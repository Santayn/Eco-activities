import com.example.eco.api.dto.bonus.BonusHistoryDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BonusApi {
    @GET("/api/user-bonus-history/user/{userId}")
    fun getBonusHistory(@Path("userId") userId: Int): Call<List<BonusHistoryDto>>
}