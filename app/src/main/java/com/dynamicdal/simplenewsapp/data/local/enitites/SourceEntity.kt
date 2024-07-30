package com.dynamicdal.simplenewsapp.data.local.enitites

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class SourceEntity(
    @PrimaryKey val id: String,
    val name: String
)