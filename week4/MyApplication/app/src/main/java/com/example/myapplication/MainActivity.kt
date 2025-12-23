package com.example.myapplication

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FirstButton(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun FirstButton( modifier: Modifier = Modifier) {
    val buttonModifier = modifier.width(150.dp).height(45.dp)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Column(modifier = modifier.height(300.dp).width(200.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Buttons", fontSize = 18.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            Button(
                onClick = { print("Test 12345") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
                modifier = buttonModifier,
            ) {
                Icon(imageVector = Icons.Rounded.Home, contentDescription = null, modifier = modifier.padding(end = 3.dp))
                Text("Home")
            }
            FilledTonalButton(onClick = {},
                modifier = buttonModifier
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null, modifier = modifier.padding(end = 3.dp))
                Text("Add")
            }
            OutlinedButton(onClick = {},
                modifier = buttonModifier
            ) {
                Icon(imageVector = Icons.Rounded.Edit, contentDescription = null, modifier = modifier.padding(end = 3.dp))
                Text("Edit")
            }
            OutlinedButton(onClick = {},
                modifier = buttonModifier
            ) {
                Icon(imageVector = Icons.Rounded.Delete, contentDescription = null, modifier = modifier.padding(end = 3.dp))
                Text("Delete")
            }
            var isToggle by remember { mutableStateOf(false) }
            var isThumb by remember { mutableStateOf(false) }
            Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly){
                IconButton(onClick = { isToggle = !isToggle }
                ) {
                    Icon(imageVector = if (isToggle) Icons.Default.Favorite else Icons.Outlined.Favorite,
                        contentDescription = null,
                        tint = if (isToggle) Color.Red else Color.Gray
                    )
                }
                IconButton(onClick = { isThumb = !isThumb }
                ) {
                    Icon(imageVector = if (isThumb) Icons.Default.ThumbUp else Icons.Default.ThumbUp,
                        contentDescription = null,
                        tint = if (isThumb) Color.Blue else Color.Gray,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        FirstButton()
    }
}