package com.aranteknoloji.connectableobservabletraining

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private var myDatabase: MyDatabase? = null

fun myDatabase(context: Context): MyDatabase =
    myDatabase ?: Room.databaseBuilder(context, MyDatabase::class.java, "my_database")
        .build().also { myDatabase = it }

@Database(entities = [MyEntity::class], version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun myDao(): MyDao
}