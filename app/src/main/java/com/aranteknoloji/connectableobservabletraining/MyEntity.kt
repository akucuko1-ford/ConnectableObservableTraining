package com.aranteknoloji.connectableobservabletraining

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_entity")
data class MyEntity(
    @PrimaryKey
    val id: Int,
    val data: String
)