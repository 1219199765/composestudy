import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http.promisesBody
import okhttp3.internal.platform.Platform
import okio.Buffer
import okio.GzipSource
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.TreeSet
import java.util.concurrent.TimeUnit


class MyHttpLoggingInterceptor @JvmOverloads constructor(
    private val logger: HttpLogger = HttpLogger()
) : Interceptor {

    @Volatile private var headersToRedact = emptySet<String>()

    @set:JvmName("level")
    @Volatile var level = Level.NONE

    enum class Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    interface Logger {
        fun log(message: String)

        companion object {
            /** A [Logger] defaults output appropriate for the current platform. */
            @JvmField
            val DEFAULT: Logger = object : Logger {
                override fun log(message: String) {
                    Platform.get().log(message)
                }
            }
        }
    }

    fun redactHeader(name: String) {
        val newHeadersToRedact = TreeSet(String.CASE_INSENSITIVE_ORDER)
        newHeadersToRedact += headersToRedact
        newHeadersToRedact += name
        headersToRedact = newHeadersToRedact
    }

    /**
     * Sets the level and returns this.
     *
     * This was deprecated in OkHttp 4.0 in favor of the [level] val. In OkHttp 4.3 it is
     * un-deprecated because Java callers can't chain when assigning Kotlin vals. (The getter remains
     * deprecated).
     */
    fun setLevel(level: Level) = apply {
        this.level = level
    }

    @JvmName("-deprecated_level")
    @Deprecated(
        message = "moved to var",
        replaceWith = ReplaceWith(expression = "level"),
        level = DeprecationLevel.ERROR)
    fun getLevel(): Level = level

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val level = this.level

//        val request = chain.request().newBuilder().header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmN2I1MjkyY2MwZTZmZjBjMjhmMzkyMzA4MzBhYmNhNCIsInN1YiI6IjY0YzEyNDc5MmYxYmUwMDEwYzgxY2RmNCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.IJrPSx_JyGWKGEect9QmF5c6C1p03HLvvAij2tGqIqg").build()
//        // 添加请求头

        val original: Request = chain.request()
        val request: Request = original.newBuilder()
            .header("Merch","2306263100549")
            .header("BusinessType","D")
            .build()




        if (level == Level.NONE) {
            return chain.proceed(request)
        }

        val logBody = level == Level.BODY
        val logHeaders = logBody || level == Level.HEADERS

        val requestBody = request.body

        val connection = chain.connection()
        var requestStartMessage =
            ("--> ${request.method} ${request.url}${if (connection != null) " " + connection.protocol() else ""}")
        if (!logHeaders && requestBody != null) {
            requestStartMessage += " (${requestBody.contentLength()}-byte body)"
        }
        logger.log(requestStartMessage)

        if (logHeaders) {
            val headers = request.headers

            if (requestBody != null) {
                // Request body headers are only present when installed as a network interceptor. When not
                // already present, force them to be included (if available) so their values are known.
                requestBody.contentType()?.let {
                    if (headers["Content-Type"] == null) {
                        logger.log("Content-Type: $it")
                    }
                }
                if (requestBody.contentLength() != -1L) {
                    if (headers["Content-Length"] == null) {
                        logger.log("Content-Length: ${requestBody.contentLength()}")
                    }
                }
            }

            for (i in 0 until headers.size) {
                logHeader(headers, i)
            }

            if (!logBody || requestBody == null) {
                logger.log("--> END ${request.method}")
            } else if (bodyHasUnknownEncoding(request.headers)) {
                logger.log("--> END ${request.method} (encoded body omitted)")
            } else if (requestBody.isDuplex()) {
                logger.log("--> END ${request.method} (duplex request body omitted)")
            } else if (requestBody.isOneShot()) {
                logger.log("--> END ${request.method} (one-shot body omitted)")
            } else {
                val buffer = Buffer()
                requestBody.writeTo(buffer)

                val contentType = requestBody.contentType()
                val charset: Charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

                logger.log("")
                if (buffer.isProbablyUtf8()) {
                    logger.log(buffer.readString(charset))
                    logger.log("--> END ${request.method} (${requestBody.contentLength()}-byte body)")
                } else {
                    logger.log(
                        "--> END ${request.method} (binary ${requestBody.contentLength()}-byte body omitted)")
                }
            }
        }

        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            logger.log("<-- HTTP FAILED: $e")
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body!!
        val contentLength = responseBody.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"
        logger.log(
            "<-- ${response.code}${if (response.message.isEmpty()) "" else ' ' + response.message} ${response.request.url} (${tookMs}ms${if (!logHeaders) ", $bodySize body" else ""})")

        if (logHeaders) {
            val headers = response.headers
            for (i in 0 until headers.size) {
                logHeader(headers, i)
            }

            if (!logBody || !response.promisesBody()) {
                logger.log("<-- END HTTP")
            } else if (bodyHasUnknownEncoding(response.headers)) {
                logger.log("<-- END HTTP (encoded body omitted)")
            } else {
                val source = responseBody.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                var buffer = source.buffer

                var gzippedLength: Long? = null
                if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
                    gzippedLength = buffer.size
                    GzipSource(buffer.clone()).use { gzippedResponseBody ->
                        buffer = Buffer()
                        buffer.writeAll(gzippedResponseBody)
                    }
                }

                val contentType = responseBody.contentType()
                val charset: Charset = contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8

                if (!buffer.isProbablyUtf8()) {
                    logger.log("")
                    logger.log("<-- END HTTP (binary ${buffer.size}-byte body omitted)")
                    return response
                }

                if (contentLength != 0L) {
                    logger.log("")
                    logger.log(buffer.clone().readString(charset))
                }

                if (gzippedLength != null) {
                    logger.log("<-- END HTTP (${buffer.size}-byte, $gzippedLength-gzipped-byte body)")
                } else {
                    logger.log("<-- END HTTP (${buffer.size}-byte body)")
                }
            }
        }

        return response
    }

    private fun logHeader(headers: Headers, i: Int) {
        val value = if (headers.name(i) in headersToRedact) "██" else headers.value(i)
        logger.log(headers.name(i) + ": " + value)
    }

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"] ?: return false
        return !contentEncoding.equals("identity", ignoreCase = true) &&
                !contentEncoding.equals("gzip", ignoreCase = true)
    }
}