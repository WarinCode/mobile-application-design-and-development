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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.shopapp.UpdateScreen

data class DrinkModel (
    val size: String,
    val quantity: Int,
    val note: String?,
    )

class SharedViewModel: ViewModel(){
    private val _drink = mutableStateOf<DrinkModel?>(null)
    val product: State<DrinkModel?> = _drink

    fun setDrink(newDrink: DrinkModel){
        _drink.value = newDrink
    }
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopAppTheme {
                val navController = rememberNavController()
                val sharedViewModel: SharedViewModel = viewModel()
                val context = LocalContext.current
                val viewModel: OrderViewModel = viewModel(
                    factory = OrderViewModelFactory(context)
                )
                var selectedPage by remember { mutableStateOf("") }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF3F51B5),
                                titleContentColor = Color.White
                            ),
                            title = { Text("Shop App") },
                            actions = {
                                IconButton(onClick = {},
                                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ShoppingCart,
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = Color(0xFF3F51B5),
                            contentColor = Color.White,
                            ) {
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                                selected = true,
                                onClick = { navController.navigate("home") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.White,
                                    unselectedIconColor = Color.White,
                                    indicatorColor = Color(0xFF5E76C5),
                                )
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.History, contentDescription = null) },
                                selected = false,
                                onClick = { navController.navigate("history") },
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
                            HomeScreen(navController = navController,
                                onSubmit = { drink ->
                                    sharedViewModel.setDrink(drink)
                                    navController.navigate("confirm")
                            },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable(route = "confirm"){
                            ConfirmScreen(
                                sharedViewModel = sharedViewModel,
                                navController = navController,
                                viewModel = viewModel,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable(route = "history"){
                            HistoryScreeen(viewModel = viewModel, navController = navController,  modifier = Modifier)
                        }
                        composable(route = "update/{id}",
                            arguments = listOf(navArgument("id"){
                                type = NavType.IntType
                            })){ backStackEntry ->
                            val id = backStackEntry.arguments?.getInt("id") ?: 0
                            UpdateScreen(orderID = id, viewModel = viewModel, modifier = Modifier)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(navController: NavController, onSubmit: (DrinkModel) -> Unit, modifier: Modifier = Modifier){
    val sizes = listOf("S", "M", "L")
    var note by remember { mutableStateOf("") }
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
                if (quantity > 1) {
                    quantity -= 1
                }
            }) {
                Icon(
                    imageVector = Icons.Default.RemoveCircleOutline,
                    contentDescription = null
                )
            }
            Text("$quantity", fontSize = 18.sp)

            IconButton(onClick = {
                quantity += 1
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
                onSubmit(DrinkModel(note = note, quantity = quantity, size = selectedSize))
                navController.navigate("confirm")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4)
        )){
            Text("ใส่ตะกร้า")
        }
    }
}

@Composable
fun ConfirmScreen(sharedViewModel: SharedViewModel, navController: NavController, viewModel: OrderViewModel,  modifier: Modifier = Modifier){
    val drink by sharedViewModel.product

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
        Text("ขนาด: ${drink!!.size}")
        Text("จำนวน: ${drink!!.quantity}")
        Text("รายละเอียดเพิ่มเติม: ${drink!!.note}")
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Button(onClick = {
                navController.navigate("home")
                viewModel.insertOrder(
                    size = drink!!.size,
                    qty = drink!!.quantity,
                    note = drink!!.note
                )
            }){
                Text("สั่งเลย")
            }
            Button(onClick = { navController.navigate("home") }){
                Text("ยกเลิก")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreeen(viewModel: OrderViewModel, navController: NavController, modifier: Modifier = Modifier){
    val order by viewModel.orders.collectAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, top = 130.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        items(order) {
            orders ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Size: ${orders.size}, " +
                            "Qty: ${orders.qty}, " +
                            "Note: ${orders.note}",
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                    )
                    IconButton(onClick = {
                        navController.navigate("update/${orders.id}")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = {
                        viewModel.deleteOrder(id = orders.id, size = orders.size, qty = orders.qty, note = orders.note)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null
                        )
                    }
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