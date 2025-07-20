
import com.example.eco.api.dto.bonus.UserBonusHistoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface BonusApi {
    @GET("/api/user-bonus-history/search")
    fun searchUserBonusHistory(@QueryMap filters: Map<String, String>): Call<UserBonusHistoryResponse?>
}