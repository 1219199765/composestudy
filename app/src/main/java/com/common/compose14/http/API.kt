import com.common.compose14.bean.CategoryBean
import com.common.compose14.bean.CategoryContentBean
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface API {
    @GET("home")
    suspend fun requestHome(
//        @QueryMap params: Map<String, String>
    ): HomeBean


    @GET("dj-mini/categories")
    suspend fun requestCategory(
        @QueryMap params: Map<String, String>
    ): CategoryBean


    @GET("dj-mini/goods")
    suspend fun requestCategoryContent(
        @QueryMap params: Map<String, String>
    ): CategoryContentBean

}