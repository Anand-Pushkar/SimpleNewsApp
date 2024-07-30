package com.dynamicdal.simplenewsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dynamicdal.simplenewsapp.data.local.enitites.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<RemoteKeyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: RemoteKeyEntity)

    @Query("select * from RemoteKeyEntity  WHERE id = :key")
    suspend fun getKey(key: String): RemoteKeyEntity?

    @Query("delete from RemoteKeyEntity")
    suspend fun clearAllKeys()
}