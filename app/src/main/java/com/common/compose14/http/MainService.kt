import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val MainService: API by lazy {
    Retrofit.Builder()
        .baseUrl(" https://mock.mengxuegu.com/mock/64ec7de6e70b8004a69e95b8/music/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkHttpClient())
        .build().create(API::class.java)
}

val MyService2: API by lazy {
    Retrofit.Builder()
        .baseUrl("https://v.ystpay.cn/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(getOkHttpClient())
        .build().create(API::class.java)
}


private fun getOkHttpClient(): OkHttpClient {
    val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        .readTimeout(300, TimeUnit.SECONDS) //设置读取超时时间
        .writeTimeout(300, TimeUnit.SECONDS) //设置写的超时时间
        .connectTimeout(300, TimeUnit.SECONDS)

//    if (BuildConfig.DEBUG) {
    val httpLoggingInterceptor = MyHttpLoggingInterceptor()
    builder.addNetworkInterceptor(httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = MyHttpLoggingInterceptor.Level.BODY
    })
//    }
    return builder.build()
}