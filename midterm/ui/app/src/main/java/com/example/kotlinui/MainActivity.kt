package com.example.kotlinui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kotlinui.screen.ExampleScreen
import com.example.kotlinui.screen.ExampleScreen2
import com.example.kotlinui.screen.SimapleLayoutScreen
import com.example.kotlinui.ui.theme.KotlinUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinUITheme(darkTheme = true) {
                var selectScreen by remember { mutableStateOf(1) }
                var changeScreen: List<() -> Unit> = listOf(
                    { selectScreen = 1 },
                    { selectScreen = 2 },
                    { selectScreen = 3 },
                )

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        SwitchScreen(changeScreen)
                        when(selectScreen) {
                            1 -> ExampleScreen(modifier = Modifier.fillMaxSize())
                            2 -> ExampleScreen2(modifier = Modifier.fillMaxSize())
                            3 -> SimapleLayoutScreen(modifier = Modifier.fillMaxSize())
                            else -> {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("Hello World!")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SwitchScreen(onClicks: List<() -> Unit>, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 0.. onClicks.size - 1){
            Button(onClick = onClicks.get(i)) {
                Text("Screen ${i + 1}")
            }
        }
    }
}
