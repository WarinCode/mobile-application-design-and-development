package com.example.myapplication2

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication2.ui.theme.MyApplication2Theme
import com.example.myapplication2.SignUpScreen
import com.example.myapplication2.SharedViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication2Theme {
                MyApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Search", "Notifications", "Mail", "Setting")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        Icons.Default.Notifications,
        Icons.Default.Mail,
        Icons.Default.Settings)

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1C4D8D),
                    titleContentColor = Color.White
                ),
                title = { Text("Shop App") },
                navigationIcon = {
                    IconButton (onClick = {}) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = null, tint = Color.White)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar (
                containerColor = Color(0xFF1C4D8D),
                contentColor = Color.White
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
//                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            when(index) {
//                                0 -> navController.navigate("home")
                                0 -> navController.navigate("signup_screen")
//                                1 -> navController.navigate("findData")
                                1 -> navController.navigate("profile_screen")
                                2 -> navController.navigate("showData")
//                                3 -> navController.navigate("signup_screen")
//                                4 -> navController.navigate("profile_screen")
                            } },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.Gray,
                            indicatorColor = Color(0xFF6A45B1)
                        )
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()) { innnerpadding ->
            NavHost(
                navController = navController,
                startDestination = "signup_screen",
                modifier = Modifier,
            ){
                composable ("home") {
                    HomeScreen(
                    ToSendData = {
                        text -> navController.navigate("search/$text")
                    }
                )}
                composable ("search/{dataInput}") {
                    backEntry -> val data = backEntry.arguments?.getString("dataInput") ?: "ไม่มีข้อมูล"
                    SearchScreen(data, navController = navController)
                }
                composable("showData") { ShowDataScreen() }
                composable ("findData") { FindDataScreen() }
                composable ("signup_screen") {
                    SignUpScreen (
                        onSignUp = { user ->
                            sharedViewModel.setUser(user)
                            navController.navigate("profile_screen")
                        }
                    )
                }
                composable ("profile_screen") { ProfileScreen(sharedViewModel) }
            }
    }
}

@Composable
fun HomeScreen(ToSendData: (String) -> Unit, modifier: Modifier = Modifier) {
    var textInput by remember { mutableStateOf(TextFieldValue("")) }
    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = textInput,
            onValueChange = { new -> textInput = new },
            label = { Text("กรุณากรอกข้อมูล") },
            modifier = modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(30.dp))
        Button (onClick = {
            ToSendData(textInput.text)
        }) {
            Text("ส่งข้อมูล")
        }
    }
}

@Composable
fun SearchScreen(dataReceive: String, navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hello $dataReceive")
        IconButton(onClick = {
            navController.popBackStack("home", inclusive = false)
        }) {
            Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)
        }
    }
}

@Composable
fun FindDataScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Search Screen")
    }
}

@Composable
fun ShowDataScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("หน้าสำหรับค้นหา")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplication2Theme {
        MyApp()
    }
}
