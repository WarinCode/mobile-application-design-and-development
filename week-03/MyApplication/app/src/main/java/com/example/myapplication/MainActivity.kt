package com.example.myapplication

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_DESK
import android.os.Bundle
import android.text.Layout
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(containerColor = Color.Red, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.react)
    val image2 =painterResource(R.drawable.nextjs)

    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(10.dp).fillMaxSize()
    ){
        Text(
            text = "Hello $name!",
            fontSize = 30.sp,
            // lineHeight = 50.sp,
            color = Color(color = 0xFFF11470),
            modifier = modifier.padding(30.dp)
        )
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier,
        ){
            Text(
                text = "Kotlin",
                fontSize = 20.sp,
            )
            Text(
                text = "Hello App",
                fontSize = 30.sp,
                modifier = modifier,
            )
        }
        Box(modifier =
            Modifier.height(120.dp)
                .fillMaxWidth()
                .background(Color.Blue)
                ){
            Text(text = "Wednesday", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Row (verticalAlignment = Alignment.CenterVertically){
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = image, contentDescription = "React.js", modifier = modifier.size(100.dp))
                Text(text = "React.js", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            }

            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = image2, contentDescription = "React.js", modifier = modifier.size(80.dp))
                Text(text = "Next.js", fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Scaffold(containerColor = Color.White, modifier = Modifier.fillMaxSize()) { innerPadding ->
            Greeting(name = "Android")
        }
        // Greeting("Android")
    }
}