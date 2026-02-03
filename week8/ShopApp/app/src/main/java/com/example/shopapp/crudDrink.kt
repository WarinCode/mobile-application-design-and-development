package com.example.shopapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun UpdateScreen(
    orderID: Int,
    viewModel: OrderViewModel,
    modifier: Modifier = Modifier
){

    val radioOptions = listOf("S", "M", "L")
    var note by remember { mutableStateOf("") }
    var numberOrder by remember { mutableStateOf(1) }
    var selectedOption by remember { mutableStateOf(radioOptions[0]) }

    val orders by viewModel.getOrderID(orderID).collectAsState(initial = null)
    LaunchedEffect(orders) {
        orders?.let {
            note = it.note.toString()
            numberOrder = it.qty
            selectedOption = it.size
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(R.drawable.image),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )
        Text("ชานมไข่มุก",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text("Bubble milk tea")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text("ขนาด: ")
            radioOptions.forEach { size ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 5.dp)
                ){
                    RadioButton(
                        selected = size == selectedOption,
                        onClick = { selectedOption = size }
                    )
                    Text(size)
                }
            }
        }

        Text("รายละเอียดเพิ่มเติม: ")
        OutlinedTextField(
            value = note,
            onValueChange = { new -> note = new },
            label = { Text("เช่น หวานน้อย, เพิ่มช็อต") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("จำนวน", modifier = Modifier.padding(top = 10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            IconButton(onClick = {
                if (numberOrder > 1) {
                    numberOrder -= 1
                }
            }) {
                Icon(
                    imageVector = Icons.Default.RemoveCircleOutline,
                    contentDescription = null
                )
            }
            Text("$numberOrder", fontSize = 18.sp)

            IconButton(onClick = {
                numberOrder += 1
            }) {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = null
                )
            }
        }

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                viewModel.updateOrder(id = orderID, note = note, size = selectedOption, qty = numberOrder)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)
            )){
            Text("แก้ไข")
        }
    }
}