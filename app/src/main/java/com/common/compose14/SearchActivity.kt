package com.common.compose14

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.common.compose14.ui.theme.Compose14Theme
import com.common.compose14.view.MainView
import com.common.compose14.view.search.SearchView

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarTransparent()

        setContent {
            Compose14Theme {
                SearchView()
            }
        }
    }


    @Suppress("DEPRECATION")
    fun Activity.makeStatusBarTransparent() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = android.graphics.Color.TRANSPARENT//android.graphics.Color.TRANSPARENT
            navigationBarColor = android.graphics.Color.WHITE
        }
    }

}
