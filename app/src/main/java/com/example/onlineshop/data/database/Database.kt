package com.example.onlineshop.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.onlineshop.model.CartProduct

@Database(entities = [CartProduct::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract val cartDao:CartDao
}