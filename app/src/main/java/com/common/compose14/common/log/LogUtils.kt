
import com.orhanobut.logger.Logger
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.PrettyFormatStrategy


/**
 * @author: 时间
 * Date:2021/9/19
 * Time:19:24
 */
object LogUtils {

    /**
     * 初始化log工具，在app入口处调用
     *
     * @param isLogEnable 是否打印log
     */
    fun init() {
        val build = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .methodOffset(1)
            .tag("画江湖")
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(build))

    }

    fun d(message: String?) {
        Logger.d(message)
    }

    fun i(message: String?) {
        Logger.i(message!!)
    }

    fun w(message: String, e: Throwable?) {
        val info = e?.toString() ?: "null"
        Logger.w("$message：$info")
    }

    fun e(message: String?, e: Throwable?) {
        Logger.e(e, message!!)
    }

    fun json(json: String?) {
        Logger.json(json)
    }
}