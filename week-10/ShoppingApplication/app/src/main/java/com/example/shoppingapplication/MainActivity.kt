package com.example.restapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.shoppingapplication.ProductDBViewModel
import com.example.shoppingapplication.ProductDBViewModelFactory
import com.example.shoppingapplication.ProductRepository
import com.example.shoppingapplication.ProductViewModel
import com.example.shoppingapplication.ProductViewModelFactory
import com.example.shoppingapplication.Resource
import com.example.shoppingapplication.ui.theme.ShoppingApplicationTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppingApplicationTheme {
                val navController = rememberNavController()
                var selectedPage by remember { mutableStateOf("") }
                var productId by remember { mutableStateOf(0) }

                navController.addOnDestinationChangedListener { controller, destination, arguments ->
                    selectedPage = destination.route ?: "home"
                }
                navController.removeOnDestinationChangedListener { controller, destination, arguments ->
                    selectedPage = destination.route ?: "home"
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title={ Text("Shopping App") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF3F51B5),
                                titleContentColor = Color.White
                            ),
                            actions = {
                                IconButton(
                                    onClick = {},
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
                                selected = selectedPage == "home",
                                onClick = { navController.navigate("home") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.White,
                                    unselectedIconColor = Color.White,
                                    indicatorColor = Color(0xFF5E76C5),
                                )
                            )
                            NavigationBarItem(
                                icon = { Icon(Icons.Default.History, contentDescription = null) },
                                selected = selectedPage == "history",
                                onClick = { navController.navigate("history") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.White,
                                    unselectedIconColor = Color.White,
                                    indicatorColor = Color(0xFF5E76C5),
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable (route = "home"){
                            AllProductsScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                onClick = { id -> productId = id }
                            )
                        }
                        composable (route = "confirm"){
                            ConfirmScreen(
                                navController = navController,
                                productId = productId,
                            )
                        }
                        composable(route = "history") {
                            HistoryScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConfirmScreen(
    navController: NavController,
    productId: Int,
    viewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(ProductRepository())),
    viewProductDBModel: ProductDBViewModel = viewModel(
        factory = ProductDBViewModelFactory(LocalContext.current)
    ),
    modifier: Modifier = Modifier
){
    var qty by remember { mutableStateOf(1) }

    val state = viewModel.product.observeAsState()
    LaunchedEffect(productId) { viewModel.loadProduct(productId) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        when(val result = state.value) {
            is Resource.Loading -> { CircularProgressIndicator() }
            is Resource.Success -> {
                val product = result.data

                Column(
                    modifier = modifier.fillMaxSize().padding(16.dp)
                ) {
                    Spacer(modifier.height(140.dp))
                    Text("รายการที่สั่ง", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    AsyncImage(
                        model = product?.image,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier.height(10.dp))
                    Text("รายละเอียดเพิ่มเติม:")
                    Text("ชื่อสินค้า: ${product?.title}")
                    Text("ราคา: $${product?.price}")
                    Text("ประเภทสินค้า: ${product?.category}")
                    Text("จำนวน: ")
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = modifier.fillMaxWidth().padding(vertical = 20.dp),
                    ) {
                        IconButton(onClick = {
                            if (qty > 1) {
                                qty -= 1
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.RemoveCircleOutline,
                                contentDescription = null
                            )
                        }
                        Text("${qty}", modifier = modifier.padding(bottom = 10.dp), fontSize = 18.sp)
                        IconButton(onClick = {
                            if (qty < 100){
                                qty += 1
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.AddCircleOutline,
                                contentDescription = null
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = modifier.fillMaxWidth(),
                    ) {
                        Button(onClick = {
                            viewProductDBModel.insertProduct(
                                id = Random.nextInt(1, 1000000),
                                name = product!!.title,
                                price = product.price,
                                qty = qty,
                                image = product.image,
                            )
                            navController.navigate("home")
                        }) { Text("สั่งเลย") }
                        Button(onClick = { navController.navigate("home") }) { Text("ยกเลิก") }
                    }
                }
            }
            is Resource.Error -> { Text(text = result.message ?: "Error") }
            null -> Unit
        }
    }
}

@Composable
fun HistoryScreen(
    navController: NavController,
    viewProductDBModel: ProductDBViewModel = viewModel(factory = ProductDBViewModelFactory(LocalContext.current)),
    modifier: Modifier = Modifier,
){
    val products by viewProductDBModel.products.collectAsState(initial = emptyList())

    LazyColumn(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.fillMaxSize().padding(16.dp)
    ) {
        item {
            Spacer(modifier.height(130.dp))
            Text("ประวัติการสั่งซื้อ",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = modifier.padding(bottom = 16.dp))
        }
        items(products) {
                product ->
            Row(modifier = modifier
                .fillMaxWidth()
                .background(Color(0xF0E7E7E7))
                .padding(12.dp)
            ) {
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 10.dp),
                    contentScale = ContentScale.Fit
                )
                Column() {
                    Text("${product.name}", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Text("Qty: ${product.qty}")
                    Text("Price: ${product.price}")
                }
            }
            Spacer(modifier.height(20.dp))
        }
    }
}

@Composable
fun AllProductsScreen(
    viewModel: ProductViewModel = viewModel(factory = ProductViewModelFactory(ProductRepository())),
    navController: NavController,
    onClick: (id: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = viewModel.allProducts.observeAsState()
    LaunchedEffect(Unit) { viewModel.loadAllProducts() }
    when(val result = state.value) {
        is Resource.Loading -> { CircularProgressIndicator() }
        is Resource.Success -> {
            LazyColumn{
                items(result.data ?: emptyList()) {
                        product -> ProductItem(product = product, navController = navController, onClick =  onClick)
                }
            }
        }
        is Resource.Error -> { Text(text = result.message ?: "Error") }
        null -> Unit
    }
}

@Composable
fun ProductItem(
    product: com.example.shoppingapplication.Product,
    navController: NavController,
    onClick: (Int) -> Unit,
){
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Column(modifier = Modifier
            .padding(16.dp)
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                product.title,
                fontWeight = FontWeight.Bold
            )
            Text("Price $${product.price}")
            Text("Category: ${product.category}")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Text("Rating: ${product.rating.rate} (${product.rating.count})")
                IconButton(onClick = {
                    onClick(product.id)
                    navController.navigate("confirm")
                }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null
                    )
                }
            }
        }
    }
}