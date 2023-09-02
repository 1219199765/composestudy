package com.common.compose14

import LogUtils
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Lifecycle
import com.biubiu.eventbus.observe.observeEvent
import com.common.compose14.common.event.AppScopeEvent
import com.common.compose14.ui.theme.Compose14Theme
import com.common.compose14.view.MainView


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        //关键代码,沉浸
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//
//
//        val windowInsetsController =
//            WindowCompat.getInsetsController(window, window.decorView)

        makeStatusBarTransparent()


        moveTo()
        setContent {
            Compose14Theme {
                MainView()
            }
        }
    }


    fun moveTo() {
        observeEvent<AppScopeEvent> {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("id", it.toString())
            startActivity(intent, null)
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



