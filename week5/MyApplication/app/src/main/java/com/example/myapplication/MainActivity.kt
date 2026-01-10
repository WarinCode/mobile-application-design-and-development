package com.example.myapplication

import android.R
import android.os.Bundle
import android.webkit.WebView
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
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerifiedUser
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
import androidx.compose.runtime.MutableState
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
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigationevent.compose.rememberNavigationEventState
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
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
    val items = listOf("home", "search", "showdata", "profile", "signup")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        Icons.Default.DataObject,
        Icons.Default.VerifiedUser,
        Icons.Default.Email
    )
    var currentDestination by remember { mutableStateOf<String?>("home") }

    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        currentDestination = destination.route
    }

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
                        selected = currentDestination == item || currentDestination!!.contains(item),
                        onClick = { selectedItem = index
                            when(index)
                            {
                                0-> navController.navigate("home")
                                1-> navController.navigate("search/")
                                2-> navController.navigate("showdata")
                                3-> navController.navigate("profile")
                                4-> navController.navigate("signup")
                                else -> navController.navigate("signup")
                            }},
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.Gray,
                            indicatorColor = Color(0xFF6A45B1),
                        )
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()) { innerppadding ->
        NavHost(
            navController = navController,
            startDestination = "home"
        ){
            composable(route ="home"){ HomeScreen(ToSenData = {
                    text -> navController.navigate("search/$text")
            })}
            composable(route ="search/{text}")
            {
                    backEntry -> val data = backEntry.arguments?.getString("text")?: "Notdata"
                SearchScreen(
                    dataReceive = data,
                    navController = navController
                ) }

            composable(route ="showdata"){ ShowDataScreen()}
            composable (route="signup"){ SignUpScreen (
                onSignUp = {user->
                    sharedViewModel.setUser((user))
                    navController.navigate(route = "profile")
                }
            )
            }
            composable (route = "profile"){ ProfileScreen(sharedViewModel) }
        }
    }
}

@Composable
fun HomeScreen(ToSenData:(String)-> Unit, modifier: Modifier = Modifier) {
    var textInput by remember { mutableStateOf(TextFieldValue("")) }
    Column (
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = textInput,
            onValueChange = { new -> textInput = new },
            label = { Text("กรุณากรอกข้อมูล") },
            modifier = modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button (onClick = { ToSenData(textInput.text)}) {
            Text("ส่งข้อมูล")
        }
    }
}

@Composable
fun SearchScreen(dataReceive:String,navController: NavController,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Hello,$dataReceive")
        IconButton(onClick = { navController.popBackStack(route = "home", inclusive = false) }) {
            Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)
        }
    }
}

@Composable
fun ShowDataScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("หน้าสำหรับค้นหา")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        MyApp()
    }
}

