package com.example.shoppingapplication

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Entity(tableName = "products")
data class ProductEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    val name: String,
    val image: String,
    val qty: Int,
    val price: Double
)

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(product: ProductEntity)

    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id")
    fun getByID(id: Int): Flow<ProductEntity?>

    @Query("DELETE FROM products")
    suspend fun deleteAll()
}

@Database(
    entities = [ProductEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun productDao(): ProductDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "product_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}

class ProductDBRepository(private val dao: ProductDao){
    suspend fun insert(product: ProductEntity){
        dao.insert(product)
    }

    val products = dao.getAll()

    fun getByID(id: Int): Flow<ProductEntity?>{
        return dao.getByID(id)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}

class ProductDBViewModel(
    private val repository: ProductDBRepository
): ViewModel() {
    val products = repository.products
    fun insertProduct(id: Int, name: String, image: String, qty: Int, price: Double){
        viewModelScope.launch {
            repository.insert(
                ProductEntity(
                    id = id,
                    name = name,
                    image = image,
                    qty = qty,
                    price = price
                ))
        }
    }

    fun getProductID(id: Int): Flow<ProductEntity?>{
        return repository.getByID(id);
    }

    fun deleteAllProduct(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}

class ProductDBViewModelFactory(context: Context): ViewModelProvider.Factory {
    private val dao = AppDatabase.getDatabase(context).productDao()
    private val repository = ProductDBRepository(dao)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDBViewModel::class.java)) {
            return ProductDBViewModel(repository) as T
        }

        throw IllegalArgumentException("ไม่พบ ViewModel ที่ต้องการ")
    }
}