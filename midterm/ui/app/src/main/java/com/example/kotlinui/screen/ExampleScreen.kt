package com.example.kotlinui.screen
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExampleScreen(modifier: Modifier){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Greeting()
        Spacer(modifier = Modifier.height(10.dp))
        Buttons()
    }
}


@Composable
fun Greeting(modifier: Modifier = Modifier) {
    Text(
        text = "Hello Android",
        color = Color(0xFFE91E63),
        fontSize = 35.sp,
        modifier = modifier,
    )
    Text(
        text = "Hello Jetpack",
        color = Color(0xFF2196F3),
        fontSize = 35.sp,
        lineHeight = 60.sp,
        modifier = modifier,
    )
    Text(
        text = "Hello Kotlin",
        color = Color(0xFFAC1EE9),
        fontSize = 35.sp,
        modifier = modifier,
    )
}

@Composable
fun Buttons(modifier: Modifier = Modifier) {
    val defaultSize: Modifier = modifier
        .width(170.dp)
        .height(50.dp)
    val defaultOnClick: () -> Unit = {}

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().height(400.dp),
    ) {
        Button(onClick = defaultOnClick, modifier = defaultSize) {
            Text("Button")
        }
        FilledTonalButton (onClick = defaultOnClick, modifier = defaultSize) {
            Text("Button")
        }
        OutlinedButton(onClick = defaultOnClick, modifier = defaultSize) {
            Text("Button")
        }
        ElevatedButton(onClick = defaultOnClick, modifier = defaultSize) {
            Text("Button")
        }
        TextButton(onClick = defaultOnClick, modifier = defaultSize) {
            Text("Button")
        }
    }
}
