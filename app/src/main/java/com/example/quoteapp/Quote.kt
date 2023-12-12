package com.example.quoteapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quote(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var text: String,
    var favorite: Boolean = false
)
