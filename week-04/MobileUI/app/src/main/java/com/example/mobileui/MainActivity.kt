package com.example.mobileui
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileui.ui.theme.MobileUITheme
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MobileUITheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(color = 0xFFFAE44C),
                                titleContentColor = Color.White
                            ),
                            title = {Text("Lemonade", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.SansSerif, color = Color.Black) },
                        )
                    }
                    ) { innerPadding ->
                    Contents(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

class Data(val image: Painter, val text: String) {}

@Composable
fun Contents(modifier: Modifier = Modifier) {
    var pointer by remember { mutableStateOf(0) }
    val dataItems = listOf(Data(
        painterResource(R.drawable.lemon_tree),
        "Tap the lemon tree to select a lemon"
    ), Data(
        painterResource(R.drawable.lemon_squeeze),
        "Keep tapping the lemon to squeeze it"
    ), Data(
        painterResource(R.drawable.lemon_drink),
        "Tap the lemonade to drink it"
    ), Data(
        painterResource(R.drawable.lemon_restart),
        "Tap the empty glass to start again"
    ))

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
            OutlinedButton (
                onClick = { pointer = (pointer + 1) % dataItems.size },
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC3EDD2)),
                modifier = Modifier.size(200.dp),
                border = BorderStroke(0.dp, Color.Transparent)
            ){
                Image(painter = dataItems[pointer].image, contentDescription = null, modifier = Modifier.size(130.dp))
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = dataItems[pointer].text,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { pointer = (pointer + 1) % dataItems.size },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
                modifier = Modifier
                .width(160.dp)
                .height(50.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.padding(end = 6.dp))
                Text("Next")
            }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MobileUITheme {
        Contents()
    }
}
