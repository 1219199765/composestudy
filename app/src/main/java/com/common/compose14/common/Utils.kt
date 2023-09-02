
import API
import android.content.Context
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity

object Utils {

    fun getTheme(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        // 读取数据
        val name = sharedPreferences.getString("theme", "")
        return name ?: "light"
    }

    fun setTheme(theme: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        // 保存数据
        sharedPreferences.edit().putString("theme", theme).apply()
    }
}


