package com.example.shopapp

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

@Entity(tableName = "orders")
data class OrderEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val size: String,
    val qty: Int,
    val note: String?
)

@Dao
interface  OrderDao {
    @Insert
    suspend fun insert(order: OrderEntity)

    @Query("SELECT * FROM orders")
    fun getAll(): Flow<List<OrderEntity>>
}

@Database(
    entities = [OrderEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun orderDao(): OrderDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "order_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}

class OrderRepository(private  val dao: OrderDao){
    suspend fun insert(order: OrderEntity){
        dao.insert(order)
    }

    val orders = dao.getAll()
}

class OrderViewModel(
    private val repository: OrderRepository
): ViewModel() {
    val orders = repository.orders
    fun insertOrder(size: String, qty: Int, note: String?){
        viewModelScope.launch {
            repository.insert(
                OrderEntity(
                    size = size,
                    qty = qty,
                    note = note
                ))
        }
    }
}

class OrderViewModelFactory(context: Context): ViewModelProvider.Factory {
    private val dao = AppDatabase.getDatabase(context).orderDao()
    private val repository = OrderRepository(dao)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return OrderViewModel(repository) as T
        }

        throw IllegalArgumentException("ไม่พบ ViewModel ที่ต้องการ")
    }
}