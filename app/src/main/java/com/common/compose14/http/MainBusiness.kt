


import android.util.Log
import com.common.compose14.bean.CategoryBean
import com.common.compose14.bean.CategoryContentBean
import com.common.compose14.bean.HomeBean
import com.common.compose14.view.category.CategoryState
import com.common.compose14.view.home.HomeState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

object MainBusiness {
    /**
     *
     * */
    suspend fun requestHome(
        request: suspend (API) -> HomeBean?
    ): Flow<HomeState> {
        return flow<HomeState> {
            val result = request(MainService) ?: throw IllegalArgumentException("数据非法，获取响应数据为空")
            LogUtils.d("结果===$result")
            emit(HomeState.RequestHomeSus(result))
        }.onStart {
            //在开始流之前调用的  例如开启loading
//            emit(MainFragmentUiState.IsLoading)
        }.catch {
            //请求出现错误，例如无网络，地址出错，返回异常
//            emit(BaseResult.Failure(it))
            Log.d("main","444$it")
            emit(HomeState.RequestHomeErr(it as Exception))
        }.flowOn(Dispatchers.IO)
            .onCompletion {
                //在结束流之前调用的  例如关闭loading
            }
    }

    suspend fun requestCategory(
        request: suspend (API) -> CategoryBean?
    ): Flow<CategoryState> {
        return flow<CategoryState> {
            val result = request(MainService) ?: throw IllegalArgumentException("数据非法，获取响应数据为空")
            LogUtils.d("结果2===$result")
            emit(CategoryState.RequestCategorySus(result))
        }.onStart {
            //在开始流之前调用的  例如开启loading
//            emit(MainFragmentUiState.IsLoading)
        }.catch {
            //请求出现错误，例如无网络，地址出错，返回异常
//            emit(BaseResult.Failure(it))
            Log.d("main","444$it")
            emit(CategoryState.RequestCategoryErr(it as Exception))
        }.flowOn(Dispatchers.IO)
            .onCompletion {
                //在结束流之前调用的  例如关闭loading
            }
    }

    suspend fun requestCategoryContent(
        request: suspend (API) -> CategoryContentBean?
    ): Flow<CategoryState> {
        return flow<CategoryState> {
            val result = request(MainService) ?: throw IllegalArgumentException("数据非法，获取响应数据为空")
            emit(CategoryState.RequestCategoryContentSus(result))
        }.onStart {
            //在开始流之前调用的  例如开启loading
//            emit(MainFragmentUiState.IsLoading)
        }.catch {
            //请求出现错误，例如无网络，地址出错，返回异常
//            emit(BaseResult.Failure(it))
            Log.d("main","444$it")
            emit(CategoryState.RequestCategoryContentErr(it as Exception))
        }.flowOn(Dispatchers.IO)
            .onCompletion {
                //在结束流之前调用的  例如关闭loading
            }
    }


}