package io.github.henryquan.battery68

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.github.henryquan.battery68.ui.theme.Battery68Theme
import io.github.henryquan.battery68.ui.view.BatteryStatusView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Battery68Theme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = MaterialTheme.colorScheme.background,
                    darkIcons = !isSystemInDarkTheme()
                )

                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    BatteryStatusView(100.0f)
                }
            }
        }
    }
}
