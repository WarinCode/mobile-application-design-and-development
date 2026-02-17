package com.example.shoppingapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class Rating (
    val rate: Double,
    val count: Int
)

data class Product (
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)

interface ApiService {
    @GET("products/{id}")
    suspend fun getProductByID(
        @Path("id") id: Int
    ): Response<Product>

    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

    @POST("products")
    suspend fun addProduct(product: Product): Response<Product>

    @PUT("products/{id}")
    suspend fun updateProduct(id: Int, product: Product): Response<Product>

    @DELETE("products/{id}")
    suspend fun deleteProduct(id: Int): Response<Product>
}

object RetrofitInstance {
    private const val BASE_URL = "https://fakestoreapi.com/"
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
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

class ProductRepository {
    suspend fun fetchProductByID(id: Int): Resource<Product> {
        return try {
            val response = RetrofitInstance.api.getProductByID(id)
            if (response.isSuccessful){
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Empty body")
            } else { Resource.Error("Error ${response.code()}") }
        } catch (e: Exception) { Resource.Error(e.message) }
    }

    suspend fun fetchAllProducts(): Resource<List<Product>> {
        return try {
            val response = RetrofitInstance.api.getAllProducts()
            if (response.isSuccessful){
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Empty body")
            } else { Resource.Error("Error ${response.code()}") }
        } catch (e: Exception) { Resource.Error(e.message) }
    }
}

class ProductViewModel(private val repository: ProductRepository): ViewModel() {
    private val _product = MutableLiveData<Resource<Product>>()
    private val _allProducts = MutableLiveData<Resource<List<Product>>>()
    val product: LiveData<Resource<Product>> = _product
    val allProducts: LiveData<Resource<List<Product>>> = _allProducts
    fun loadProduct(id: Int){
        _product.value = Resource.Loading()
        viewModelScope.launch {
            _product.value = repository.fetchProductByID(id)
        }
    }

    fun loadAllProducts(){
        _allProducts.value = Resource.Loading()
        viewModelScope.launch {
            _allProducts.value = repository.fetchAllProducts()
        }
    }
}

class ProductViewModelFactory(private val repository: ProductRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}