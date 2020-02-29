package com.aranteknoloji.connectableobservabletraining

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MyDao {

    @Query("select * from my_entity where id = :id")
    fun getDataObservable(id: Int): Flowable<MyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(myEntity: MyEntity): Completable
}