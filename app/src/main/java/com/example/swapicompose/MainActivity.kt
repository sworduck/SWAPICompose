package com.example.swapicompose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.swapicompose.di.AppModule
import com.example.swapicompose.ui.theme.SWAPIComposeTheme

class MainActivity : ComponentActivity() {
    companion object{
        lateinit var appModule:AppModule
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appModule = AppModule(applicationContext)
        setContent {
            SWAPIComposeTheme {
                MainScreen()
            }
        }
    }

}


