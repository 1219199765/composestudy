
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.StringBuilder

/**
 * @author: 时间
 * Date:2021/9/19
 * Time:18:43
 */
class HttpLogger:HttpLoggingInterceptor.Logger {
    var mMessage = StringBuilder()

    override fun log(message: String) {
        var msg = message
        if ((msg.startsWith("{") && msg.endsWith("}"))
            || (msg.startsWith("[") && (msg.endsWith("]")))
        ) {
            LogUtils.json(msg)
        } else {
            LogUtils.d(msg)
        }
    }
}