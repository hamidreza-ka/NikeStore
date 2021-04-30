package com.example.nikestore.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nikestore.data.Product
import com.example.nikestore.data.repository.source.ProductLocalDataSource

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductLocalDataSource

}