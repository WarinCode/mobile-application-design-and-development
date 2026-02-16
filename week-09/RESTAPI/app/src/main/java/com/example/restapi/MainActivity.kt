package com.example.restapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.restapi.ui.theme.RESTAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RESTAPITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AllProductsScreen()
                }
            }
        }
    }
}

@Composable
fun ProductScreen(productId: Int, viewModel: ProductViewModel = viewModel(
    factory = ProductViewModelFactory(ProductRepository())
)) {
    val state = viewModel.product.observeAsState()
    LaunchedEffect(productId) { viewModel.loadProduct(productId) }
    when(val result = state.value) {
        is Resource.Loading -> { CircularProgressIndicator() }
        is Resource.Success -> { result.data?.let { ProductItem(it) } }
        is Resource.Error -> { Text(text = result.message ?: "Error") }
        null -> Unit
    }
}

@Composable
fun AllProductsScreen(viewModel: ProductViewModel = viewModel(
    factory = ProductViewModelFactory(ProductRepository())
)) {
    val state = viewModel.allProducts.observeAsState()
    LaunchedEffect(Unit) { viewModel.loadAllProducts() }
    when(val result = state.value) {
        is Resource.Loading -> { CircularProgressIndicator() }
        is Resource.Success -> {
            LazyColumn{
                items(result.data ?: emptyList()) {
                    product -> ProductItem(product)
                }
            }
        }
        is Resource.Error -> { Text(text = result.message ?: "Error") }
        null -> Unit
    }
}

@Composable
fun ProductItem(product: Product){
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
            Text("Rating: ${product.rating.rate} (${product.rating.count})")
        }
    }
}