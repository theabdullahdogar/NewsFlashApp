package com.example.madquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.madquiz.navigation.AppNavigation
import com.example.madquiz.ui.theme.MADQUIZTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MADQUIZTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}
