package com.example.shopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.sharp.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModel

data class ProductModel (
    val detail: String,
    val quantity: Int,
    val size: String,
)

class SharedViewModel: ViewModel(){
    private val _product = mutableStateOf<ProductModel?>(null)
    val product: State<ProductModel?> = _product

    fun setProduct(newProduct: ProductModel){
        _product.value = newProduct
    }
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val sharedViewModel: SharedViewModel = viewModel()

            ShopAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF571C1C),
                                titleContentColor = Color.White
                            ),
                            title = { Text("Shop App") },
                            actions = {
                                IconButton(onClick = {}) {
                                    Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = null)
                                }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = Color(0xFF571C1C),
                            contentColor = Color.White,
                            ) {
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                                selected = true,
                                onClick = { navController.navigate("home") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.White,
                                    unselectedIconColor = Color.White,
                                    indicatorColor = Color(0xFFCC9486),
                                )
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.History, contentDescription = null) },
                                selected = false,
                                onClick = { navController.navigate("orderDetails") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.White,
                                    unselectedIconColor = Color.White,
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ){
                        composable(route = "home") {
                            Shop(navController = navController,
                                onSubmit = { product ->
                                    sharedViewModel.setProduct(product)
                                    navController.navigate("orderDetails")
                            },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable(route = "orderDetails"){
                            OrderDetails(sharedViewModel = sharedViewModel,
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Shop(navController: NavController, onSubmit: (ProductModel) -> Unit, modifier: Modifier = Modifier){
    val sizes = listOf("S", "M", "L")
    var detail by remember { mutableStateOf("") }
    var selectedSize by remember { mutableStateOf(sizes[0]) }
    var quantity by remember { mutableStateOf(1) }

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
            sizes.forEach { size ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 5.dp)
                ){
                    RadioButton(
                        selected = size == selectedSize,
                        onClick = { selectedSize = size }
                    )
                    Text(size)
                }
            }
        }

        Text("รายละเอียดเพิ่มเติม: ")
        OutlinedTextField(
            value = detail,
            onValueChange = { new -> detail = new },
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
                if (quantity > 1) {
                    quantity -= 1
                }
            }) {
                Icon(imageVector = Icons.Default.RemoveCircleOutline, contentDescription = null)
            }
            Text("$quantity", fontSize = 18.sp)

            IconButton(onClick = {
                quantity += 1
            }) {
                Icon(imageVector = Icons.Default.AddCircleOutline, contentDescription = null)
            }
        }

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                onSubmit(ProductModel(detail = detail, quantity = quantity, size = selectedSize))
                navController.navigate("orderDetails")
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0BDAC3)
        )){
            Text("ใส่ตะกร้า")
        }
    }
}

@Composable
fun OrderDetails(sharedViewModel: SharedViewModel, navController: NavController, modifier: Modifier = Modifier){
    val product by sharedViewModel.product

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painterResource(R.drawable.image,),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        )
        Text("รายการที่สั่ง", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("ขนาด: ${product!!.size}")
        Text("จำนวน: ${product!!.quantity}")
        Text("รายละเอียดเพิ่มเติม: ${product!!.detail}")
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Button(onClick = { navController.navigate("home") }){
                Text("สั่งเลย")
            }
            Button(onClick = { navController.navigate("home") }){
                Text("ยกเลิก")
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val radioOption = listOf("ปี 1", "ปี 2", "ปี 3", "ปี 4", "อื่นๆ")
    var selectedOption by remember { mutableStateOf(radioOption[0]) }

    Column(
        modifier = modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text("ชั้นปีของคุณ")
        radioOption.forEach { option ->
            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = {
                        selectedOption = option
                    }
                )
                Text(option)
            }
        }
        Spacer(modifier = modifier.height(20.dp))
        Text("ผบคำตอบของคุณเลือกคือ: $selectedOption",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth()
        )
    }
}