package com.example.jetnytimesnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.jetnytimesnews.compose.JetNYTimesNewsApp
import com.example.jetnytimesnews.ui.theme.JetNYTimesNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetNYTimesNewsTheme {
                JetNYTimesNewsApp()
            }
        }
    }
}
