package com.example.firstapplication
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.tooling.preview.Preview
import com.example.firstapplication.ui.theme.FirstApplicationTheme
import kotlin.random.Random
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
@OptIn(ExperimentalMaterial3Api::class)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstApplicationTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                containerColor = Color(color = 0xFF6200EE),
                                titleContentColor = Color.White
                            ),
                            title = {Text(text = "Home") },
                            navigationIcon = {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                            },
                            actions = {
                                Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    DiceRandom(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
@Composable
fun DiceRandom(modifier: Modifier = Modifier){
    var randomNum by remember { mutableStateOf(value = 1) }
    var imageResult = when(randomNum){
        1->R.drawable.dice_1
        2->R.drawable.dice_2
        3->R.drawable.dice_3
        4->R.drawable.dice_4
        5->R.drawable.dice_5
        else ->R.drawable.dice_6
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResult),
            contentDescription = "1"
        )
        FilledTonalButton(onClick = {randomNum = (1..6).random()}) {
            Text(text = "Random")
        }
        var inputText by remember { mutableStateOf(value = TextFieldValue(text = ""))}
        var passText by remember { mutableStateOf(value = TextFieldValue(text = ""))}
        var isIconG by remember { mutableStateOf(value = false) }
        TextField(
            value = inputText,
            onValueChange = { newText -> inputText = newText},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = {
                Icon( Icons.Default.Call,
                    contentDescription = null)

            }
        )
        Spacer(modifier = Modifier.height(height = 10.dp))
        OutlinedTextField(
            value = passText,
            onValueChange = {password -> passText = password },
            visualTransformation = if(isIconG) VisualTransformation.None
                else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {isIconG = !isIconG}) {
                    Icon(
                        imageVector = if (isIconG)Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null)
                }

            }
        )
        Text(inputText.text)
    }
}

@Composable
fun FirstButton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                println("ทดสอบการ Click")
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color(0xFF998877),
                containerColor = Color.Black
            )
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null
            )
            Text(text = " Hello")
        }
        FilledTonalButton(onClick = {}) {
            Icon(imageVector = Icons.Default.Home, contentDescription = null)
            Text(text = "Black")
        }
        OutlinedButton(onClick = {}) {
            Icon(imageVector = Icons.Default.Home, contentDescription = null)
            Text(text = "Black")
        }
        var isToggle by remember { mutableStateOf(value = false) }
        var isThumb by remember { mutableStateOf(value = false) }
        Row {
            IconButton(onClick = { isToggle = !isToggle }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    tint = if (isToggle) Color.Red else Color.Gray,
                    contentDescription = null
                )
            }
            IconButton(onClick = { isThumb = !isThumb }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    tint = if (isThumb) Color.Red else Color.Gray,
                    contentDescription = null
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FirstApplicationTheme {
//        FirstButton()
        DiceRandom()
    }
}
