package com.example.kotlinui.screen
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun SimapleLayoutScreen(modifier: Modifier = Modifier){
    val scrollState = rememberScrollState()

    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
    ) {
        RowLayout()
        Spacer(modifier = Modifier.height(20.dp))
        ColumnLayout()
    }
}

@Composable
fun RowLayout(modifier: Modifier = Modifier){
    Text("Row Layout",
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth(),
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
    )

    Text("verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween",
        modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .border(2.dp, Color.Red)
            .padding(10.dp),
    ) {
        Box(modifier = Modifier.size(50.dp).background(Color.Red))
        Box(modifier = Modifier.size(50.dp).background(Color.Blue))
        Box(modifier = Modifier.size(50.dp).background(Color.Yellow))
        Box(modifier = Modifier.size(50.dp).background(Color.White))
    }

    Spacer(modifier = modifier.height(20.dp))
    Text("verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround",
        modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .border(2.dp, Color.Red)
            .padding(10.dp),
    ) {
        Box(modifier = Modifier.size(50.dp).background(Color.Red))
        Box(modifier = Modifier.size(50.dp).background(Color.Blue))
        Box(modifier = Modifier.size(50.dp).background(Color.Yellow))
        Box(modifier = Modifier.size(50.dp).background(Color.White))
    }

    Spacer(modifier = modifier.height(20.dp))
    Text("verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly",
        modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .border(2.dp, Color.Red)
            .padding(10.dp),
    ) {
        Box(modifier = Modifier.size(50.dp).background(Color.Red))
        Box(modifier = Modifier.size(50.dp).background(Color.Blue))
        Box(modifier = Modifier.size(50.dp).background(Color.Yellow))
        Box(modifier = Modifier.size(50.dp).background(Color.White))
    }

    Spacer(modifier = modifier.height(20.dp))
    Text("verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center",
        modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .border(2.dp, Color.Red)
            .padding(10.dp),
    ) {
        Box(modifier = Modifier.size(50.dp).background(Color.Red))
        Box(modifier = Modifier.size(50.dp).background(Color.Blue))
        Box(modifier = Modifier.size(50.dp).background(Color.Yellow))
        Box(modifier = Modifier.size(50.dp).background(Color.White))
    }

    Spacer(modifier = modifier.height(20.dp))
    Text("verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start",
        modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .border(2.dp, Color.Red)
            .padding(10.dp),
    ) {
        Box(modifier = Modifier.size(50.dp).background(Color.Red))
        Box(modifier = Modifier.size(50.dp).background(Color.Blue))
        Box(modifier = Modifier.size(50.dp).background(Color.Yellow))
        Box(modifier = Modifier.size(50.dp).background(Color.White))
    }

    Spacer(modifier = modifier.height(20.dp))
    Text("verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End",
        modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .border(2.dp, Color.Red)
            .padding(10.dp),
    ) {
        Box(modifier = Modifier.size(50.dp).background(Color.Red))
        Box(modifier = Modifier.size(50.dp).background(Color.Blue))
        Box(modifier = Modifier.size(50.dp).background(Color.Yellow))
        Box(modifier = Modifier.size(50.dp).background(Color.White))
    }
}

@Composable
fun ColumnLayout(modifier: Modifier = Modifier){
    Text("Column Layout",
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth(),
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
    )

    Text("verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally",
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .border(2.dp, Color.White)
            .padding(vertical = 15.dp)
    ) {
        Box(modifier = Modifier.size(50.dp).background(Color.Red))
        Box(modifier = Modifier.size(50.dp).background(Color.Blue))
        Box(modifier = Modifier.size(50.dp).background(Color.Yellow))
        Box(modifier = Modifier.size(50.dp).background(Color.White))
    }

    Spacer(modifier = modifier.height(20.dp))
    Text("verticalArrangement = Arrangement.SpaceAround, horizontalAlignment = Alignment.CenterHorizontally",
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .border(2.dp, Color.White)
            .padding(vertical = 15.dp)
    ) {
        Box(modifier = Modifier.size(50.dp).background(Color.Red))
        Box(modifier = Modifier.size(50.dp).background(Color.Blue))
        Box(modifier = Modifier.size(50.dp).background(Color.Yellow))
        Box(modifier = Modifier.size(50.dp).background(Color.White))
    }

    Spacer(modifier = modifier.height(20.dp))
    Text("verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally",
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .border(2.dp, Color.White)
            .padding(vertical = 15.dp)
    ) {
        Box(modifier = Modifier.size(50.dp).background(Color.Red))
        Box(modifier = Modifier.size(50.dp).background(Color.Blue))
        Box(modifier = Modifier.size(50.dp).background(Color.Yellow))
        Box(modifier = Modifier.size(50.dp).background(Color.White))
    }

    Spacer(modifier = modifier.height(20.dp))
    Text("verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally",
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .border(2.dp, Color.White)
            .padding(vertical = 15.dp)
    ) {
        Box(modifier = Modifier.size(50.dp).background(Color.Red))
        Box(modifier = Modifier.size(50.dp).background(Color.Blue))
        Box(modifier = Modifier.size(50.dp).background(Color.Yellow))
        Box(modifier = Modifier.size(50.dp).background(Color.White))
    }

    Spacer(modifier = modifier.height(20.dp))
    Text("verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally",
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .border(2.dp, Color.White)
            .padding(vertical = 15.dp)
    ) {
        Box(modifier = Modifier.size(50.dp).background(Color.Red))
        Box(modifier = Modifier.size(50.dp).background(Color.Blue))
        Box(modifier = Modifier.size(50.dp).background(Color.Yellow))
        Box(modifier = Modifier.size(50.dp).background(Color.White))
    }

    Spacer(modifier = modifier.height(20.dp))
    Text("verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally",
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .border(2.dp, Color.White)
            .padding(vertical = 15.dp)
    ) {
        Box(modifier = Modifier.size(50.dp).background(Color.Red))
        Box(modifier = Modifier.size(50.dp).background(Color.Blue))
        Box(modifier = Modifier.size(50.dp).background(Color.Yellow))
        Box(modifier = Modifier.size(50.dp).background(Color.White))
    }
}