package com.example.uefachampionsleagueapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.collection.emptyObjectList
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.uefachampionsleagueapi.ui.theme.UEFACHAMPIONSLEAGUEAPITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UEFACHAMPIONSLEAGUEAPITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AllTeamsScreen(modifier = Modifier
                        .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TeamItem(team: Team){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
        ) {
            Text(team.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 6.dp)
            )
            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(Color.Black)
                .padding(vertical = 5.dp)
            )
            Text("id ${team.id}", modifier =
                Modifier.padding(top=10.dp)
            )
            Text("short_name ${team.short_name}")
            Text("abbreviation: ${team.abbreviation}")
            Text("location: ${team.location}")
        }
    }

}

@Composable
fun AllTeamsScreen(
    modifier: Modifier = Modifier,
    viewModel: TeamViewModel = viewModel(
    factory = TeamViewModelFactory (TeamRepository())
)) {
    val state = viewModel.allTeams.observeAsState()
    LaunchedEffect(Unit) { viewModel.loadAllTeams() }
    when(val result = state.value) {
        is Resource.Loading -> { CircularProgressIndicator() }
        is Resource.Success -> {

            LazyColumn(modifier = modifier.padding(16.dp)){
                item {
                    Text("UEFA Champions League 2025 - 2026 | Teams",
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
                items(result.data!!.sortedBy { it -> it.id } ?: emptyList()) {
                        team -> TeamItem(team)
                }
            }
        }
        is Resource.Error -> {
            Text(text = result.message ?: "Error", modifier = Modifier.padding(top=120.dp))
        }
        null -> Unit
    }
}