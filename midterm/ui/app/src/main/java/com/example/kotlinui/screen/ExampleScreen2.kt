package com.example.kotlinui.screen
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.outlined.Accessibility
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlinui.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import kotlin.random.Random

@Composable
fun ExampleScreen2(modifier: Modifier){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        DiceImage()
        ImageIcons()
    }
}

@Composable
fun DiceImage(modifier: Modifier = Modifier){
    val defaultSize: Modifier = modifier.size(230.dp)
    var diceRoll by remember { mutableStateOf(1)}

    when(diceRoll){
        1 -> Image(
            painter = painterResource(R.drawable.dice_1),
            contentDescription = null,
            modifier = defaultSize,
        )
        2 -> Image(
            painter = painterResource(R.drawable.dice_2),
            contentDescription = null,
            modifier = defaultSize,
        )
        3 -> Image(
            painter = painterResource(R.drawable.dice_3),
            contentDescription = null,
            modifier = defaultSize,
        )
        4 -> Image(
            painter = painterResource(R.drawable.dice_4),
            contentDescription = null,
            modifier = defaultSize,
        )
        5 -> Image(
            painter = painterResource(R.drawable.dice_5),
            contentDescription = null,
            modifier = defaultSize,
        )
        else -> Image(
            painter = painterResource(R.drawable.dice_6),
            contentDescription = null,
            modifier = defaultSize,
        )
    }

    Button(onClick = {
        diceRoll = (1..6).random()
    },
        modifier = modifier
            .width(140.dp)
            .padding(top = 20.dp)
        ) {
        Icon(imageVector = Icons.Filled.Android, contentDescription = null, modifier = modifier.padding(end = 6.dp))
        Text("Roll")
    }
}

@Composable
fun ImageIcons(modifier: Modifier = Modifier){
    val defaultSize = modifier.size(30.dp)
    val icons = listOf(
        Icons.Outlined.Add,
        Icons.Outlined.Accessibility,
        Icons.Outlined.Air,
        Icons.Rounded.AttachMoney
    )

    Spacer(modifier = modifier.height(30.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxWidth()
        ) {
        for (i in 0..icons.size - 1){
            IconButton(onClick = {},
                modifier = modifier.padding(top = 10.dp, bottom = 10.dp)) {
                Icon(imageVector = icons.get(i), contentDescription = null, modifier = defaultSize)
            }
        }
    }
}