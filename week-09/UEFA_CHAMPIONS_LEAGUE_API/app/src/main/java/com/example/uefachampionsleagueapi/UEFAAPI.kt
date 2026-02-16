package com.example.uefachampionsleagueapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class Team (
    val id: Int,
    val name: String,
    val short_name: String,
    val abbreviation: String,
    val location: String
    )

data class TeamResponse(
    val data: List<Team>
)

interface ApiService {
    @GET("teams?season=2025")
    suspend fun getAllTeams(): Response<TeamResponse>
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.balldontlie.io/ucl/v1/"
    private const val API_KEY = "{Your Api Key}"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", API_KEY)
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

sealed class Resource<T> (
    val data: T? = null,
    val message: String? = null
){
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String?, data: T? = null): Resource<T>(data, message)
    class Loading<T>: Resource<T>()
}

class TeamRepository {
    suspend fun fetchAllTeams(): Resource<List<Team>> {
        return try {
            val response = RetrofitInstance.api.getAllTeams()
            if (response.isSuccessful){
                response.body()?.let {
                    Resource.Success(it.data)
                } ?: Resource.Error("Empty body")
            } else { Resource.Error("Error ${response.code()}") }
        } catch (e: Exception) { Resource.Error(e.message) }
    }
}

class TeamViewModel(private val repository: TeamRepository): ViewModel() {
    private val _allTeams = MutableLiveData<Resource<List<Team>>>()
    val allTeams: LiveData<Resource<List<Team>>> = _allTeams

    fun loadAllTeams(){
        _allTeams.value = Resource.Loading()
        viewModelScope.launch {
            _allTeams.value = repository.fetchAllTeams()
        }
    }
}

class TeamViewModelFactory(private val repository: TeamRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
            return TeamViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}