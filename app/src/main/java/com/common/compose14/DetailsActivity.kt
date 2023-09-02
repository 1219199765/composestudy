package com.common.compose14

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.common.compose14.ui.theme.Compose14Theme
import com.common.compose14.view.MainView
import com.common.compose14.view.details.DetailsView

class DetailsActivity : ComponentActivity() {

    lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        id = intent.getStringExtra("id").toString()


        setContent {
            Compose14Theme {
                DetailsView(id)
            }
        }
    }
}